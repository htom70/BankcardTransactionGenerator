package user.card.generator.service.transaction.fraud;

import org.springframework.beans.factory.annotation.Autowired;
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
public class MultiplePosPayPassTransactionAtSameVendor {

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

    public void createFrauds(CurrentYear currentYear) {
        Random random = new Random();
//        int sumOfTransaction = transactionService.findAll().size();
        int sumOfTransaction = 6566011;
        int numberOfFrauds = (int) (sumOfTransaction * 0.004);
        Country Hungary = countryService.findByCountryCode("HU");
        List<City> cities = cityService.findAllByCountry(Hungary);
        List<Vendor> vendors = vendorService.findAll();
        int numberOfVendors = vendors.size();
        List<Person> people = personService.findAll();
        int numberOfPeople = people.size();
        for (int i = 0; i < numberOfFrauds; i++) {
            List<Transaction> transactions = new ArrayList<>();
            Vendor vendor = vendors.get(random.nextInt(numberOfVendors));
            Person person = people.get(random.nextInt(numberOfPeople));
            List<LocalDate> days = currentYear.getDays();
            LocalDate currentDate = days.get(random.nextInt(days.size()));
            int occasions = 3 + random.nextInt(7);
            List<Timestamp> timestamps = generateTimestamp(currentDate, occasions,random);
            for (int j = 0; j < timestamps.size(); j++) {
                Timestamp timestamp = timestamps.get(j);
                int amount = 3000 + random.nextInt(12000);
                Transaction transaction = new Transaction(person.getCardNumber(),
                        TransactionType.POS, timestamp, amount, "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
                transaction.setAllFields(cities);
                transaction.setFraud(true);
                transactions.add(transaction);
            }
            transactionService.saveAll(transactions);
        }
    }

    private List<Timestamp> generateTimestamp(LocalDate currentDate, int occasions, Random random) {
        List<Timestamp> result = new ArrayList<>();
        LocalTime localTime = LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60));
        LocalDateTime beginOfTime = LocalDateTime.of(currentDate, localTime);
        LocalDateTime time = beginOfTime;
        for (int i = 0; i <occasions ; i++) {
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
