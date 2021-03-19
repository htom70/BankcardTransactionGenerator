package user.card.generator.service.transaction.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.city.City;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.service.TransactionService;
import user.card.generator.service.transaction.vendorandatm.AtmSelector;
import user.card.generator.time.CurrentYear;
import user.card.generator.time.TimestampGenerator;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RetiredDontUseCardTransaction {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AtmSelector atmSelector;

    public void processTransaction(List<Person> people, CurrentYear currentYear, List<City> cities) {
        Random random = new Random(3);
        atmSelector.setHomeRatePercent(100);
        atmSelector.setPrivateBankPercent(100);
        Instant start = Instant.now();
        for (Person person : people) {
            List<Transaction> transactions = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                int ordinalDayNumber = 10 + random.nextInt(4);
                LocalDate date = LocalDate.of(currentYear.getYear().getValue(), i, ordinalDayNumber);
                int amount = person.getIncome();
                Timestamp timestamp = TimestampGenerator.generate(random, date);
                ATM atm = atmSelector.selectAtm(person);
                Transaction transaction = new Transaction(person.getCardNumber(), TransactionType.ATM, timestamp, amount, "HUF", ResponseCode.OK, "HU", atm.getATMcode());
                transaction.setAllFields(cities);
                transaction.setFraud(false);
                transactions.add(transaction);
            }
            System.out.println("Kártyát nem használó nyugdíjas tranzakcióinak száma: " + transactions.size());
            transactionService.saveAll(transactions);
        }
        Instant end = Instant.now();
        long elapsedTime = Duration.between(start, end).toMillis() / 1000;
        System.out.println("Kártyát nem használó nyugdíjasok tranzakció generálás időszükséglete: " + elapsedTime + " másodperc");    }
}

