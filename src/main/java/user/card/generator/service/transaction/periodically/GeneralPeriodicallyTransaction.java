package user.card.generator.service.transaction.periodically;

import user.card.generator.domain.transaction.SimplePreTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public abstract class GeneralPeriodicallyTransaction {

    public abstract Map<LocalDate,List<SimplePreTransaction>> processTypedTransaction(List<LocalDate> days);
}
