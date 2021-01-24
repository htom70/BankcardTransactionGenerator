package user.card.generator.service.transaction.container;

import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.service.transaction.kind.GeneralTransaction;
import user.card.generator.service.transaction.periodically.GeneralPeriodicallyTransaction;
import user.card.generator.service.transaction.periodically.PeriodicallyTransactionFactory;
import user.card.generator.service.transaction.periodically.PeriodicallyTransactionType;
import user.card.generator.service.transaction.vendorandatm.AtmSelector;

import java.util.HashMap;
import java.util.Map;

public class RetiredDontUseInternetTransactionContainer {

    public RetiredDontUseInternetTransactionContainer() {
        GeneralTransactionContainer generalTransactionContainer = new GeneralTransactionContainer();
        GeneralTransaction generalTransaction = new GeneralTransaction(TransactionType.ATM);
        AtmSelector atmSelector = new AtmSelector(100, 100);
        GeneralPeriodicallyTransaction onceMonthlyTransaction =
                PeriodicallyTransactionFactory.create(PeriodicallyTransactionType.ONCE_MONTHLY);
        Map<GeneralPeriodicallyTransaction, AtmSelector> atmSelectorMap = new HashMap<>();
        atmSelectorMap.put(onceMonthlyTransaction, atmSelector);
        generalTransaction.addAtmSelector(atmSelectorMap);
    }

    public void process() {

    }

}
