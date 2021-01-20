package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.city.City;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.repository.VendorRepository;
import user.card.generator.service.singleton.NumberStringGenerator;

import java.util.*;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    NumberStringGenerator numberStringGenerator;

    @Autowired
    CityService cityService;

    private final static int NUMBER_OF_ALL_VENDORS = 46030;
    private final static Map<String, Integer> numberOfVendorsOfSpecifiedBigCities = new HashMap<String, Integer>() {{
        put("BUDAPEST", 9200);
        put("DEBRECEN", 920);
        put("SZEGED", 920);
        put("NYIREGYHAZA", 920);
        put("MISKOLC", 920);
        put("PECS", 920);
        put("GYOR", 920);
    }};
    private final static List<String> CITI_NAMES_WITHOUT_BIG_CITIES = new ArrayList<String>() {{
        add("Budapest");
        add("Debrecen");
        add("Szeged");
        add("Nyíregyháza");
        add("Miskolc");
        add("Pécs");
        add("Győr");
    }};

    public void saveAllVendors(List<Vendor> vendors) {
        vendorRepository.saveAll(vendors);
    }

    @Transactional
    public void generateVendorsInHungary() {
        List<Vendor> vendors = new ArrayList<>();
        List<String> vendorCodes = numberStringGenerator.generateDifferentCodeStrings(6, NUMBER_OF_ALL_VENDORS);
        int number = NUMBER_OF_ALL_VENDORS;
        ListIterator<String> vendorCodeListIterator = vendorCodes.listIterator();
        for (String cityKey : numberOfVendorsOfSpecifiedBigCities.keySet()) {
            int currentNumberOfSpecifiedCity = numberOfVendorsOfSpecifiedBigCities.get(cityKey);
            for (int i = 0; i < currentNumberOfSpecifiedCity; i++) {
                if (vendorCodeListIterator.hasNext()) {
                    String currentVendorCode = vendorCodeListIterator.next();
                    City currentCity = cityService.findByName(cityKey);
                    Vendor vendor = new Vendor(currentVendorCode,currentCity);
                    vendors.add(vendor);
                    vendorCodeListIterator.remove();
                }
            }
        }
        List<City> littleCities = cityService.findByNameNotIn(CITI_NAMES_WITHOUT_BIG_CITIES);
        for (City currentCity : littleCities) {
            for (int i = 0; i <10 ; i++) {
                if (vendorCodeListIterator.hasNext()) {
                    String currentVendorCode = vendorCodeListIterator.next();
                    Vendor vendor = new Vendor(currentVendorCode, currentCity);
                    vendors.add(vendor);
                    vendorCodeListIterator.remove();
                }
            }
        }
        saveAllVendors(vendors);
    }
}
