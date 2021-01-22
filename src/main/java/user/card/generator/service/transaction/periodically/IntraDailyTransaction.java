package user.card.generator.service.transaction.periodically;

public class IntraDailyTransaction extends GeneralPeriodicallyTransaction{
    @Override
    public void processTypedTransaction() {
        System.out.println("intra daily transaction");
    }
}
