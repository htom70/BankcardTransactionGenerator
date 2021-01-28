package user.card.generator.service.transaction.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.city.City;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.PreTransaction;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.CityService;
import user.card.generator.service.CountryService;
import user.card.generator.service.TransactionService;
import user.card.generator.service.transaction.vendorandatm.AtmSelector;
import user.card.generator.service.transaction.vendorselector.OrdinaryVendorSelector;
import user.card.generator.service.transaction.vendorselector.VendorSelector;
import user.card.generator.time.CurrentYear;
import user.card.generator.time.TimestampGenerator;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class OrdinaryUserDontUseCardTransaction {

    @Autowired
    TransactionService transactionService;

    @Autowired
    CountryService countryService;

    @Autowired
    CityService cityService;

    @Autowired
    AtmSelector atmSelector;

    @Autowired
    OrdinaryVendorSelector ordinaryVendorSelector;

    @Transactional
    public void processTransaction(List<Person> people, CurrentYear currentYear) {
        Random random = new Random();
        Country country = countryService.findByCountryCode("HU");
        List<City> cities = cityService.findAllByCountry(country);
        List<Transaction> transactions = new ArrayList<>();
        Instant start = Instant.now();
        for (Person person : people) {
            int limit = 400000;
            Map<Integer, List<LocalDate>> monthsAndDays = currentYear.getMonthsAndDaysInMonth(currentYear.getDays());
            for (Map.Entry<Integer, List<LocalDate>> item : monthsAndDays.entrySet()) {
                Map<LocalDate, List<PreTransaction>> pretransactionsMap = new HashMap<>();
                Map<LocalDate, List<PreTransaction>> yearlyPretransactionsMap = new HashMap<>();
                Month month = Month.of(item.getKey());
                List<LocalDate> daysInCurrentMonth = item.getValue();
                if (month.equals(Month.JANUARY)) {
                    createYearlyPosTransaction(yearlyPretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                }
                createMothlyPosTransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                createMonthlyAtmTransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                int sum = 0;
                for (List<PreTransaction> preTransactions : pretransactionsMap.values()) {
                    for (int i = 0; i < preTransactions.size() && (sum < limit); i++) {
                        sum = calculateSumAmontUponYearlyTransactionAndCurrentMonht(month, yearlyPretransactionsMap);
                        PreTransaction preTransaction = preTransactions.get(i);
                        sum += preTransaction.getAmount();
                        System.out.println("sum: " + sum);
                        System.out.println("i: " + i);
                        if (sum < limit) {
                            Transaction transaction = new Transaction(preTransaction.getCardNumber(), preTransaction.getTransactionType()
                                    , preTransaction.getTimestamp(), preTransaction.getAmount(), "HUF", ResponseCode.OK, "HU", preTransaction.getVendorCode());
                            transaction.setAllFields(cities);
                            transaction.setFraud(false);
                            transactions.add(transaction);
                        }
                    }
                }
            }
        }
        transactionService.saveAll(transactions);
        System.out.println("Kártyát nem használó személyek  generált tranzakcióinak száma: " + transactions.size());
        Instant end = Instant.now();
        long elapsedTime = Duration.between(start, end).toMillis() / 1000;
        System.out.println("Genrálás időszükséglete: " + elapsedTime + "másodperc");
    }

    private void createMothlyPosTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        int occasions = 2 + random.nextInt(4);
        for (int i = 0; i < occasions; i++) {
            LocalDate day = daysInCurrentMonth.get(random.nextInt(daysInCurrentMonth.size()));
            int amount = 2000 + random.nextInt(8001);
            Timestamp timestamp = TimestampGenerator.generate(random, day);
            Vendor vendor = ordinaryVendorSelector.selectVendor(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.POS, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
            addItemToMap(pretransactionsMap, day, preTransaction);
        }

    }

    private void createMonthlyAtmTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        int occasions = 2 + random.nextInt(1);
        for (int i = 0; i < occasions; i++) {
            LocalDate day = daysInCurrentMonth.get(random.nextInt(daysInCurrentMonth.size()));
            int amount = 100000 + random.nextInt(200001);
            Timestamp timestamp = TimestampGenerator.generate(random, day);
            atmSelector.setHomeRatePercent(90);
            atmSelector.setPrivateBankPercent(70);
            ATM atm = atmSelector.selectAtm(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.ATM, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", atm.getATMcode());
            addItemToMap(pretransactionsMap, day, preTransaction);
        }
    }

    private void createYearlyPosTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        int occasion = random.nextInt(5);
        for (int i = 0; i < occasion; i++) {
            LocalDate day = currentYear.getDays().get(random.nextInt(currentYear.getDays().size()));
            int amount = 100000 + random.nextInt(300001);
            Timestamp timestamp = TimestampGenerator.generate(random, day);
            Vendor vendor = ordinaryVendorSelector.selectVendor(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.POS, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
            addItemToMap(pretransactionsMap, day, preTransaction);
        }
    }

    private int calculateSumAmontUponYearlyTransactionAndCurrentMonht(Month month, Map<LocalDate, List<PreTransaction>> yearlyPretransactionsMap) {
        int sum = 0;
        for (Map.Entry<LocalDate, List<PreTransaction>> item : yearlyPretransactionsMap.entrySet()) {
            if (month.equals(item.getKey().getMonth())) {
                List<PreTransaction> preTransactions = item.getValue();
                for (PreTransaction preTransaction : preTransactions)
                    sum += preTransaction.getAmount();
            }
        }
        return sum;
    }

    public void addItemToMap(Map<LocalDate, List<PreTransaction>> preTransactions, LocalDate date, PreTransaction preTransaction) {
        if (preTransactions.containsKey(date)) {
            preTransactions.get(date).add(preTransaction);
        } else {
            preTransactions.put(date, new ArrayList<>(Arrays.asList(preTransaction)));
        }
    }

}
