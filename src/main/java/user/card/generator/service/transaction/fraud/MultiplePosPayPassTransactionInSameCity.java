package user.card.generator.service.transaction.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.city.City;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.*;
import user.card.generator.time.CurrentYear;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MultiplePosPayPassTransactionInSameCity {

    @Autowired
    TransactionService transactionService;

    @Autowired
    CountryService countryService;

    @Autowired
    CityService cityService;

    @Autowired
    VendorService vendorService;

    @Autowired
    PersonService personService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createFrauds(CurrentYear currentYear) {
        Random random = new Random();
        int numberOfTransaction = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction", Integer.class);
//        int numberOfFrauds = (int) (numberOfTransaction * 0.002); //10 % csalás
//        int numberOfFrauds = (int) (numberOfTransaction * 0.001); //5 % csalás
        int numberOfFrauds = (int) (numberOfTransaction * 0.0002); //1 % csalás
//        int numberOfFrauds = (int) (numberOfTransaction * 0.0006); //1 % csalás
//        int numberOfFrauds = (int) (numberOfTransaction * 0.0000625); //0.2 % csalás
        Country Hungary = countryService.findByCountryCode("HU");
        List<Vendor> activeVendors = vendorService.findAllVendorInNormalTransaction();
        List<City> cities = cityService.findAllByActiveVendors(activeVendors);
        List<Person> people = personService.findAll();
        int numberOfPeople = people.size();
        List<LocalDate> days = currentYear.getDays();
        for (int i = 0; i < numberOfFrauds; i++) {
            List<Transaction> transactions = new ArrayList<>();
            City currentCity = cities.get(random.nextInt(cities.size()));
            List<Vendor> vendorsInCurrentCity = vendorService.findAllVendorInNormalTransactionByCity(currentCity);
            Person person = people.get(random.nextInt(numberOfPeople));
            int occasions = 3 + random.nextInt(7);
            LocalDate currentDate = days.get(random.nextInt(days.size()));
            for (int j = 0; j < occasions; j++) {
                Vendor currentVendor = vendorsInCurrentCity.get(random.nextInt(vendorsInCurrentCity.size()));
                List<Timestamp> timestamps = generateTimestamp(currentDate, occasions, random);
                for (int k = 0; k < timestamps.size(); k++) {
                    Timestamp timestamp = timestamps.get(k);
                    int amount = 3000 + random.nextInt(12000);
                    Transaction transaction = new Transaction(person.getCardNumber(),
                            TransactionType.POS, timestamp, amount, "HUF", ResponseCode.OK, "HU", currentVendor.getVendorCode());
                    transaction.setAllFields(cities);
                    transaction.setFraud(true);
                    transactions.add(transaction);
                }
            }
            transactionService.saveAll(transactions);
        }
    }

    private List<Timestamp> generateTimestamp(LocalDate currentDate, int occasions, Random random) {
        List<Timestamp> result = new ArrayList<>();
        LocalTime localTime = LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60));
        LocalDateTime beginOfTime = LocalDateTime.of(currentDate, localTime);
        LocalDateTime time = beginOfTime;
        for (int i = 0; i < occasions; i++) {
            int randomNanosecs = random.nextInt(1000000);
            LocalDateTime actualTime = LocalDateTime.of(
                    time.getYear(), time.getMonth(), time.getDayOfMonth(), time.getHour(), time.getMinute(), time.getSecond(), randomNanosecs);
            Timestamp timestamp = Timestamp.valueOf(actualTime);
            result.add(timestamp);
            int diffMinute = 5 + random.nextInt(15);
            int diffSec = 3 + random.nextInt(55);
            localTime.plusMinutes(diffMinute);
            localTime.plusSeconds(diffSec);
        }
        return result;
    }
}
