package user.card.generator.service.transaction.periodically;

public class YearlyTransaction extends GeneralPeriodicallyTransaction{
    @Override
    public void processTypedTransaction() {
        System.out.println("yearly transaction");
    }
}
