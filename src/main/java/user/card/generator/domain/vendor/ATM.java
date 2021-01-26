package user.card.generator.domain.vendor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import user.card.generator.domain.city.City;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Data
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ATM extends AbstractVendor {

    private String ATMcode;

    @ManyToOne
    private Bank bank;

    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    public ATM(String ATMcode, City city, Bank bank) {
        this.ATMcode = ATMcode;
        this.city = city;
        this.bank = bank;
    }
}
