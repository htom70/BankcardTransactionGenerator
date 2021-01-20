package user.card.generator.service.transactionhandler;

import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class SaturdayTransaction extends AbstractTransaction {

    private TransactionType transactionType;

    public SaturdayTransaction(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public Map<LocalDate, List<SimplePreTransaction>> generate(List<LocalDate> days, TransactionProperty transactionProperty, TransactionType transactionType, Year year) {
        Random random = new Random();
        Map<LocalDate, List<SimplePreTransaction>> result = new HashMap<>();
        int occasionMin = transactionProperty.getDailyOccasionMin();
        int occasionMax = transactionProperty.getDailyOccasionMax();
        int amountMin = transactionProperty.getDailyAmountMin();
        int amountMax = transactionProperty.getDailyAmountMax();
        for (int i = 1; i < 12; i++) {
            LocalDate startDate = LocalDate.of(year.getValue(), i, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.getMonth().length(startDate.isLeapYear()));
            List<LocalDate> saturdays = days.stream()
                    .filter(date -> (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate)))
                    .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY)
                    .collect(Collectors.toList());
            int amount;
            for (LocalDate currentSaturday : saturdays) {
                amount = amountMin + random.nextInt(amountMax - amountMin);
                SimplePreTransaction simplePreTransaction = new SimplePreTransaction(currentSaturday, amount, transactionType);
                if (result.get(currentSaturday) == null) {
                    result.put(currentSaturday, Arrays.asList(simplePreTransaction));
                } else result.get(currentSaturday).add(simplePreTransaction);
            }
        }
        return result;
    }
}
