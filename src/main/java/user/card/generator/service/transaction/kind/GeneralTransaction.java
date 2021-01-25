package user.card.generator.service.transaction.kind;

import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.service.transaction.periodically.GeneralTimeTypedTransaction;
import user.card.generator.service.transaction.vendorandatm.AtmSelector;
import user.card.generator.service.transaction.vendorselector.VendorSelector;

import java.util.HashMap;
import java.util.Map;

public  class GeneralTransaction {

    protected TransactionType transactionType;
    protected Map<GeneralTimeTypedTransaction, VendorSelector> periodicallyTransactionVendorSelectors = new HashMap<>();
    protected Map<GeneralTimeTypedTransaction, AtmSelector> periodicallyTransactionAtmSelectors = new HashMap<>();

    public GeneralTransaction(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void addVendorSelector(Map<GeneralTimeTypedTransaction, VendorSelector> selectorMap) {
        periodicallyTransactionVendorSelectors.putAll(selectorMap);
    }

    public void addAtmSelector(Map<GeneralTimeTypedTransaction, AtmSelector> selectorMap) {
        periodicallyTransactionAtmSelectors.putAll(selectorMap);
    }

    public void process() {

    }


}
