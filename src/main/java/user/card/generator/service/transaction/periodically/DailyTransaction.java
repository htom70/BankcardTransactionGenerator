package user.card.generator.service.transaction.periodically;

import user.card.generator.service.transaction.kind.GeneralTypedTransaction;

public class DailyTransaction extends GeneralPeriodicallyTransaction {

    @Override
    public void processTypedTransaction() {
        System.out.println("daily transaction");
    }
}
