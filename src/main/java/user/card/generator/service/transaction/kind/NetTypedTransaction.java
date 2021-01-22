package user.card.generator.service.transaction.kind;

import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.service.transaction.periodically.GeneralPeriodicallyTransaction;

public class NetTypedTransaction extends GeneralTypedTransaction {

    public NetTypedTransaction() {
        this.transactionType = TransactionType.NET;
    }

    @Override
    public void process() {
        System.out.println("NET");
        for (GeneralPeriodicallyTransaction t : generalPeriodicallyTransactions) {
            t.processTypedTransaction();
        }
    }
}
