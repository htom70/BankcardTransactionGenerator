package user.card.generator.service.transaction.periodically;

public class OnceMonthlyTransaction extends GeneralPeriodicallyTransaction{
    @Override
    public void processTypedTransaction() {
        System.out.println("Once monthly");
    }
}
