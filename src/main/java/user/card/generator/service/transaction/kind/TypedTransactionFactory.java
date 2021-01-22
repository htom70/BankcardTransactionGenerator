package user.card.generator.service.transaction.kind;

import user.card.generator.domain.transaction.TransactionType;

public class TypedTransactionFactory {

    public static GeneralTypedTransaction create(TransactionType transactionType) {
        GeneralTypedTransaction result = null;
        switch (transactionType) {
            case POS:
                result = new PosTypedTransaction();
                break;
            case ATM:
                result = new AtmTypedTransaction();
                break;
            case NET:
                result = new NetTypedTransaction();
                break;
        }
        return result;
    }
}
