package user.card.generator.service.transactionhandler;

import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class MonthlyTransaction extends AbstractTransaction {

    private TransactionType transactionType;

    public MonthlyTransaction(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public Map<LocalDate, List<SimplePreTransaction>> generate(List<LocalDate> days, TransactionProperty transactionProperty, Year year) {
        Random random = new Random();
        Map<LocalDate, List<SimplePreTransaction>> result = new HashMap<>();
        int occasionMin = transactionProperty.getMonthlyOccasionMin();
        int occasionMax = transactionProperty.getMonthlyOccasionMax();
        int amountMin = transactionProperty.getMonthlyAmountMin();
        int amountMax = transactionProperty.getMonthlyAmountMax();
        for (int i = 1; i < 12; i++) {
            LocalDate startDate = LocalDate.of(year.getValue(), i, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.getMonth().length(startDate.isLeapYear()));
            List<LocalDate> daysInCurrentmonth = days.stream()
                    .filter(date -> (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate)))
                    .collect(Collectors.toList());
            int numberOfDayInCurrentMonth = daysInCurrentmonth.size();
            int occasion = occasionMin + random.nextInt(occasionMax - occasionMin);
            for (int j = 0; j < occasion; j++) {
                int amount = amountMin + random.nextInt(amountMax - amountMin);
                LocalDate date;
                do {                                                //ne essen ugyanarra a napre több tranzakció
                    date = daysInCurrentmonth.get(random.nextInt(numberOfDayInCurrentMonth));
                } while (result.get(date) != null);
                SimplePreTransaction simplePreTransaction = new SimplePreTransaction(date, amount, transactionType);
                result.put(date, Arrays.asList(simplePreTransaction));
            }
        }
        return result;
    }
}
