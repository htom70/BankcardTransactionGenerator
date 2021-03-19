package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Bank;
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

    public List<ATM> findAllByCityAndBank(City city, Bank bank) {
        return atMrepository.findAllByCityAndBank(city, bank);
    }

    public List<ATM> findAllByCityIsNotAndBank(City city, Bank bank) {
        return atMrepository.findAllByCityIsNotAndBank(city, bank);
    }

    public List<ATM> findAllByCityAndBankIsNot(City city, Bank bank) {
        return atMrepository.findAllByCityAndBankIsNot(city, bank);
    }

    public List<ATM> findAllByCityIsNotAndBankIsNot(City city, Bank bank) {
        return atMrepository.findAllByCityIsNotAndBankIsNot(city, bank);
    }


    public void generateHungarianATMs() {
        List<ATM> atms = new ArrayList<>();
        Country countryHungary = countryService.findByCountryCode("HU");
        List<City> cities = new ArrayList<>();
        List<City> citiesInHungary = cityService.findAllByCountry(countryHungary);
        City cityOfBudapest = cityService.findByNameAndQueryAtms("Budapest");
        City cityOfDebrecen = cityService.findByNameAndQueryAtms("Debrecen");
        City cityOfSzeged = cityService.findByNameAndQueryAtms("Szeged");
        City cityOfMiskolc = cityService.findByNameAndQueryAtms("Miskolc");
        City cityOfPecs = cityService.findByNameAndQueryAtms("Pécs");
        City cityOfGyor = cityService.findByNameAndQueryAtms("Győr");
        City cityOfNyiregyhaza = cityService.findByNameAndQueryAtms("Nyiregyhaza");
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
                    cityOfBudapest.addATM(atm);
                    cities.add(cityOfBudapest);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfDebrecen, currentBank);
                    atms.add(atm);
                    cityOfDebrecen.addATM(atm);
                    cities.add(cityOfDebrecen);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfGyor, currentBank);
                    atms.add(atm);
                    cityOfGyor.addATM(atm);
                    cities.add(cityOfGyor);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfMiskolc, currentBank);
                    atms.add(atm);
                    cityOfMiskolc.addATM(atm);
                    cities.add(cityOfMiskolc);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfNyiregyhaza, currentBank);
                    atms.add(atm);
                    cityOfNyiregyhaza.addATM(atm);
                    cities.add(cityOfNyiregyhaza);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfPecs, currentBank);
                    atms.add(atm);
                    cityOfPecs.addATM(atm);
                    cities.add(cityOfPecs);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), cityOfSzeged, currentBank);
                    atms.add(atm);
                    cityOfSzeged.addATM(atm);
                    cities.add(cityOfSzeged);
                }
            }
        }
        for (City littleCity : citiesInHungary) {
            for (Bank currentBank : banksInHungary) {
                if (atmCodeIterator.hasNext()) {
                    ATM atm = new ATM(atmCodeIterator.next(), littleCity, currentBank);
                    atms.add(atm);
                    cities.add(littleCity);
                }
            }
        }
        saveAllATMs(atms);
        cityService.saveAllCities(cities);
    }
}
