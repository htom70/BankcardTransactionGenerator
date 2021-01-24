package user.card.generator.service.transaction.container;

import lombok.Data;
import user.card.generator.service.transaction.kind.GeneralTransaction;

import java.util.ArrayList;
import java.util.List;

@Data
public class GeneralTransactionContainer {

    private List<GeneralTransaction> transactionItems = new ArrayList<>();

    public void addGeneralTypedTransaction(GeneralTransaction generalTransaction) {
        transactionItems.add(generalTransaction);
    }

    public void process() {
        for (GeneralTransaction t : transactionItems) {
            t.process();
        }
    }

}
