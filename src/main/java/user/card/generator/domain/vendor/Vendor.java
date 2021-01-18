package user.card.generator.domain.vendor;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@NoArgsConstructor
@PrimaryKeyJoinColumn
public class Vendor extends AbstractVendor{

    private String vendorCode;

    public Vendor(String vendorCode) {
        this.vendorCode = vendorCode;
    }
}
