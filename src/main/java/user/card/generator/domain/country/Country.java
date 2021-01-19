package user.card.generator.domain.country;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;


@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String countryCode;
    private String currencyName;



//    @OneToMany(mappedBy = "country",fetch = FetchType.EAGER)
//    private Set<AbstractVendor> vendors = new HashSet<>();

    public Country(String countryCode, String currencyName) {
        this.countryCode = countryCode;
        this.currencyName = currencyName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryCode='" + countryCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}
