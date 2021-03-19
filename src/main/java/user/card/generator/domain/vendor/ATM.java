package user.card.generator.domain.vendor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import user.card.generator.domain.city.City;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ATM {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String ATMcode;

    @ManyToOne
    private Bank bank;

    @ManyToOne
    private City city;

    public ATM(String ATMcode, City city, Bank bank) {
        this.city=city;
        this.ATMcode = ATMcode;
        this.bank = bank;
    }
}
