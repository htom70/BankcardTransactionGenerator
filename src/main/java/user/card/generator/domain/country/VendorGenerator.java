package user.card.generator.domain.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import user.card.generator.repository.CountryRepository;
import user.card.generator.repository.VendorRepository;

import java.util.*;

@Component
public class VendorGenerator {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CountryRepository countryRepository;

    public VendorGenerator(VendorRepository vendorRepository, CountryRepository countryRepository) {
        this.vendorRepository = vendorRepository;
        this.countryRepository = countryRepository;
    }

    public void generate() {
        if (countryRepository.findAll().isEmpty()) {
            Random random = new Random();
            generateCountriesAndVendors(random);
        }
    }

    private void generateCountriesAndVendors(Random random) {
        Map<String, String> countriesAndCurrencies = CountryCodeAndCurrency.getProperties();
        for (Map.Entry<String, String> entry : countriesAndCurrencies.entrySet()) {
            Country country = new Country(entry.getKey(), entry.getValue());
//            Set<Vendor> vendors = generateVendors(random, country);
//            country.setVendors(vendors);
            countryRepository.save(country);
        }
        List<Country> countries = countryRepository.findAll();
        for (int i = 0; i < countries.size(); i++) {
//            generateVendors(random, countries.get(i));
        }
    }

//    private Set<AbstractVendor> generateVendors(Random random, Country country) {
//        Set<AbstractVendor> result = new HashSet<>();
//            for (int i = 0; i < 100; i++) {
//                String generatedVendorCode;
//                do {
//                    generatedVendorCode = generateVendorString(random);
//                } while (vendorRepository.findByVendorCode(generatedVendorCode) != null);
//                AbstractVendor vendor = new Vendor(generatedVendorCode);
//                vendor.setCountry(country);
//                vendorRepository.save(vendor);
//                result.add(vendor);
//            }
//        return result;
//    }

    private String generateVendorString(Random random) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        String result = stringBuilder.toString();
        return result;
    }
}
