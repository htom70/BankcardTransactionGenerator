package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.card.generator.domain.AtmOwnerBank;
import user.card.generator.domain.ProductCategory;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.person.PersonCategory;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.repository.PersonRepository;
import user.card.generator.repository.TransactionRepository;
import user.card.generator.repository.VendorRepository;
import user.card.generator.time.CurrentYear;
import user.card.generator.time.TimestampGenerator;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

@Service
public class RetiredUseCardTransactionGeneratorService {

    @Value("${retiredUseCard.limitRateToIncome.Min}")
    private double retiredUseCardLimitRateToIncomeMin;
    @Value("${retiredUseCard.limitRateToIncome.Max}")
    private double retiredUseCardLimitRateToIncomeMax;
    @Value("${retiredUseCard.limitRateToIncomeInChristmasPeriod}")
    private double retiredUseCardLimitRateToIncomeInChristmasPeriod;
    //POS daily
    @Value("${retiredUseCard.Pos.daily.occasion.Min}")
    private int retiredUseCardPosDailyOccasionMin;
    @Value("${retiredUseCard.Pos.daily.occasion.Max}")
    private int retiredUseCardPosDailyOccasionMax;
    @Value("${retiredUseCard.Pos.daily.amount.Min}")
    private int retiredUseCardPosDailyAmountMin;
    @Value("${retiredUseCard.Pos.daily.amount.Max}")
    private int retiredUseCardPosDailyAmountMax;
    @Value("${retiredUseCard.Pos.daily.storeNumber.Max}")
    private int retiredUseCardPosDailyStoreNumberMax;
    //POS saturday
    @Value("${retiredUseCard.Pos.saturday.amount.Min}")
    private int retiredUseCardPosSaturdayAmountMin;
    @Value("${retiredUseCard.Pos.saturday.amount.Max}")
    private int retiredUseCardPosSaturdayAmountMax;
    @Value("${retiredUseCard.Pos.saturday.storeNumber.Min}")
    private int retiredUseCardPosSaturdayStoreNumberMin;
    @Value("${retiredUseCard.Pos.saturday.storeNumber.Max}")
    private int retiredUseCardPosSaturdayStoreNumberMax;
    //POS ChristmasDay
    @Value("${retiredUseCard.Pos.christmasPeriod.beginDay}")
    private int retiredUseCardPosChristmasPeriodBeginDay;
    @Value("${retiredUseCard.Pos.christmasPeriod.daily.occasion.Min}")
    private int retiredUseCardPosChristmasPeriodDailyOccasionMin;
    @Value("${retiredUseCard.Pos.christmasPeriod.daily.occasion.Max}")
    private int retiredUseCardPosChristmasPeriodDailyOccasionMax;
    @Value("${retiredUseCard.Pos.christmasPeriod.daily.amount.Min}")
    private int retiredUseCardPosChristmasPeriodDailyAmountMin;
    @Value("${retiredUseCard.Pos.christmasPeriod.daily.amount.Max}")
    private int retiredUseCardPosChristmasPeriodDailyAmountMax;
    //ATM
    @Value("${retiredUseCard.ATM.monthly.occasion.Min}")
    private int retiredUseCardATMmonthlyOccasionMin;
    @Value("${retiredUseCard.ATM.monthly.occasion.Max}")
    private int retiredUseCardATMmonthlyOccasionMax;
    @Value("${retiredUseCard.ATM.monthly.amount.Min}")
    private int retiredUseCardATMmonthlyAmountMin;
    @Value("${retiredUseCard.ATM.monthly.amount.Max}")
    private int retiredUseCardATMmonthlyAmountMax;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public RetiredUseCardTransactionGeneratorService(PersonRepository personRepository, VendorRepository vendorRepository,
                                                     TransactionRepository transactionRepository) {
        this.personRepository = personRepository;
        this.vendorRepository = vendorRepository;
        this.transactionRepository = transactionRepository;
    }

    private List<Person> retiredUseCardPeople = new ArrayList<>();

    public void generate(Random random, CurrentYear currentYear) {
        retiredUseCardPeople = personRepository.findPeopleUponCategory(PersonCategory.RETIRED_USE_CARD_AND_INTERNET);
        for (Person person : retiredUseCardPeople) {
            generateCurrentPersonTransactions(person, random, currentYear);
        }
    }

