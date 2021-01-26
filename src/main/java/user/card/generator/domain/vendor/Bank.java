package user.card.generator.domain.vendor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import user.card.generator.domain.country.Country;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String name;

    @ManyToOne
    private Country country;

    public Bank(String name,Country country) {
        this.name = name;
        this.country = country;
    }
}
