package user.card.generator.service.transaction.vendorselector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.VendorService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdinaryVendorSelector implements VendorSelector {

    @Autowired
    VendorService vendorService;

    private List<City> storedCities = new ArrayList<>();
    private int homeRatePercent;
    private List<Vendor> vendors = new ArrayList<>();
    private List<Vendor> vendorsInHomeCity = new ArrayList<>();
    Map<City, List<Vendor>> vendorsInHomeCityStoredByCity = new HashMap<>();
    private List<Vendor> vendorsInForeignCity = new ArrayList<>();
    Map<City, List<Vendor>> vendorsInForeignCityStoredByCity = new HashMap<>();

    @Override
    public Vendor selectVendor(Person person) {
        Vendor result;
        City currentCity = person.getCity();
        Random random = new Random();
        if (vendors.isEmpty()) {
            vendors = vendorService.findAll();
        }
        if (!storedCities.contains(currentCity)) {
            storedCities.add(currentCity);
            vendorsInHomeCity = vendors.parallelStream()
                    .filter(vendor -> vendor.getCity().equals(currentCity))
                    .collect(Collectors.toList());
            vendorsInHomeCityStoredByCity.put(currentCity, vendorsInHomeCity);

            vendorsInForeignCity = vendors.parallelStream()
                    .filter(vendor -> vendor.getCity().equals(currentCity))
                    .collect(Collectors.toList());
            vendorsInForeignCityStoredByCity.put(currentCity, vendorsInForeignCity);
        }
        vendorsInHomeCity = vendorsInHomeCityStoredByCity.get(currentCity);
        vendorsInForeignCity = vendorsInForeignCityStoredByCity.get(currentCity);
        int numForSelect = random.nextInt(100);
        if (numForSelect < homeRatePercent) {
            result = vendorsInHomeCity.get(random.nextInt(vendorsInHomeCity.size()));
        } else {
            result = vendors.get(random.nextInt(vendorsInForeignCity.size()));
        }
        return result;
    }
}
