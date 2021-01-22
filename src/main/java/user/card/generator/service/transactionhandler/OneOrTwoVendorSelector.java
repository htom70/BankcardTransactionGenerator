package user.card.generator.service.transactionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.VendorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneOrTwoVendorSelector implements VendorAndAtmSelector{

    @Autowired
    VendorService vendorService;

    @Override
    public List<Vendor> selectAvailableVendors(Person person) {
        Random random = new Random();
        List<Vendor> selectedVendors = new ArrayList<>();
        List<Vendor> vendorsInCity = vendorService.findAllByCity(person.getCity());
        int sumOfVendorsInCity = vendorsInCity.size();
        int numberOfVendorsToBeSelected = 1 + random.nextInt(1);
        for (int i = 0; i < numberOfVendorsToBeSelected; i++) {
            selectedVendors.add(vendorsInCity.get(random.nextInt(sumOfVendorsInCity)));
        }
        return selectedVendors;
    }

    @Override
    public List<ATM> selectAvailableATMs(Person person) {
        return null;
    }
}
