//package user.card.generator.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import user.card.generator.domain.AtmOwnerBank;
//import user.card.generator.domain.ProductCategory;
//import user.card.generator.domain.ResponseCode;
//import user.card.generator.domain.vendor.AbstractVendor;
//import user.card.generator.domain.person.Person;
//import user.card.generator.domain.person.PersonCategory;
//import user.card.generator.domain.transaction.Transaction;
//import user.card.generator.domain.transaction.TransactionType;
//import user.card.generator.repository.PersonRepository;
//import user.card.generator.repository.TransactionRepository;
//import user.card.generator.repository.VendorRepository;
//import user.card.generator.time.CurrentYear;
//import user.card.generator.time.TimestampGenerator;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.*;
//
//public class OrdinaryUserDontUseCardGeneratorService {
//
//    @Value("${ordinaryUserDontUseCard.limitRateToIncome.Min}")
//    private double ordinaryUserDontUseCardLimitRateToIncomeMin;
//    @Value("${ordinaryUserDontUseCard.limitRateToIncome.Max}")
//    private double ordinaryUserDontUseCardLimitRateToIncomeMax;
//    @Value("${ordinaryUserDontUseCard.limitRateToIncomeInChristmasPeriod}")
//    private double ordinaryUserDontUseCardLimitRateToIncomeInChristmasPeriod;
//    //POS daily
//    @Value("${ordinaryUserDontUseCard.Pos.monthly.occasion.Min}")
//    private int ordinaryUserDontUseCardPosMonthlyOccasionMin;
//    @Value("${ordinaryUserDontUseCard.Pos.monthly.occasion.Max}")
//    private int ordinaryUserDontUseCardPosMonthlyOccasionMax;
//    @Value("${ordinaryUserDontUseCard.Pos.monthly.amount.Min}")
//    private int ordinaryUserDontUseCardPosMonthlyAmountMin;
//    @Value("${ordinaryUserDontUseCard.Pos.monthly.amount.Max}")
//    private int ordinaryUserDontUseCardPosMonthlyAmountMax;
//    //POS yearly
//    @Value("${ordinaryUserDontUseCard.Pos.yearly.occasion.Max}")
//    private int ordinaryUserDontUseCardPosYearlyOccasionMax;
//    @Value("${ordinaryUserDontUseCard.Pos.yearly.amount.Min}")
//    private int ordinaryUserDontUseCardPosYearlyAmountMin;
//    @Value("${ordinaryUserDontUseCard.Pos.yearly.amount.Max}")
//    private int ordinaryUserDontUseCardPosYearlyAmountMax;
//    //ATM
//    @Value("${ordinaryUserDontUseCard.ATM.yearly.occasion.Min}")
//    private int ordinaryUserDontUseCardATMyearlyOccasionMin;
//    @Value("${ordinaryUserDontUseCard.ATM.yearly.occasion.Max}")
//    private int ordinaryUserDontUseCardATMyearlyOccasionMax;
//    @Value("${ordinaryUserDontUseCard.ATM.yearly.amount.Min}")
//    private int ordinaryUserDontUseCardATMyearlyAmountMin;
//    @Value("${ordinaryUserDontUseCard.ATM.yearly.amount.Max}")
//    private int ordinaryUserDontUseCardATMyearlyAmountMax;
//
//    @Autowired
//    PersonRepository personRepository;
//
//    @Autowired
//    VendorRepository vendorRepository;
//
//    @Autowired
//    TransactionRepository transactionRepository;
//
//    public OrdinaryUserDontUseCardGeneratorService(PersonRepository personRepository, VendorRepository vendorRepository, TransactionRepository transactionRepository) {
//        this.personRepository = personRepository;
//        this.vendorRepository = vendorRepository;
//        this.transactionRepository = transactionRepository;
//    }
//
//
//    public void generate(Random random, CurrentYear currentYear) {
//        List<Person> ordinaryUserDontUseCardPeople = personRepository.findPeopleUponCategory(PersonCategory.ORDINARY_USER_DONT_USE_CARD);
//        for (Person person : ordinaryUserDontUseCardPeople) {
//            generateCurrentPersonTransactions(person, random, currentYear);
//        }
//    }
//
//    private void generateCurrentPersonTransactions(Person person, Random random, CurrentYear currentYear) {
//        Map<LocalDate, List<Transaction>> preTransactions = new HashMap<>();
//        List<Transaction> finalTransactions = new ArrayList<>();
//        double limitRate = (100 * ordinaryUserDontUseCardLimitRateToIncomeMin +
//                random.nextInt((int) (100 * ordinaryUserDontUseCardLimitRateToIncomeMax - 100 * ordinaryUserDontUseCardLimitRateToIncomeMin))) / 100;
//        int limitToIncome = (int) (person.getIncome() * limitRate);
//        int limitToIncomeInChristmasPeriod = (int) (person.getIncome() * ordinaryUserDontUseCardLimitRateToIncomeInChristmasPeriod);
//        addToPreTransactionMap(preTransactions, generateYearlyPosTransactions(person, random, currentYear));
//        addToPreTransactionMap(preTransactions, generateYearlyATMTransactions(person, random, currentYear));
//        for (int i = 1; i <= 12; i++) {
//            int limit;
//            int sum = 0;
//            if (i == 12) {
//                limit = limitToIncomeInChristmasPeriod;
//            } else limit = limitToIncome;
//            addToPreTransactionMap(preTransactions, generateDailyPosTransactionsInMonth(person, random, currentYear, i));
//            //TO-DO limit ellenőrzés
//            List<LocalDate> daysOfMonth = currentYear.getDaysOfMonth(Month.of(i));
//            List<Transaction> transactionsOfDay;
//            for (LocalDate date : daysOfMonth) {
//                if ((transactionsOfDay = preTransactions.get(date)) != null) {
//                    for (Transaction actualTransactionOfDay : transactionsOfDay) {
//                        int temp = sum + actualTransactionOfDay.getAmount();
//                        if (temp < limit) {
//                            sum = temp;
//                            finalTransactions.add(actualTransactionOfDay);
//                        }
//                    }
//                }
//            }
//            for (Transaction finalTransaction : finalTransactions) {
//                transactionRepository.save(finalTransaction);
//                person.getTransactions().add(finalTransaction);
//            }
//        }
//        personRepository.save(person);
//    }
//
//    private void addToPreTransactionMap(Map<LocalDate, List<Transaction>> preTransactions, List<Transaction> transactions) {
//        for (Transaction actualTransaction : transactions) {
//            Timestamp timestamp = actualTransaction.getTimestamp();
//            LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
//            if (preTransactions.get(localDate) == null) {
//                List<Transaction> dayTransactions = new ArrayList<>();
//                dayTransactions.add(actualTransaction);
//            } else {
//                preTransactions.get(localDate).add(actualTransaction);
//            }
//        }
//    }
//
//    private List<Transaction> generateYearlyPosTransactions(Person person, Random random, CurrentYear currentYear) {
//        List<Transaction> transactions = new ArrayList<>();
//        List<LocalDate> days = currentYear.getDays();
//        int occasion = random.nextInt(ordinaryUserDontUseCardPosYearlyOccasionMax + 1);
//        for (int i = 0; i < occasion; i++) {
//            LocalDate date = days.get(random.nextInt(days.size()));
//            Timestamp timestamp = TimestampGenerator.generate(random, date);
//            int amount = ordinaryUserDontUseCardPosYearlyAmountMin + random.nextInt(ordinaryUserDontUseCardPosYearlyAmountMax - ordinaryUserDontUseCardPosYearlyAmountMin);
//            String vendorCode = getVendorCode(random);
//            AtmOwnerBank atmOwnerBank = getCurrentAtmOwnerBank(random);
//            ProductCategory productCategory = getActualProductCategoryWithoutCash(random);
//            Transaction transaction = new Transaction(person.getCardNumber(), TransactionType.POS, timestamp, amount, "HUF", ResponseCode.OK, "HU", vendorCode, productCategory, atmOwnerBank);
//            transactions.add(transaction);
//        }
//        return transactions;
//    }
//
//    private List<Transaction> generateDailyPosTransactionsInMonth(Person person, Random random, CurrentYear currentYear, int monthOrdinalNumber) {
//        List<Transaction> transactions = new ArrayList<>();
//        List<LocalDate> daysInMonth = currentYear.getDaysOfMonth(Month.of(monthOrdinalNumber));
//        int occasion = ordinaryUserDontUseCardPosMonthlyOccasionMin + random.nextInt(ordinaryUserDontUseCardPosMonthlyOccasionMax - ordinaryUserDontUseCardPosMonthlyOccasionMin);
//        for (int i = 0; i < occasion; i++) {
//            LocalDate actualDate = daysInMonth.get(random.nextInt(daysInMonth.size()));
//            Timestamp timestamp = TimestampGenerator.generate(random, actualDate);
//            int amount = ordinaryUserDontUseCardPosMonthlyAmountMin + random.nextInt(ordinaryUserDontUseCardPosMonthlyAmountMax - ordinaryUserDontUseCardPosMonthlyAmountMin);
//            String vendorCode = getVendorCode(random);
//            AtmOwnerBank atmOwnerBank = getCurrentAtmOwnerBank(random);
//            ProductCategory productCategory = getActualProductCategoryWithoutCash(random);
//            Transaction transaction = new Transaction(person.getCardNumber(), TransactionType.POS, timestamp, amount, "HUF", ResponseCode.OK, "HU", vendorCode, productCategory, atmOwnerBank);
//            transactions.add(transaction);
//        }
//        return transactions;
//    }
//
//    private List<Transaction> generateYearlyATMTransactions(Person person, Random random, CurrentYear currentYear) {
//        List<Transaction> transactions = new ArrayList<>();
//        List<LocalDate> days = currentYear.getDays();
//        int occasion = ordinaryUserDontUseCardATMyearlyOccasionMin + random.nextInt(ordinaryUserDontUseCardATMyearlyOccasionMax - ordinaryUserDontUseCardATMyearlyOccasionMin);
//        for (int i = 0; i < occasion; i++) {
//            LocalDate actualDate = days.get(i);
//            Timestamp timestamp = TimestampGenerator.generate(random, actualDate);
//            int amount = ordinaryUserDontUseCardATMyearlyAmountMin + random.nextInt(ordinaryUserDontUseCardATMyearlyAmountMax - ordinaryUserDontUseCardATMyearlyAmountMin);
//            String vendorCode = getVendorCode(random);
//            AtmOwnerBank atmOwnerBank = getCurrentAtmOwnerBank(random);
//            ProductCategory productCategory = ProductCategory.CASH;
//            Transaction transaction = new Transaction(person.getCardNumber(), TransactionType.ATM, timestamp, amount, "HUF", ResponseCode.OK, "HU", vendorCode, productCategory, atmOwnerBank);
//            transactions.add(transaction);
//        }
//        return transactions;
//    }
//
//
//    private AtmOwnerBank getCurrentAtmOwnerBank(Random random) {
//        AtmOwnerBank[] atmOwnerBanks = AtmOwnerBank.values();
//        AtmOwnerBank atmOwnerBank = atmOwnerBanks[random.nextInt(atmOwnerBanks.length)];
//        return atmOwnerBank;
//    }
//
//    private ProductCategory getActualProductCategoryWithoutCash(Random random) {
//        ProductCategory[] productCategories = ProductCategory.values();
//        List<ProductCategory> productCategoryList = Arrays.asList(productCategories);
//        productCategoryList.remove(ProductCategory.CASH);
//        ProductCategory productCategory = productCategoryList.get(random.nextInt(productCategoryList.size()));
//        return productCategory;
//    }
//
//    private String getVendorCode(Random random) {
//        List<AbstractVendor> vendors = vendorRepository.findAll();
//        String vendorCode = (vendors.get(random.nextInt(vendors.size()))).getVendorCode();
//        return vendorCode;
//    }
//
//}
