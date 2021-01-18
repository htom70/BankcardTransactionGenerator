package user.card.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import user.card.generator.batch.person.PersonBatchInsertAsync;
import user.card.generator.domain.BankInHungary;
import user.card.generator.domain.country.VendorGenerator;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Bank;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.repository.ATMrepository;
import user.card.generator.repository.BankRepository;
import user.card.generator.repository.TransactionRepository;
import user.card.generator.repository.VendorRepository;
import user.card.generator.service.*;
import user.card.generator.time.CurrentYear;

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



//        vendorGenerator.generate();
        cityService.citiesReadFromCsv();
//        userGenerator.generatePerson(random);
//        retiredDontUseCardTransactionGeneratorService.generate(random,currentYear.getYear());
//        retiredUseCardTransactionGeneratorService.generate(random, currentYear);
//        csvHandlerService.writeData("C:\\Users\\machine\\Documents\\MKI\\transaction.csv");
//        probeUserGenerator.generate(random);
        vendorRepository.save(new Vendor("firstVendor"));

        bankService.generateHungarianBanks();
        ATM atm0 = new ATM("First");
        ATM atm1 = new ATM("Second");
        atm0.setAtmOwnerBank(bankRepository.findByName("OTP"));
        atm1.setAtmOwnerBank(bankRepository.findByName("OTP"));

        atMrepository.save(atm0);
        atMrepository.save(atm1);

    }
}
