package user.card.generator.service.transactionhandler;

import user.card.generator.domain.transaction.TransactionType;

public class TransactionFactory {

    public static AbstractTransaction produce(KindOfTransaction kindOfTransaction, TransactionType transactionType) {
        AbstractTransaction result=null;
        switch (kindOfTransaction) {
            case YEARLY:
                result = new YearlyTransaction(transactionType);
                break;
            case MONTHLY:
                result = new MonthlyTransaction(transactionType);
                break;
            case ONCE_MONTHLY:
                result = new OnceMonthlyTransaction(transactionType);
                break;
            case DAILY:
                result = new DailyTransaction(transactionType);
                break;
            case SATURDAY:
                result = new SaturdayTransaction(transactionType);
                break;
            case INTRA_DAILY:
                result = new IntraDailyTransaction(transactionType);
        }
        return result;
    }
}
