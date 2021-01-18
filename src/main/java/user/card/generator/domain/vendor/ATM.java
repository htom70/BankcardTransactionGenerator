package user.card.generator.domain.vendor;

import lombok.Data;
import lombok.NoArgsConstructor;
import user.card.generator.domain.city.City;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Data
public class ATM extends AbstractVendor {

    private String ATMcode;

    @ManyToOne
    private Bank atmOwnerBank;

    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    public ATM(String ATMcode, City city) {
        this.ATMcode = ATMcode;
        this.city = city;

    }
}
