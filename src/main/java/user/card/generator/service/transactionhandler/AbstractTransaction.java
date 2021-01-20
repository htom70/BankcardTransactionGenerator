package user.card.generator.service.transactionhandler;

import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;

public abstract class AbstractTransaction {

    public abstract Map<LocalDate,List<SimplePreTransaction>> generate(List<LocalDate> days, TransactionProperty transactionProperty, Year year);

}
