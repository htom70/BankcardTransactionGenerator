package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Bank;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.repository.ATMrepository;
import user.card.generator.service.singleton.NumberStringGenerator;

import java.util.*;

@Service
public class ATMservice {

    @Autowired
    ATMrepository atMrepository;

    @Autowired
    CityService cityService;

    @Autowired
    CountryService countryService;

    @Autowired
    BankService bankService;


    private final static Map<String, Integer> numberOfATMsOfSpecifiedBigCities = new HashMap<String, Integer>() {{
        put("BUDAPEST", 200);
        put("DEBRECEN", 920);
        put("SZEGED", 920);
        put("NYIREGYHAZA", 920);
        put("MISKOLC", 920);
        put("PECS", 920);
        put("GYOR", 920);
    }};


    public void saveAllATMs(List<ATM> atms) {
        atMrepository.saveAll(atms);
    }

    public List<ATM> findAll() {
        return atMrepository.findAll();
    }

    public List<ATM> findAllByCity(City city) {
        return atMrepository.findAllByCity(city);
    }

    public List<ATM> findAllByCityIsNot(City city) {
        return atMrepository.findAllByCityIsNot(city);
    }

    public void generateHungarianATMs() {
        List<ATM> atms = new ArrayList<>();
        Country countryHungary = countryService.findByCountryCode("HU");
        List<City> citiesInHungary = cityService.findAllByCountry(countryHungary);
        City cityOfBudapest = cityService.findByName("Budapest");
        City cityOfDebrecen = cityService.findByName("Debrecen");
        City cityOfSzeged = cityService.findByName("Szeged");
        City cityOfMiskolc = cityService.findByName("Miskolc");
        City cityOfPecs = cityService.findByName("Pécs");
        City cityOfGyor = cityService.findByName("Győr");
        City cityOfNyiregyhaza = cityService.findByName("Nyiregyhaza");
        citiesInHungary.remove(cityOfBudapest);
        citiesInHungary.remove(cityOfGyor);
        citiesInHungary.remove(cityOfPecs);
        citiesInHungary.remove(cityOfMiskolc);
        citiesInHungary.remove(cityOfDebrecen);
        citiesInHungary.remove(cityOfSzeged);
        citiesInHungary.remove(cityOfNyiregyhaza);
        List<Bank> banksInHungary = bankService.findAllByCountry(countryHungary);
        int numOfToBeGeneratedATM = (100 + 6 * 5 + citiesInHungary.size()) * banksInHungary.size();
        NumberStringGenerator numberStringGenerator = new NumberStringGenerator();
        List<String> atmCodes = numberStringGenerator.generateDifferentCodeStrings(5, numOfToBeGeneratedATM);
        ListIterator<String> atmCodeIterator = atmCodes.listIterator();
        for (int i = 0; i < 100; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfBudapest, currentBank);
                    atms.add(atm);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfDebrecen, currentBank);
                    atms.add(atm);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfGyor, currentBank);
                    atms.add(atm);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfMiskolc, currentBank);
                    atms.add(atm);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfNyiregyhaza, currentBank);
                    atms.add(atm);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfPecs, currentBank);
                    atms.add(atm);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfSzeged, currentBank);
                    atms.add(atm);
                }
            }
        }
        for (City littleCity : citiesInHungary) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), littleCity, currentBank);
                    atms.add(atm);
                }
            }
        }
        saveAllATMs(atms);
    }
}
