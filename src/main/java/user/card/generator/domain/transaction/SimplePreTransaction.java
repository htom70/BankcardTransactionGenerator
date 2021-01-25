package user.card.generator.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class SimplePreTransaction {

    private LocalDate date;
    private int amount;
    private TransactionType transactionType;
    private String atmOrVendorCode;

    public SimplePreTransaction(LocalDate date, int amount) {
        this.date = date;
        this.amount = amount;
    }
}
