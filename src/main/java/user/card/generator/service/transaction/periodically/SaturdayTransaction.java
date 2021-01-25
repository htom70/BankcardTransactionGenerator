package user.card.generator.service.transaction.periodically;

import user.card.generator.domain.transaction.SimplePreTransaction;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SaturdayTransaction extends GeneralTimeTypedTransaction {

    @Override
    public Map<LocalDate, List<SimplePreTransaction>> processTimeTypedTransaction(List<LocalDate> days) {
        Random random = new Random();
        List<SimplePreTransaction> simplePreTransactions = new ArrayList<>();
        Map<LocalDate, List<SimplePreTransaction>> result = new HashMap<>();
        List<LocalDate> saturdays = days.stream()
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY)
                .collect(Collectors.toList());
        for (LocalDate saturday : saturdays) {
            int amount = 10000 + random.nextInt(5000);
            SimplePreTransaction simplePreTransaction = new SimplePreTransaction(saturday, amount);
            simplePreTransactions.add(simplePreTransaction);
            result.put(saturday, simplePreTransactions);
        }
        return result;
    }
}
