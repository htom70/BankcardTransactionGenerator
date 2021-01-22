package user.card.generator.service.transaction.kind;

import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.service.transaction.periodically.GeneralPeriodicallyTransaction;
import user.card.generator.service.transactionhandler.VendorAndAtmSelector;

import java.util.ArrayList;
import java.util.List;

public  abstract class GeneralTypedTransaction {

    protected TransactionType transactionType;
    protected VendorAndAtmSelector vendorAndAtmSelector;
    protected List<GeneralPeriodicallyTransaction> generalPeriodicallyTransactions = new ArrayList<>();

    public abstract void process();

    public void addGeneralPeriodicallyTransaction(GeneralPeriodicallyTransaction generalPeriodicallyTransaction) {
        generalPeriodicallyTransactions.add(generalPeriodicallyTransaction);
    }

}
