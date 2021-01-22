package user.card.generator.service.transactionhandler;

import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.TransactionType;

public class TransactionFactory {

    public static AbstractTransaction produce(KindOfTransaction kindOfTransaction, TransactionType transactionType, Person person) {
        AbstractTransaction result = null;
        switch (kindOfTransaction) {
            case YEARLY:
                result = new YearlyTransaction(transactionType, person);
                break;
            case MONTHLY:
                result = new MonthlyTransaction(transactionType, person);
                break;
            case ONCE_MONTHLY:
                result = new OnceMonthlyTransaction(transactionType, person);
                break;
            case DAILY:
                result = new DailyTransaction(transactionType, person);
                switch (person.getPersonType()) {
                    case RETIRED_USE_CARD_AND_INTERNET:

                        result.setVendorAndAtmSelector(new OneOrTwoVendorSelector());
                        break;
                    case ORDINARYUSER_USE_CARD_AND_INTERNET:
                        //TO_DO
                        break;

                }
                break;
            case SATURDAY:
                result = new SaturdayTransaction(transactionType, person);
                result.setVendorAndAtmSelector(new SaturdayVendorSelector());
                break;
            case INTRA_DAILY:
                result = new IntraDailyTransaction(transactionType, person);
        }
        return result;
    }
}
