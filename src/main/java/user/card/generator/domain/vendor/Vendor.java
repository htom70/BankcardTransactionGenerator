package user.card.generator.domain.vendor;

import lombok.NoArgsConstructor;
import user.card.generator.domain.city.City;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn
public class Vendor extends AbstractVendor {

    private String vendorCode;

    @ManyToOne
    private City city;

    public Vendor(String vendorCode, City city) {
        this.vendorCode = vendorCode;
        this.city = city;
    }
}
