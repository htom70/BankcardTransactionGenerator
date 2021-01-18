package user.card.generator.domain.vendor;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Data
public class ATM extends AbstractVendor {

    private String ATMcode;

    @ManyToOne
    private Bank atmOwnerBank;

    public ATM(String ATMcode) {
        this.ATMcode = ATMcode;
    }
}
