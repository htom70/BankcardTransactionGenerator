package user.card.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import user.card.generator.domain.country.VendorGenerator;
import user.card.generator.service.RetiredDontUseCardTransactionGeneratorService;
import user.card.generator.service.RetiredUseCardTransactionGeneratorService;
import user.card.generator.service.UserGenerator;
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

    @Autowired
    RetiredUseCardTransactionGeneratorService retiredUseCardTransactionGeneratorService;

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();
        CurrentYear currentYear = new CurrentYear(2008);
//        vendorGenerator.generate();
//        userGenerator.generatePerson(random);
//        retiredDontUseCardTransactionGeneratorService.generate(random,currentYear.getYear());
        retiredUseCardTransactionGeneratorService.generate(random, currentYear);

    }
}
