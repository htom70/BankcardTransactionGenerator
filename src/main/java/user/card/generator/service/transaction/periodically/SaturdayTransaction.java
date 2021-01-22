package user.card.generator.service.transaction.periodically;

public class SaturdayTransaction extends GeneralPeriodicallyTransaction{
    @Override
    public void processTypedTransaction() {
        System.out.println("saturday transaction");
    }
}
