package user.card.generator.service.transactionhandler;

import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class DailyTransaction extends AbstractTransaction {

    private TransactionType transactionType;

    public DailyTransaction(TransactionType transactionType) {
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
            List<LocalDate> daysInCurrentmonth = days.stream()
                    .filter(date -> (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate)))
                    .collect(Collectors.toList());
            int numberOfDayInCurrentMonth = daysInCurrentmonth.size();
            int dayIndex = 0;
            LocalDate date = null;
            int amount = 0;
            while (dayIndex < numberOfDayInCurrentMonth) {
                date = daysInCurrentmonth.get(dayIndex);
                amount = amountMin + random.nextInt(amountMax - amountMin);
                dayIndex += occasionMin + random.nextInt(occasionMax - occasionMin);
            }
            SimplePreTransaction simplePreTransaction = new SimplePreTransaction(date, amount, transactionType);
            if (result.get(date) == null) {
                result.put(date, Arrays.asList(simplePreTransaction));
            } else result.get(date).add(simplePreTransaction);
        }
        return result;
    }
}

