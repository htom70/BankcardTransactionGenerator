package user.card.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import user.card.generator.batch.person.PersonBatchInsertAsync;
import user.card.generator.domain.BankInHungary;
import user.card.generator.domain.city.City;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.country.VendorGenerator;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.AbstractVendor;
import user.card.generator.domain.vendor.Bank;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.repository.ATMrepository;
import user.card.generator.repository.BankRepository;
import user.card.generator.repository.TransactionRepository;
import user.card.generator.repository.VendorRepository;
import user.card.generator.service.*;
import user.card.generator.service.transaction.container.TransactionContainer;
import user.card.generator.service.transaction.kind.*;
import user.card.generator.service.transaction.periodically.DailyTransaction;
import user.card.generator.service.transaction.periodically.PeriodicallyTransactionFactory;
import user.card.generator.service.transaction.periodically.PeriodicallyTransactionType;
import user.card.generator.time.CurrentYear;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class GeneratorApplication implements CommandLineRunner {

    @Autowired
    private VendorGenerator vendorGenerator;

    @Autowired
    private UserGenerator userGenerator;

    @Autowired
    RetiredDontUseCardTransactionGeneratorService retiredDontUseCardTransactionGeneratorService;

//    @Autowired
//    RetiredUseCardTransactionGeneratorService retiredUseCardTransactionGeneratorService;

    @Autowired
    CSVHandlerService csvHandlerService;

    @Autowired
    CityService cityService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PersonBatchInsertAsync personBatchInsertAsync;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    ATMrepository atMrepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BankService bankService;

    @Autowired
    VendorService vendorService;

    @Autowired
    CountryService countryService;

    @Autowired
    ATMservice atMservice;

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        CurrentYear currentYear = new CurrentYear(2008);
//        List<City> cities = csvHandlerService.citiesReadFromCsv();
//        cityBatchInsertAsync.batchInsertAsync(cities);
//        List<Person> people = new ArrayList<>();
//        for (int i = 0; i <1000000 ; i++) {
//            people.add(new Person("11", PersonCategory.VIP_USER, 1000000));
//        }
//        personBatchInsertAsync.batchInsertAsync(people);

//        vendorService.generateVendorsInHungary();

//        userGenerator.generatePerson(random);
//        retiredDontUseCardTransactionGeneratorService.generate(random,currentYear.getYear());
//        retiredUseCardTransactionGeneratorService.generate(random, currentYear);
//        csvHandlerService.writeData("C:\\Users\\machine\\Documents\\MKI\\transaction.csv");
//        probeUserGenerator.generate(random);
//
//        countryService.generateCountries();
//        cityService.citiesReadFromCsv();
//        bankService.generateHungarianBanks();
        Country country = countryService.findByCountryCode("HU");
//        System.out.println(country.toString());
        List<City> citiesInHungary = cityService.findAllByCountry(country);
//        System.out.println(citiesInHungary.size());
////        System.out.println(cityService.findAllByCountry(countryService.findByCountryCode("HU")));
//        List<String> bigCityNames = new ArrayList<String>() {{
//            add("Budapest");
//            add("Szeged");
//            add("Miskolc");
//            add("Debrecen");
//            add("Pécs");
//            add("Győr");
//            add("Nyíregyháza");
//
//        }};
//        List<City> cities = cityService.findByNameNotIn(bigCityNames);
//        System.out.println("Little cities count: " + cities.size());
//
//        List<City> bigCities = cityService.findByNameIn(bigCityNames);
//        System.out.println("Big Cities count: " + bigCities.size());
//        atMservice.generateHungarianATMs();
        System.out.println("IN");
        List<Vendor> vendorsInCity = vendorRepository.findAllByCity(citiesInHungary.get(1));
        System.out.println(vendorsInCity.size());
        System.out.println("NOT IN");
        List<Vendor> vendorsNotIn = vendorRepository.findAllByCityIsNot(citiesInHungary.get(1));
        System.out.println(vendorsNotIn.size());

        TransactionContainer transactionContainer = new TransactionContainer();
        GeneralTypedTransaction POSTypedTransaction = TypedTransactionFactory.create(TransactionType.POS);
        POSTypedTransaction.addGeneralPeriodicallyTransaction(PeriodicallyTransactionFactory.create(PeriodicallyTransactionType.DAILY));
        POSTypedTransaction.addGeneralPeriodicallyTransaction(PeriodicallyTransactionFactory.create(PeriodicallyTransactionType.MONTHLY));
        transactionContainer.addGeneralTypedTransaction(POSTypedTransaction);

        GeneralTypedTransaction ATMtypedTransaction = TypedTransactionFactory.create(TransactionType.ATM);
        ATMtypedTransaction.addGeneralPeriodicallyTransaction(PeriodicallyTransactionFactory.create(PeriodicallyTransactionType.INTER_DAILY));
        ATMtypedTransaction.addGeneralPeriodicallyTransaction(PeriodicallyTransactionFactory.create(PeriodicallyTransactionType.SATURDAY));
        transactionContainer.addGeneralTypedTransaction(ATMtypedTransaction);

        GeneralTypedTransaction NETtypedTransaction = TypedTransactionFactory.create(TransactionType.NET);
        NETtypedTransaction.addGeneralPeriodicallyTransaction(PeriodicallyTransactionFactory.create(PeriodicallyTransactionType.ONCE_MONTHLY));
        transactionContainer.addGeneralTypedTransaction(NETtypedTransaction);
        transactionContainer.process();

//        vendorService.generateVendorsInHungary();

//        cityService.findAllByNameIn(cities);


    }
}
