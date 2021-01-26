package user.card.generator.service.transaction.vendorselector;

import org.springframework.beans.factory.annotation.Autowired;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.VendorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class OrdinaryVendorSelector implements VendorSelector {

    @Autowired
    VendorService vendorService;

    private Person currentPerson;
    private int homeRatePercent;
    private List<Vendor> vendors = new ArrayList<>();
    private List<Vendor> vendorsInHomeCity = new ArrayList<>();

    public OrdinaryVendorSelector(int homeRatePercent) {
        this.homeRatePercent = homeRatePercent;
    }

    @Override
    public Vendor selectVendor(Person person) {
        Vendor result;
        Random random = new Random();
        if (vendors.isEmpty()) {
            vendors = vendorService.findAll();
        }
        if (person.equals(currentPerson)) {
            currentPerson = person;
            vendorsInHomeCity = vendors.stream()
                    .filter(vendor -> vendor.getCity().equals(person.getCity()))
                    .collect(Collectors.toList());
        }
        int numForSelect = random.nextInt(100);
        if (numForSelect < homeRatePercent) {
            result = vendorsInHomeCity.get(random.nextInt(vendorsInHomeCity.size()));
        } else {
            result = vendors.get(random.nextInt(vendors.size()));
        }
        return result;
    }
}
