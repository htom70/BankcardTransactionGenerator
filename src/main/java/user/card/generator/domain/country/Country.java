package user.card.generator.domain.country;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import user.card.generator.domain.vendor.AbstractVendor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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

    private String name;
    private String currencyName;

//    @OneToMany(mappedBy = "country",fetch = FetchType.EAGER)
//    private Set<AbstractVendor> vendors = new HashSet<>();

    public Country(String name, String currencyName) {
        this.name = name;
        this.currencyName = currencyName;
    }
}
