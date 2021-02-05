package user.card.generator.service.transaction.fraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.time.CurrentYear;

import java.time.Duration;
import java.time.Instant;

@Service
public class FraudContainer {

    @Autowired
    MultiplePosPayPassTransactionAtSameVendor multiplePosPayPassTransactionAtSameVendor;

    @Autowired
    MultiplePosPayPassTransactionInSameCity multiplePosPayPassTransactionInSameCity;

    public void process(CurrentYear currentYear) {
        Instant startStep1 = Instant.now();
        System.out.println("Azonos vendor csalás generálás kezdete.");
        multiplePosPayPassTransactionAtSameVendor.createFrauds(currentYear);
        Instant endStep1 = Instant.now();
        long elapsedTime = Duration.between(startStep1, endStep1).toMillis() / 1000;
        System.out.println("Azonos vendornál generált csalás tranzakciók előállítási ideje: " + elapsedTime + " másodperc");
        System.out.println("Adott városban elkövetett csalás generálás kezdete.");
        multiplePosPayPassTransactionInSameCity.createFrauds(currentYear);
        Instant endStep2 = Instant.now();
        elapsedTime = Duration.between(endStep1, endStep2).toMillis();
        System.out.println("Adott városban generált csalás tranzakciók előállítási ideje: " + elapsedTime + " másodperc");
    }
}
