package user.card.generator.service.transaction.periodically;

import user.card.generator.domain.transaction.SimplePreTransaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public abstract class GeneralPeriodicallyTransaction {

//    Map<LocalDate, List<SimplePreTransaction>> generatePretransactions();

    public abstract void processTypedTransaction();
}
