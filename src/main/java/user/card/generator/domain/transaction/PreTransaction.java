package user.card.generator.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import user.card.generator.domain.ResponseCode;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
public class PreTransaction {

    private String cardNumber;
    private TransactionType transactionType;
    private Timestamp timestamp;
    private int amount;
    private String currencyName;
    private ResponseCode responseCode;
    private String countryName;
    private String vendorCode;
}