    private void generateCurrentPersonTransactions(Person person, Random random, CurrentYear currentYear) {
        double limitRate = (100*retiredUseCardLimitRateToIncomeMin +
                random.nextInt((int) (100 * retiredUseCardLimitRateToIncomeMax - 100 * retiredUseCardLimitRateToIncomeMin)))/100;
        int limitToIncome = (int) (person.getIncome() * limitRate);
        int limitToIncomeInChristmasPeriod = (int) (person.getIncome() * retiredUseCardLimitRateToIncomeInChristmasPeriod);
        for (int i = 1; i <= 12; i++) {
            int limit;
            int sum = 0;
            if (i == 12) {
                limit = limitToIncomeInChristmasPeriod;
            } else limit = limitToIncome;
            List<Transaction> preTransactions = new ArrayList<>();
            List<Transaction> finalTransactions = new ArrayList<>();
            preTransactions.addAll(generateDailyPosTransactionsInMonth(person, random, currentYear, i, limitToIncome, limitToIncomeInChristmasPeriod));
            preTransactions.addAll(generateSaturdayPosTransactionsInMonth(person, random, currentYear, i, limitToIncome, limitToIncomeInChristmasPeriod));
            preTransactions.addAll(generateATMTransactionsInMonth(person, random, currentYear, i, limitToIncome, limitToIncomeInChristmasPeriod));
            for (int j = 0; j < preTransactions.size(); j++) {
                Transaction actualTransaction = preTransactions.get(j);
                int amount = actualTransaction.getAmount();
                sum += amount;
                if (sum < limit) {
                    finalTransactions.add(actualTransaction);
                } else break;
            }
            for (Transaction finalTransaction : finalTransactions) {
                transactionRepository.save(finalTransaction);
                person.getTransactions().add(finalTransaction);
            }
        }
//        person.setTransactions(transactions);
        personRepository.save(person);

    }

    private List<Transaction> generateDailyPosTransactionsInMonth(Person person, Random random, CurrentYear currentYear, int monthOrdinalNumber, int limitToIncome, int limitToIncomeInChristmasPeriod) {
        List<Transaction> transactions = new ArrayList<>();
        List<Vendor> workDaysUsedVendors = new ArrayList<>();
        List<Vendor> vendors = vendorRepository.findAll();
        for (int i = 0; i < retiredUseCardPosDailyStoreNumberMax; i++) {
            workDaysUsedVendors.add(vendors.get(i));
        }
        List<LocalDate> daysInMonth = currentYear.getDaysInMonthWithoutSaturdays(Month.of(monthOrdinalNumber));
        int daysListSize = daysInMonth.size();
        int index = 0;
        while (index < daysListSize) {
            LocalDate actualDate = daysInMonth.get(index);
            Timestamp timestamp = TimestampGenerator.generate(random, actualDate);
            index += retiredUseCardPosDailyOccasionMin + random.nextInt(retiredUseCardPosDailyOccasionMax - retiredUseCardPosDailyOccasionMin);
            int amount = retiredUseCardPosDailyAmountMin + random.nextInt(retiredUseCardPosDailyAmountMax - retiredUseCardPosDailyAmountMin);
            String vendorCode = (workDaysUsedVendors.get(random.nextInt(retiredUseCardPosDailyStoreNumberMax))).getVendorCode();
            AtmOwnerBank atmOwnerBank = getCurrentAtmOwnerBank(random);
            ProductCategory productCategory = getActualProductCategory(random);
            Transaction transaction = new Transaction(person.getCardNumber(), TransactionType.POS, timestamp, amount, "HUF", ResponseCode.OK, "HU", vendorCode, productCategory, atmOwnerBank);
            transactions.add(transaction);
        }
        return transactions;
    }

