package user.card.generator.service.transactionhandler;

import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;

public class OnceMonthlyTransaction extends AbstractTransaction {

    private TransactionType transactionType;

    public OnceMonthlyTransaction(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public Map<LocalDate, List<SimplePreTransaction>> generate(List<LocalDate> days, TransactionProperty transactionProperty, TransactionType transactionType, Year year) {
        Random random = new Random();
        Map<LocalDate, List<SimplePreTransaction>> result = new HashMap<>();
        int startDayOrdinalNumber = transactionProperty.getOncemonthlyStartDateOrdinalNumberDayMin();
        int endDayOrdinalNumber = transactionProperty.getOncemonthlyStartDateOrdinalNumberDayMax();
        int amountMin = transactionProperty.getOnceMonthlyAmountMin();
        int amountMax = transactionProperty.getOnceMonthlyAmountMax();
        for (int i = 1; i < 12; i++) {
            LocalDate startDate = LocalDate.of(year.getValue(), i, 1);
            int dayOrdinalNumber = startDayOrdinalNumber + random.nextInt(endDayOrdinalNumber - startDayOrdinalNumber);
            int amount = amountMin + random.nextInt(amountMax - amountMin);
            LocalDate date = LocalDate.of(year.getValue(), i, dayOrdinalNumber);
            SimplePreTransaction simplePreTransaction = new SimplePreTransaction(date, amount, transactionType);
            if (result.get(date) == null) {
                result.put(date, Arrays.asList(simplePreTransaction));
            } else result.get(date).add(simplePreTransaction);
        }
        return result;
    }
}

