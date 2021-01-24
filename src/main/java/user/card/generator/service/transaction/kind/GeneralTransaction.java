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

public  class GeneralTransaction {

    protected TransactionType transactionType;
    protected Map<GeneralPeriodicallyTransaction, VendorSelector> periodicallyTransactionVendorSelectors = new HashMap<>();
    protected Map<GeneralPeriodicallyTransaction, AtmSelector> periodicallyTransactionAtmSelectors = new HashMap<>();

    public GeneralTransaction(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void addVendorSelector(Map<GeneralPeriodicallyTransaction, VendorSelector> selectorMap) {
        periodicallyTransactionVendorSelectors.putAll(selectorMap);
    }

    public void addAtmSelector(Map<GeneralPeriodicallyTransaction, AtmSelector> selectorMap) {
        periodicallyTransactionAtmSelectors.putAll(selectorMap);
    }

    public void process() {

    }


}
