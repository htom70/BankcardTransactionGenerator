package user.card.generator.domain.vendor;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ManyToAny;
import user.card.generator.domain.country.Country;

import javax.persistence.*;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@Getter
@Setter
public class Vendor {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String vendorCode;

    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;

    public Vendor(String vendorCode) {
        this.vendorCode = vendorCode;
    }
}
