package user.card.generator.service.transaction.kind;

import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.service.transaction.periodically.GeneralPeriodicallyTransaction;
import user.card.generator.service.transaction.vendorselector.VendorSelector;

public class PosTypedTransaction extends GeneralTypedTransaction {

    private int transactionInHomeCityRatePercent;

    private VendorSelector vendorSelector;

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


