package user.card.generator.domain.transaction.retireddontusecard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.SimpleTransaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.repository.PersonRepository;
import user.card.generator.time.CurrentYear;

import java.time.LocalDate;
import java.util.*;

@Component
public class RetiredDontUseCardTransactionHandler {

    private static final int RETIRED_DONT_USE_CARD_NUMBER = 10;

    @Value("${retiredDontUseCard.minIncome}")
    private int retiredDontUseCardMinIncome;
//    @Value("${retiredDontUseCard.avarageIncome}")
//    private int retiredDontUseCardAvarageIncome;
    @Value("${retiredDontUseCard.maxIncome}")
    private int retiredDontUseCardMaxIncome;
    @Value("${retiredDontUseCard.ATM.monthly.transactionDate.Min}")
    private int retiredDontUseCardATMmonthlyTransactionDateMin;
    @Value("${retiredDontUseCard.ATM.monthly.transactionDate.Max}")
    private int retiredDontUseCardATMmonthlyTransactionDateMax;
    @Value("${retiredDontUseCard.ATM.monthly.amount.Min}")
    private int retiredDontUseCardATMmonthlyAmountMin;
    @Value("${retiredDontUseCard.ATM.monthly.amount.Max}")
    private int retiredDontUseCardATMmonthlyAmountMax;

    @Autowired
    PersonRepository personRepository;


    public RetiredDontUseCardTransactionHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void generate(CurrentYear currentYear, Random random) {
        List<LocalDate> days = currentYear.getDays();
        List<Integer> cashWithdrawals = new ArrayList<>();
        cashWithdrawals.add(retiredDontUseCardATMmonthlyAmountMin);
        cashWithdrawals.add(retiredDontUseCardATMmonthlyAmountMax);


        for (int i = 0; i < RETIRED_DONT_USE_CARD_NUMBER; i++) {
            String cardNumber;
            do {
                cardNumber = generateCardNumberString(random);
            } while (personRepository.findByCardNumber(cardNumber) != null);
            Person person = new Person(cardNumber);
            personRepository.save(person);

            Set<SimpleTransaction> transactions = new HashSet<>();

            for (int j = 0; j <12 ; j++) {
                int currentDayOrdinalNumber = retiredDontUseCardATMmonthlyTransactionDateMin
                        + random.nextInt(retiredDontUseCardATMmonthlyTransactionDateMax-retiredDontUseCardATMmonthlyTransactionDateMin);
                LocalDate localDate = LocalDate.of(currentYear.getYear().getValue(), j+1, currentDayOrdinalNumber);
                int amount = cashWithdrawals.get(random.nextInt(cashWithdrawals.size()));
                SimpleTransaction simpleTransaction = new SimpleTransaction(localDate, amount, TransactionType.ATM, ResponseCode.OK);
                transactions.add(simpleTransaction);
            }

        }
    }



    private String generateCardNumberString(Random random) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        String result = stringBuilder.toString();
        System.out.println("Card Number: " + result);
        return result;
    }
}
