package user.card.generator.service.transaction.periodically;

import user.card.generator.domain.transaction.SimplePreTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OnceMonthlyTransaction extends GeneralPeriodicallyTransaction{

    @Override
    public Map<LocalDate, List<SimplePreTransaction>> processTypedTransaction(List<LocalDate> days) {
        return null;
    }
}
