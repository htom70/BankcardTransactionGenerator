package user.card.generator.service.transaction.kind;

import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.service.transaction.periodically.GeneralPeriodicallyTransaction;
import user.card.generator.service.transaction.vendorandatm.AtmSelector;

public class AtmTypedTransaction extends GeneralTypedTransaction {

    private int transactionInHomeCityRatePercent;
    private int transactionInPrivateBankRatePercent;

    private AtmSelector atmSelector;

    public AtmTypedTransaction() {
        this.transactionType = TransactionType.ATM;
    }

    public void setAtmSelector(AtmSelector atmSelector) {
        this.atmSelector = atmSelector;
    }

    @Override
    public void process() {
        System.out.println("ATM");
        for (GeneralPeriodicallyTransaction t : generalPeriodicallyTransactions) {
            t.processTypedTransaction();
        }
    }
}
