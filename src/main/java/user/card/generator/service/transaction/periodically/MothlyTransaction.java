package user.card.generator.service.transaction.periodically;

public class MothlyTransaction extends GeneralPeriodicallyTransaction{
    @Override
    public void processTypedTransaction() {
        System.out.println("monthly transaction");
    }
}
