package user.card.generator.service.transactionhandler;

import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class IntraDailyTransaction extends AbstractTransaction {

    private TransactionType transactionType;

    public IntraDailyTransaction(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public Map<LocalDate, List<SimplePreTransaction>> generate(List<LocalDate> days, TransactionProperty transactionProperty, Year year) {
        Random random = new Random();
        Map<LocalDate, List<SimplePreTransaction>> result = new HashMap<>();
        int intraDayOccasionMin = transactionProperty.getIntraDayOccasionMin();
        int intraDayOccasionMax = transactionProperty.getIntraDayOccasionMax();
        int intraDayAmountMin = transactionProperty.getIntraDayAmountMin();
        int intraDayAmountMax = transactionProperty.getIntraDayAmountMax();
        for (int i = 1; i < 12; i++) {
            LocalDate startDate = LocalDate.of(year.getValue(), i, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.getMonth().length(startDate.isLeapYear()));
            List<LocalDate> daysInCurrentmonth = days.stream()
                    .filter(date -> (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate)))
                    .collect(Collectors.toList());
            for (LocalDate date : daysInCurrentmonth) {
                int occasionUponCurrentDay = intraDayOccasionMin + random.nextInt(intraDayOccasionMax - intraDayOccasionMin);
                for (int j = 0; j < occasionUponCurrentDay; j++) {
                    int amount = intraDayAmountMin + random.nextInt(intraDayAmountMax - intraDayAmountMin);
                    SimplePreTransaction simplePreTransaction = new SimplePreTransaction(date, amount, transactionType);
                    if (result.get(date) == null) {
                        result.put(date, Arrays.asList(simplePreTransaction));
                    } else result.get(date).add(simplePreTransaction);
                }
            }
        }
        return result;
    }
}
