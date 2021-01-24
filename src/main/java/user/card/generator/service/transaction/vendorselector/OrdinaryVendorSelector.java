package user.card.generator.service.transaction.vendorselector;

import org.springframework.beans.factory.annotation.Autowired;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.VendorService;
import user.card.generator.service.transaction.vendorselector.VendorSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrdinaryVendorSelector implements VendorSelector {

    @Autowired
    VendorService vendorService;

    private int homeRatePercent;
    private List<Vendor> vendors = new ArrayList<>();
    private List<Vendor> vendorsInHomeCity = new ArrayList<>();

    public OrdinaryVendorSelector(int homeRatePercent) {
        this.homeRatePercent = homeRatePercent;
    }

    @Override
    public Vendor selectVendor(Person person) {
        Random random = new Random();
        Vendor vendor;
        if (vendors.isEmpty()) {
            vendors = vendorService.findAll();
        }
        if (vendorsInHomeCity.isEmpty()) {
            vendorsInHomeCity = vendorService.findAllByCity(person.getCity());
        }
        int numForSelect = random.nextInt(100);
        if (numForSelect < homeRatePercent) {
            vendor = vendorsInHomeCity.get(random.nextInt(vendorsInHomeCity.size()));
        } else {
            vendor = vendors.get(random.nextInt(vendors.size()));
        }
        return vendor;
    }
}
