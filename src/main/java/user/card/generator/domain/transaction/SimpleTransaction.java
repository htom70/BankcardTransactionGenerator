package user.card.generator.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import user.card.generator.domain.ResponseCode;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class SimpleTransaction {

    private LocalDate localDate;
    private int amount;
    private TransactionType transactionType;
    private ResponseCode responseCode;
}
