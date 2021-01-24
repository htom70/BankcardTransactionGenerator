package user.card.generator.service.transaction.kind;

import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.service.transaction.periodically.GeneralPeriodicallyTransaction;
import user.card.generator.service.transaction.vendorandatm.AtmSelector;
import user.card.generator.service.transaction.vendorselector.VendorSelector;
import user.card.generator.service.transactionhandler.VendorAndAtmSelector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  abstract class GeneralTypedTransaction {

    protected TransactionType transactionType;
    protected VendorAndAtmSelector vendorAndAtmSelector;
    protected List<GeneralPeriodicallyTransaction> generalPeriodicallyTransactions = new ArrayList<>();
    protected Map<GeneralPeriodicallyTransaction, VendorSelector> periodicallyTransactionVendorSelectors = new HashMap<>();
    protected Map<GeneralPeriodicallyTransaction, AtmSelector> periodicallyTransactionAtmSelectors = new HashMap<>();

    public abstract void process();

    public void addGeneralPeriodicallyTransaction(GeneralPeriodicallyTransaction generalPeriodicallyTransaction) {
        generalPeriodicallyTransactions.add(generalPeriodicallyTransaction);
    }

}
