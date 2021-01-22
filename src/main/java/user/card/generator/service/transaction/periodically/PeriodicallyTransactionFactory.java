package user.card.generator.service.transaction.periodically;

public class PeriodicallyTransactionFactory {

    public static GeneralPeriodicallyTransaction create(PeriodicallyTransactionType periodicallyTransactionType) {
        GeneralPeriodicallyTransaction generalPeriodicallyTransaction = null;
        switch (periodicallyTransactionType) {
            case DAILY:
                generalPeriodicallyTransaction= new DailyTransaction();
                break;
            case MONTHLY:
                generalPeriodicallyTransaction = new MothlyTransaction();
                break;
            case YEARLY:
                generalPeriodicallyTransaction = new YearlyTransaction();
                break;
            case ONCE_MONTHLY:
                generalPeriodicallyTransaction = new OnceMonthlyTransaction();
                break;
            case SATURDAY:
                generalPeriodicallyTransaction = new SaturdayTransaction();
                break;
            case INTER_DAILY:
                generalPeriodicallyTransaction = new IntraDailyTransaction();
                break;
        }
        return generalPeriodicallyTransaction;
    }
}
