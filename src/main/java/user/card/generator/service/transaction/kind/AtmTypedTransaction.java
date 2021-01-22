package user.card.generator.service.transaction.kind;

import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.service.transaction.periodically.GeneralPeriodicallyTransaction;

public class AtmTypedTransaction extends GeneralTypedTransaction {

    public AtmTypedTransaction() {
        this.transactionType = TransactionType.ATM;
    }

    @Override
    public void process() {
        System.out.println("ATM");
        for (GeneralPeriodicallyTransaction t : generalPeriodicallyTransactions) {
            t.processTypedTransaction();
        }
    }
}
