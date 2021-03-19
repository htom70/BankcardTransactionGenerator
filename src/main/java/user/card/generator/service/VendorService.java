package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.repository.TransactionRepository;
import user.card.generator.repository.VendorRepository;
import user.card.generator.service.singleton.NumberStringGenerator;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VendorService {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    NumberStringGenerator numberStringGenerator;

    @Autowired
    CityService cityService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionService transactionService;

    private final static int NUMBER_OF_ALL_VENDORS = 46030;
    private final static Map<String, Integer> numberOfVendorsOfSpecifiedBigCities = new HashMap<String, Integer>() {{
        put("Budapest", 9200);
        put("Debrecen", 920);
        put("Szeged", 920);
        put("Nyíregyháza", 920);
        put("Miskolc", 920);
        put("Pécs", 920);
        put("Győr", 920);
    }};
    private final static List<String> BIG_CITIES = new ArrayList<String>() {{
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

    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    public List<Vendor> findAllByCity(City city) {
        return vendorRepository.findAllByCity(city);
    }

    public List<Vendor> findAllByCityIsNot(City city) {
        return vendorRepository.findAllByCityIsNot(city);
    }

    public List<Vendor> findAllVendorInNormalTransaction() {
        List<Transaction> transactions = transactionService.findAllNormalTransaction();
        List<String> vendorCodesInNormalTransaction=transactions.parallelStream()
                .map(transaction -> transaction.getVendorCode())
                .collect(Collectors.toList());
        List<Vendor> vendorsInNormalTransaction = vendorRepository.findAllByVendorCodeIn(vendorCodesInNormalTransaction);
        return vendorsInNormalTransaction;
    }

    public List<Vendor> findAllVendorInNormalTransactionByCity(City city) {
        List<Transaction> normalTransactions = transactionRepository.findAllNormalTransaction();
        List<String> vendorNames = new ArrayList<>();
        for (Transaction t : normalTransactions) {
            vendorNames.add(t.getVendorCode());
        }
        List<Vendor> vendorsInNormalTransactionsInCity = vendorRepository.findAllByVendorCodeInAndCity(vendorNames,city);
        return vendorsInNormalTransactionsInCity;
    }

//    @Transactional
    public void generateVendorsInHungary() {
        List<City> cities = new ArrayList<>();
        List<Vendor> vendors = new ArrayList<>();
        List<String> vendorCodes = numberStringGenerator.generateDifferentCodeStrings(6, NUMBER_OF_ALL_VENDORS);
        int number = NUMBER_OF_ALL_VENDORS;
        ListIterator<String> vendorCodeListIterator = vendorCodes.listIterator();
        for (String cityKey : numberOfVendorsOfSpecifiedBigCities.keySet()) {
            int currentNumberOfSpecifiedCity = numberOfVendorsOfSpecifiedBigCities.get(cityKey);
            for (int i = 0; i < currentNumberOfSpecifiedCity; i++) {
                if (vendorCodeListIterator.hasNext()) {
                    String currentVendorCode = vendorCodeListIterator.next();
                    City currentCity = cityService.findByNameAndQueryVendors(cityKey);
                    Vendor vendor = new Vendor(currentVendorCode, currentCity);
                    vendors.add(vendor);
                    currentCity.getVendors().add(vendor);
                    cities.add(currentCity);
//                    vendorRepository.save(vendor);
//                    cityService.saveCity(currentCity);
                    vendorCodeListIterator.remove();
                }
            }
        }
        List<City> littleCities = cityService.findAllByNameNotInAndQueryVendors(BIG_CITIES);
        for (City currentCity : littleCities) {
            for (int i = 0; i < 10; i++) {
                if (vendorCodeListIterator.hasNext()) {
                    String currentVendorCode = vendorCodeListIterator.next();
                    Vendor vendor = new Vendor(currentVendorCode, currentCity);
                    vendors.add(vendor);
                    currentCity.getVendors().add(vendor);
//                    vendorRepository.save(vendor);
                    cities.add(currentCity);
//                    cityService.saveCity(currentCity);
                    vendorCodeListIterator.remove();
                }
            }
        }
        saveAllVendors(vendors);
        cityService.saveAllCities(cities);
    }
}
