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
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class OrdinaryUseCardDontUseInternetTransaction {

    @Autowired
    TransactionService transactionService;

    @Autowired
    CountryService countryService;

    @Autowired
    CityService cityService;

    @Autowired
    AtmSelector atmSelector;

    @Transactional
    public void processTransaction(List<Person> people, CurrentYear currentYear) {
        Random random = new Random();
        Country country = countryService.findByCountryCode("HU");
        List<City> cities = cityService.findAllByCountry(country);
        List<Transaction> transactions = new ArrayList<>();

        for (Person person : people) {
            for (int i = 1; i <= 12; i++) {
                Map<LocalDate, List<PreTransaction>> pretransactionsMap = new HashMap<>();
                int limit = 400000;
                Map<Integer, List<LocalDate>> monthsAndDays = currentYear.getMonthsAndDaysInMonth(currentYear.getDays());
                for (Map.Entry<Integer, List<LocalDate>> item : monthsAndDays.entrySet()) {
                    Month month = Month.of(item.getKey());
                    List<LocalDate> daysInCurrentMonth = item.getValue();
                    if (i == 1) {
                        createYearlyPosTransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                    }
                    createIntraDailyPosTransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                    createMonthlyAtmTransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                    if (i == 12) {
                        createMonthlyNetTransaction(pretransactionsMap, person, currentYear, month, daysInCurrentMonth, random);
                    }
                }
                int sum = 0;
                while (sum < limit) {
                    for (Map.Entry<LocalDate, List<PreTransaction>> item : pretransactionsMap.entrySet()) {
                        for (PreTransaction preTransaction : item.getValue()) {
                            sum += preTransaction.getAmount();
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
    }

    private void createYearlyPosTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        int occasion = random.nextInt(5);
        for (int i = 0; i < occasion; i++) {
            LocalDate day = currentYear.getDays().get(random.nextInt(currentYear.getDays().size()));
            int amount = 100000 + random.nextInt(300001);
            Timestamp timestamp = TimestampGenerator.generate(random, day);
            VendorSelector vendorSelector = new OrdinaryVendorSelector(95);
            Vendor vendor = vendorSelector.selectVendor(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.POS, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
            addItemToMap(pretransactionsMap, day, preTransaction);
        }
    }

    private void createIntraDailyPosTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        for (LocalDate day : daysInCurrentMonth) {
            int occasion = 2 + random.nextInt(9);
            for (int i = 0; i < occasion; i++) {
                int amount = 1000 + random.nextInt(24001);
                Timestamp timestamp = TimestampGenerator.generate(random, day);
                VendorSelector vendorSelector = new OrdinaryVendorSelector(95);
                Vendor vendor = vendorSelector.selectVendor(person);
                PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.POS, timestamp, amount
                        , "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
                addItemToMap(pretransactionsMap, day, preTransaction);
            }
        }
    }

    private void createMonthlyAtmTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        int occasions = random.nextInt(6);
        for (int i = 0; i < occasions; i++) {
            LocalDate day = daysInCurrentMonth.get(random.nextInt(daysInCurrentMonth.size()));
            int amount = 5000 + random.nextInt(45001);
            Timestamp timestamp = TimestampGenerator.generate(random, day);
            atmSelector.setHomeRatePercent(90);
            atmSelector.setPrivateBankPercent(70);
            ATM atm = atmSelector.selectAtm(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.ATM, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", atm.getATMcode());
            addItemToMap(pretransactionsMap, day, preTransaction);
        }
    }

    private void createMonthlyNetTransaction(Map<LocalDate, List<PreTransaction>> pretransactionsMap, Person person, CurrentYear currentYear, Month month, List<LocalDate> daysInCurrentMonth, Random random) {
        int occasions = 10 + random.nextInt(6);
        for (int i = 0; i < occasions; i++) {
            LocalDate day = daysInCurrentMonth.get(random.nextInt(daysInCurrentMonth.size()));
            int amount = 2000 + random.nextInt(38001);
            Timestamp timestamp = TimestampGenerator.generate(random, day);
            VendorSelector vendorSelector = new OrdinaryVendorSelector(95);
            Vendor vendor = vendorSelector.selectVendor(person);
            PreTransaction preTransaction = new PreTransaction(person.getCardNumber(), TransactionType.NET, timestamp, amount
                    , "HUF", ResponseCode.OK, "HU", vendor.getVendorCode());
            addItemToMap(pretransactionsMap, day, preTransaction);
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
