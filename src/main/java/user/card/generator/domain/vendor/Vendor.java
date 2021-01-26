package user.card.generator.domain.vendor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import user.card.generator.domain.city.City;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Data
@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vendor extends AbstractVendor {

    private String vendorCode;

    @ManyToOne
    private City city;

    public Vendor(String vendorCode, City city) {
        this.vendorCode = vendorCode;
        this.city = city;
    }
}


