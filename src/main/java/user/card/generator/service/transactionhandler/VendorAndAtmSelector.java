package user.card.generator.service.transactionhandler;

import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Vendor;

import java.util.List;

public interface VendorAndAtmSelector {

    public List<Vendor> selectAvailableVendors(Person person);
    public List<ATM> selectAvailableATMs(Person person);

}
