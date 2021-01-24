package user.card.generator.service.transaction.container;

import lombok.Data;
import user.card.generator.service.transaction.kind.GeneralTypedTransaction;

import java.util.ArrayList;
import java.util.List;

@Data
public class GeneralTransactionContainer {

    private List<GeneralTypedTransaction> transactionItems = new ArrayList<>();

    public void addGeneralTypedTransaction(GeneralTypedTransaction generalTypedTransaction) {
        transactionItems.add(generalTypedTransaction);
    }

    public void process() {
        for (GeneralTypedTransaction t : transactionItems) {
            t.process();
        }
    }

}
