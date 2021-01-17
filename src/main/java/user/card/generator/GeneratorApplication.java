package user.card.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import user.card.generator.batch.city.CityBatchInsertAsync;
import user.card.generator.batch.person.PersonBatchInsertAsync;
import user.card.generator.domain.AtmOwnerBank;
import user.card.generator.domain.ProductCategory;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.country.City;
import user.card.generator.domain.country.VendorGenerator;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.person.PersonCategory;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.repository.TransactionRepository;
import user.card.generator.service.*;
import user.card.generator.time.CurrentYear;
import user.card.generator.time.TimestampGenerator;

import java.time.LocalDate;
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

    @Autowired
    RetiredUseCardTransactionGeneratorService retiredUseCardTransactionGeneratorService;

    @Autowired
    CSVHandlerService csvHandlerService;

    @Autowired
    CityService cityService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CityBatchInsertAsync cityBatchInsertAsync;

    @Autowired
    PersonBatchInsertAsync personBatchInsertAsync;

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        CurrentYear currentYear = new CurrentYear(2008);
//        List<City> cities = csvHandlerService.citiesReadFromCsv();
//        cityBatchInsertAsync.batchInsertAsync(cities);
        List<Person> people = new ArrayList<>();
        for (int i = 0; i <1000000 ; i++) {
            people.add(new Person("11", PersonCategory.VIP_USER, 1000000));
        }
        personBatchInsertAsync.batchInsertAsync(people);



//        vendorGenerator.generate();
//        userGenerator.generatePerson(random);
//        retiredDontUseCardTransactionGeneratorService.generate(random,currentYear.getYear());
//        retiredUseCardTransactionGeneratorService.generate(random, currentYear);
//        csvHandlerService.writeData("C:\\Users\\machine\\Documents\\MKI\\transaction.csv");
//        probeUserGenerator.generate(random);

    }
}
