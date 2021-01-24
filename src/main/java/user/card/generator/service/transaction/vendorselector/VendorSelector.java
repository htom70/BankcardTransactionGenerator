package user.card.generator.service.transaction.vendorselector;

import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.Vendor;

import java.util.List;

public interface VendorSelector {

    public Vendor selectVendor(Person person);
}
