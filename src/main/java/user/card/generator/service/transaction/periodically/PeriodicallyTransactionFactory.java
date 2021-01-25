package user.card.generator.service.transaction.periodically;

public class PeriodicallyTransactionFactory {

    public static GeneralTimeTypedTransaction create(PeriodicallyTransactionType periodicallyTransactionType) {
        GeneralTimeTypedTransaction generalTimeTypedTransaction = null;
        switch (periodicallyTransactionType) {
            case DAILY:
                generalTimeTypedTransaction = new DailyTransaction();
                break;
            case MONTHLY:
                generalTimeTypedTransaction = new MothlyTransaction();
                break;
            case YEARLY:
                generalTimeTypedTransaction = new YearlyTransaction();
                break;
            case ONCE_MONTHLY:
                generalTimeTypedTransaction = new OnceMonthlyTransaction();
                break;
            case SATURDAY:
                generalTimeTypedTransaction = new SaturdayTransaction();
                break;
            case INTER_DAILY:
                generalTimeTypedTransaction = new IntraDailyTransaction();
                break;
        }
        return generalTimeTypedTransaction;
    }
}
