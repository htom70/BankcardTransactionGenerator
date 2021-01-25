package user.card.generator.service.transaction.container;

import org.springframework.beans.factory.annotation.Autowired;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.service.TransactionService;
import user.card.generator.service.transaction.vendorandatm.AtmSelector;
import user.card.generator.time.CurrentYear;
import user.card.generator.time.TimestampGenerator;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class RetiredDontUseCardTransaction {

    @Autowired
    TransactionService transactionService;

    public void processTransaction(List<Person> people, CurrentYear currentYear) {
        Random random = new Random();
        List<Transaction> transactions = new ArrayList<>();
        for (Person person : people) {
            for (int i = 1; i <=12 ; i++) {
                int ordinalDayNumber = 10 + random.nextInt(4);
                LocalDate date = LocalDate.of(currentYear.getYear().getValue(), i, ordinalDayNumber);
                int amount = person.getIncome();
                Timestamp timestamp = TimestampGenerator.generate(random, date);
                AtmSelector atmSelector = new AtmSelector(100, 100);
                ATM atm=atmSelector.selectAtm(person);
                Transaction transaction=new Transaction(person.getCardNumber(), TransactionType.ATM,timestamp,amount,"HUF", ResponseCode.OK,"HU",atm.getATMcode());
                transactions.add(transaction);
            }
        }
        transactionService.saveAll(transactions);
    }
}

