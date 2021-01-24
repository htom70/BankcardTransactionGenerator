package user.card.generator.service.transaction.vendorselector;

import org.springframework.beans.factory.annotation.Autowired;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.VendorService;
import user.card.generator.service.transaction.vendorselector.VendorSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RetiredDailyVendorSelector implements VendorSelector {

    @Autowired
    VendorService vendorService;

    private List<Vendor> selectedVendors = new ArrayList<>();

    @Override
    public Vendor selectVendor(Person person) {
        Random random = new Random();
        if (selectedVendors.isEmpty()) {
            getVendorsInHomeCity(person);
        }
        int numberOfVendors = selectedVendors.size();
        return selectedVendors.get(random.nextInt(numberOfVendors));
    }

    private void getVendorsInHomeCity(Person person) {
        Random random = new Random();
        List<Vendor> vendors = vendorService.findAllByCity(person.getCity());
        int numberOfUsedVendors = 3 + random.nextInt(1);
        int numberOfVendorsByCity = vendors.size();
        for (int i = 0; i < numberOfUsedVendors; i++) {
            selectedVendors.add(vendors.get(random.nextInt(numberOfVendorsByCity)));
        }
    }
}
