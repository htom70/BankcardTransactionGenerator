package user.card.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import user.card.generator.batch.person.PersonBatchInsertAsync;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.person.PersonCategory;
import user.card.generator.repository.ATMrepository;
import user.card.generator.repository.BankRepository;
import user.card.generator.repository.TransactionRepository;
import user.card.generator.repository.VendorRepository;
import user.card.generator.service.*;
import user.card.generator.service.transaction.container.TransactionContainer;
import user.card.generator.service.transaction.fraud.FraudContainer;
import user.card.generator.time.CurrentYear;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@SpringBootApplication
public class GeneratorApplication implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private UserGenerator userGenerator;

    @Autowired
    CSVHandlerService csvHandlerService;

    @Autowired
    CityService cityService;

    @Autowired
    TransactionContainer transactionContainer;

    @Autowired
    FraudContainer fraudContainer;

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

    @Autowired
    PersonService personService;

    @Autowired
    TransactionService transactionService;


    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        CurrentYear currentYear = new CurrentYear(2012);

        if (countryService.findAll().isEmpty()) {
            Instant start = Instant.now();
            System.out.println("Országkódok és devizanem generálás kezdete.");
            countryService.generateCountries();
            System.out.println("Országkódok és devizanem generálás vége.");
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis();
            System.out.println("Országok és devizanemek generálási ideje: " + elapsedTime + " ms");
        }

        Country hu = countryService.findByCountryCode("HU");
        if (bankService.findAllByCountry(hu).isEmpty()) {
            Instant start = Instant.now();
            System.out.println("Bank generálás kezdete.");
            bankService.generateHungarianBanks();
            System.out.println("Bank generálás vége.");
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis();
            System.out.println("Bankok generálási ideje: " + elapsedTime + " ms");
        }

        if (cityService.findAllByCountry(hu).isEmpty()) {
            Instant start = Instant.now();
            System.out.println("Városok beolvasásának kezdete csv-ből.");
            cityService.citiesReadFromCsv();
            System.out.println("Városok beolvasásának vége csv-ből.");
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis();
            System.out.println("Városok generálási ideje: " + elapsedTime + " ms");
        }

        if (vendorService.findAll().isEmpty()) {
            Instant start = Instant.now();
            System.out.println("Vendor generálás kezdete.");
            vendorService.generateVendorsInHungary();
            System.out.println("Vendor generálás vége.");
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis();
            System.out.println("Vendor generálási ideje: " + elapsedTime + " ms");
            System.out.println("Generált vendorok száma: " + vendorService.findAll().size());

        }

        if (atMservice.findAll().isEmpty()) {
            Instant start = Instant.now();
            System.out.println("ATM generálás kezdete.");
            atMservice.generateHungarianATMs();
            System.out.println("ATM generálás vége.");
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis();
            System.out.println("ATM generálási ideje: " + elapsedTime + " ms");
            System.out.println("Generált ATM-ek száma: " + atMservice.findAll().size());
        }

        if (personService.findAll().isEmpty()) {
            Instant start = Instant.now();
            System.out.println("User generálás kezdete.");
            userGenerator.generatePerson(random);
            System.out.println("User generálás vége.");
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis();
            System.out.println("User generálási ideje: " + elapsedTime + " ms");
            System.out.println("Generált User-ek száma: " + personService.findAll().size());
            System.out.println("Kártyát használó nyugdíjasok száma: " + personService.findPeopleUponCategory(PersonCategory.RETIRED_USE_CARD_AND_INTERNET).size());
            System.out.println("Kártyát használó, internetet nem használó nyugdíjasok száma: " + personService.findPeopleUponCategory(PersonCategory.RETIRED_DONT_USE_CARD_AND_INTERNET).size());
            System.out.println("Kártyát és netet használó felhasználó: " + personService.findPeopleUponCategory(PersonCategory.RETIRED_USE_CARD_AND_INTERNET).size());
            System.out.println("Kártyát használó, netet nem használó felhasználó: " + personService.findPeopleUponCategory(PersonCategory.ORDINARY_USER_USE_CARD_DONT_USE_INTERNET).size());
            System.out.println("Kártyát és netet nem használó felhasználó: " + personService.findPeopleUponCategory(PersonCategory.ORDINARY_USER_DONT_USE_CARD).size());
            System.out.println("VIP felhasználó: " + personService.findPeopleUponCategory(PersonCategory.VIP_USER).size());
        }

        int numberOfTransaction = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM transaction", Integer.class);
        if (numberOfTransaction<1) {
            Instant start = Instant.now();
            System.out.println("Tranzakció generálás kezdete.");
            transactionContainer.process(currentYear);
            System.out.println("Tranzakció generálás vége.");
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis();
            System.out.println("Tranzakció generálási ideje: " + elapsedTime + " ms");
            System.out.println("Generált tranzakciók száma: " + transactionService.findAll().size());
        }

//        fraudContainer.process(currentYear);

//        csvHandlerService.writeData("C:\\Temp\\reduced_transaction.csv");

    }
}
