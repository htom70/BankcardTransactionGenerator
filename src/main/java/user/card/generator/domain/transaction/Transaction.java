package user.card.generator.domain.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import user.card.generator.domain.AtmOwnerBank;
import user.card.generator.domain.ProductCategory;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.country.Vendor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String cardNumber;
    private TransactionType transactionType;
    private Timestamp timestamp;
    private int amount;
    private String currencyName;
    private ResponseCode responseCode;
    private String countryName;
    private String vendorCode;
    private ProductCategory productCategory;
    private AtmOwnerBank atmOwnerBank;

    public Transaction(String cardNumber,TransactionType transactionType, Timestamp timestamp, int amount, String currencyName, ResponseCode responseCode, String countryName,
                       String vendorCode, ProductCategory productCategory, AtmOwnerBank atmOwnerBank) {
        this.transactionType = transactionType;
        this.timestamp = timestamp;
        this.amount = amount;
        this.currencyName = currencyName;
        this.responseCode = responseCode;
        this.countryName = countryName;
        this.vendorCode = vendorCode;
        this.productCategory = productCategory;
        this.atmOwnerBank = atmOwnerBank;
        this.cardNumber = cardNumber;
    }
}
