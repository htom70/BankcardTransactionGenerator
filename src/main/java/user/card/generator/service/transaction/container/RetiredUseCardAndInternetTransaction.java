package user.card.generator.service.transaction.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.city.City;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.PreTransaction;
import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.CityService;
import user.card.generator.service.CountryService;
import user.card.generator.service.TransactionService;
import user.card.generator.service.transaction.kind.GeneralTransaction;
import user.card.generator.service.transaction.periodically.GeneralTimeTypedTransaction;
import user.card.generator.service.transaction.periodically.PeriodicallyTransactionFactory;
import user.card.generator.service.transaction.periodically.PeriodicallyTransactionType;
import user.card.generator.service.transaction.vendorandatm.AtmSelector;
import user.card.generator.service.transaction.vendorselector.RetiredDailyVendorSelector;
import user.card.generator.service.transaction.vendorselector.RetiredSaturdayVendorSelector;
import user.card.generator.service.transaction.vendorselector.VendorSelector;
import user.card.generator.time.CurrentYear;
import user.card.generator.time.TimestampGenerator;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RetiredUseCardAndInternetTransaction {

    @Autowired
    TransactionService transactionService;

    @Autowired
    CountryService countryService;

    @Autowired
    CityService cityService;

    @Autowired
    AtmSelector atmSelector;

    @Autowired
    RetiredDailyVendorSelector retiredDailyVendorSelector;

    @Autowired
    RetiredSaturdayVendorSelector retiredSaturdayVendorSelector;


    @Transactional
    public void processTransaction(List<Person> people, CurrentYear currentYear) {
        Instant start = Instant.now();
        Random random = new Random();
        Country country = countryService.findByCountryCode("HU");
        List<City> cities = cityService.findAllByCountry(country);
        List<Transaction> transactions = new ArrayList<>();

        for (Person person : people) {
            int limit;
            Map<Integer, List<LocalDate>> monthsAndDays = currentYear.getMonthsAndDaysInMonth(currentYear.getDays());
            for (Map.Entry<Integer, List<LocalDate>> item : monthsAndDays.entrySet()) {
                Map<LocalDate, List<PreTransaction>> pretransactionsMap = new HashMap<>();
                Month month = Month.of(item.getKey());
                List<LocalDate> daysInCurrentMonth = item.getValue();
                createDailyPosTransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                createSaturdayPosTransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                createMonthlyAtmTransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                if (month.equals(Month.DECEMBER)) {
                    createIntraDailyPostransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                }

                if (month.equals(Month.DECEMBER)) {
                    limit = (int) (person.getIncome() * 1.5);
                } else {
                    limit = (int) (person.getIncome() * (70 + random.nextInt(30)) / 100);
                }
                System.out.println(month);
                int sum = 0;
                System.out.println("limit: " + limit);
                int numberOfTransactionInGivenMonth = 0;
                for (List<PreTransaction> preTransactions : pretransactionsMap.values()) {
                    for (int i = 0; i < preTransactions.size() && (sum < limit); i++) {
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
        Instant endofSaveAll = Instant.now();
        long elapsedTime = Duration.between(start, endofSaveAll).toMillis();
        System.out.println("Retired generálás mentéssel együtt: " + elapsedTime + " ms");
        System.out.println("Tranzakciók száma: " + transactions.size());
    }

    private void createDailyPosTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        int lengthOfMonth = month.length(currentYear.getYear().isLeap());
        LocalDate startDate = LocalDate.of(currentYear.getYear().getValue(), month.getValue(), 1);
        LocalDate endDate = LocalDate.of(currentYear.getYear().getValue(), month.getValue(), lengthOfMonth);
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        List<LocalDate> transactionDays = Stream.iterate(startDate, date -> date.plusDays(2 + random.nextInt(1)))
                .limit(numOfDays)
                .collect(Collectors.toList());
        for (LocalDate day : transactionDays) {
            int amount = 2000 + random.nextInt(8001);
            Timestamp timestamp = TimestampGenerator.generate(random, day);
            Vendor vendor = retiredDailyVendorSelector.selectVendor(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.POS, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
            addItemToMap(pretransactionsMap, day, preTransaction);
        }
    }

    private void createSaturdayPosTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        List<LocalDate> saturdays = daysInCurrentMonth.stream()
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY)
                .collect(Collectors.toList());
        for (LocalDate saturday : saturdays) {
            int amount = 10000 + random.nextInt(5001);
            Timestamp timestamp = TimestampGenerator.generate(random, saturday);
            Vendor vendor = retiredSaturdayVendorSelector.selectVendor(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.POS, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
            addItemToMap(pretransactionsMap, saturday, preTransaction);
        }
    }

    private void createMonthlyAtmTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        int occasions = 4 + random.nextInt(5);
        System.out.println("Occasion: " + occasions);
        for (int i = 0; i < occasions; i++) {
            LocalDate day = daysInCurrentMonth.get(random.nextInt(daysInCurrentMonth.size()));
            int amount = 5000 + random.nextInt(5001);
            Timestamp timestamp = TimestampGenerator.generate(random, day);
            atmSelector.setHomeRatePercent(100);
            atmSelector.setPrivateBankPercent(100);
            ATM atm = atmSelector.selectAtm(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.ATM, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", atm.getATMcode());
            addItemToMap(pretransactionsMap, day, preTransaction);
        }

    }

    private void createIntraDailyPostransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        LocalDate startDate = LocalDate.of(currentYear.getYear().getValue(), month.getValue(), 1);
        LocalDate endDate = LocalDate.of(currentYear.getYear().getValue(), month.getValue(), 9);
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        List<LocalDate> normalTransactionDays = Stream.iterate(startDate, date -> date.plusDays(2 + random.nextInt(1)))
                .limit(numOfDays)
                .collect(Collectors.toList());
        for (LocalDate day : normalTransactionDays) {
            int amount = 2000 + random.nextInt(8001);
            Timestamp timestamp = TimestampGenerator.generate(random, day);
            Vendor vendor = retiredDailyVendorSelector.selectVendor(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.POS, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
            addItemToMap(pretransactionsMap, day, preTransaction);
        }
        int lengthOfMonth = month.length(currentYear.getYear().isLeap());
        startDate = LocalDate.of(currentYear.getYear().getValue(), month.getValue(), 10);
        endDate = LocalDate.of(currentYear.getYear().getValue(), month.getValue(), lengthOfMonth);
        numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        List<LocalDate> transactionDaysInChristmasPeriod = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays)
                .collect(Collectors.toList());
        for (LocalDate day : transactionDaysInChristmasPeriod) {
            int occasion = 2 + random.nextInt(4);
            for (int i = 0; i < occasion; i++) {
                Vendor vendor = retiredDailyVendorSelector.selectVendor(person);
                int amount = 2000 + random.nextInt(23001);
                Timestamp timestamp = TimestampGenerator.generate(random, day);
                PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.POS, timestamp, amount
                        , "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
                addItemToMap(pretransactionsMap, day, preTransaction);
            }
        }
    }

    public void addItemToMap(Map<LocalDate, List<PreTransaction>> preTransactions, LocalDate date, PreTransaction preTransaction) {
        if (preTransactions.containsKey(date)) {
            preTransactions.get(date).add(preTransaction);
        } else {
            preTransactions.put(date, new ArrayList<>(Arrays.asList(preTransaction)));
        }
    }


}