    private List<Transaction> generateSaturdayPosTransactionsInMonth(Person person, Random random, CurrentYear currentYear, int monthOrdinalNumber, int limitToIncome, int limitToIncomeInChristmasPeriod) {
        List<Transaction> transactions = new ArrayList<>();
        List<Vendor> saturdaysUsedVendors = new ArrayList<>();
        List<Vendor> vendors = vendorRepository.findAll();
        for (int i = 0; i < retiredUseCardPosSaturdayStoreNumberMax; i++) {
            saturdaysUsedVendors.add(vendors.get(i));
        }
        int numberOfUsedPosOnSaturdays = retiredUseCardPosSaturdayStoreNumberMin + random.nextInt(retiredUseCardPosSaturdayStoreNumberMax - retiredUseCardPosSaturdayStoreNumberMin);
        int yearNumber = currentYear.getYear().getValue();
        YearMonth yearMonth = YearMonth.of(yearNumber, monthOrdinalNumber);
        List<LocalDate> saturdaysInMonth = currentYear.getSaturdaysInMonth(Month.of(monthOrdinalNumber));
        for (int i = 0; i < saturdaysInMonth.size(); i++) {
            LocalDate actualSaturday = saturdaysInMonth.get(i);
            Timestamp timestamp = TimestampGenerator.generate(random, actualSaturday);
            int amount = retiredUseCardPosSaturdayAmountMin + random.nextInt(retiredUseCardPosSaturdayAmountMax - retiredUseCardPosSaturdayAmountMin);
            String vendorCode = (saturdaysUsedVendors.get(random.nextInt(numberOfUsedPosOnSaturdays))).getVendorCode();
            AtmOwnerBank atmOwnerBank = getCurrentAtmOwnerBank(random);
            ProductCategory productCategory = getActualProductCategory(random);
            Transaction transaction = new Transaction(person.getCardNumber(), TransactionType.POS, timestamp, amount, "HUF", ResponseCode.OK, "HU", vendorCode, productCategory, atmOwnerBank);
            transactions.add(transaction);
        }
        return transactions;
    }

    private ProductCategory getActualProductCategory(Random random) {
        ProductCategory[] productCategories = ProductCategory.values();
        List<ProductCategory> productCategoryList= Arrays.asList(productCategories);
        productCategoryList.remove(ProductCategory.CASH);
        ProductCategory productCategory = productCategoryList.get(random.nextInt(productCategoryList.size()));
        return productCategory;
    }

    private List<Transaction> generateATMTransactionsInMonth(Person person, Random random, CurrentYear currentYear, int monthOrdinalNumber, int limitToIncome, int limitToIncomeInChristmasPeriod) {
        List<Transaction> transactions = new ArrayList<>();
        int yearNumber = currentYear.getYear().getValue();
        YearMonth yearMonthObject = YearMonth.of(yearNumber, monthOrdinalNumber);
        int daysOfMonth = yearMonthObject.lengthOfMonth();
        int occasion = retiredUseCardATMmonthlyOccasionMin
                + random.nextInt(retiredUseCardATMmonthlyOccasionMax - retiredUseCardATMmonthlyOccasionMin);
        for (int j = 0; j < occasion; j++) {
            LocalDate date = LocalDate.of(yearNumber, monthOrdinalNumber, random.nextInt(daysOfMonth) + 1);
            int amount = retiredUseCardATMmonthlyAmountMin + random.nextInt(retiredUseCardATMmonthlyAmountMax - retiredUseCardATMmonthlyAmountMin);
            Timestamp timestamp = TimestampGenerator.generate(random, date);
            List<Vendor> vendors = vendorRepository.findAll();
            int size = vendors.size();
            String vendorCode = (vendors.get(random.nextInt(size))).getVendorCode();
            AtmOwnerBank atmOwnerBank = getCurrentAtmOwnerBank(random);
            Transaction transaction = new Transaction(person.getCardNumber(), TransactionType.ATM, timestamp, amount, "HUF", ResponseCode.OK, "HU", vendorCode,
                    ProductCategory.CASH, atmOwnerBank);
            transactions.add(transaction);
        }
        return transactions;
    }

    private AtmOwnerBank getCurrentAtmOwnerBank(Random random) {
        AtmOwnerBank[] atmOwnerBanks = AtmOwnerBank.values();
        AtmOwnerBank atmOwnerBank = atmOwnerBanks[random.nextInt(atmOwnerBanks.length)];
        return atmOwnerBank;
    }
}
