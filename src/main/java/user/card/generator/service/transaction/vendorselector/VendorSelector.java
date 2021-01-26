package user.card.generator.service.transaction.vendorselector;

import org.springframework.stereotype.Service;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.Vendor;

@Service
public interface VendorSelector {

    public Vendor selectVendor(Person person);
}
