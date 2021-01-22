package user.card.generator.service.transaction.kind;

import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.service.transaction.periodically.GeneralPeriodicallyTransaction;

public class PosTypedTransaction extends GeneralTypedTransaction {

    public PosTypedTransaction() {
        this.transactionType = TransactionType.POS;
    }

    @Override
    public void process() {
        System.out.println("POS");
        for (GeneralPeriodicallyTransaction t : generalPeriodicallyTransactions) {
            t.processTypedTransaction();
        }
    }

}


