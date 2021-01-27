package user.card.generator.service.transaction.vendorselector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.VendorService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RetiredDailyVendorSelector implements VendorSelector {

    @Autowired
    VendorService vendorService;

    private List<City> storedCities = new ArrayList<>();
    private List<Person> storedPerson = new ArrayList<>();
    private List<Vendor> selectedVendors = new ArrayList<>();
    private List<Vendor> vendors = new ArrayList<>();

    private List<Vendor> vendorsInHomeCity = new ArrayList<>();
    Map<City, List<Vendor>> vendorsInHomeCityStoredByCity = new HashMap<>();
    Map<Person, List<Vendor>> vendorsStoredByPerson = new HashMap<>();

    @Override
    public Vendor selectVendor(Person person) {
        Random random = new Random();
        City currentCity = person.getCity();

        if (vendors.isEmpty()) {
            vendors = vendorService.findAll();
        }

        if (!storedCities.contains(currentCity)) {
            storedCities.add(currentCity);
            vendorsInHomeCity = vendors.parallelStream()
                    .filter(vendor -> vendor.getCity().equals(currentCity))
                    .collect(Collectors.toList());
            vendorsInHomeCityStoredByCity.put(currentCity, vendorsInHomeCity);
        }
        if (!storedPerson.contains(person)) {
            storedPerson.add(person);
            int numberOfUsedVendors = 1 + random.nextInt(2);
            List<Vendor> usedVendorsOfGiverPerson = new ArrayList<>();
            for (int i = 0; i < numberOfUsedVendors; i++) {
                List<Vendor> vendors = vendorsInHomeCityStoredByCity.get(currentCity);
                usedVendorsOfGiverPerson.add(vendors.get(random.nextInt(vendors.size())));
            }
            vendorsStoredByPerson.put(person, usedVendorsOfGiverPerson);
        }

        selectedVendors = vendorsStoredByPerson.get(person);
        int numberOfUsedVendors = selectedVendors.size();
        return selectedVendors.get(random.nextInt(numberOfUsedVendors));
    }
}
