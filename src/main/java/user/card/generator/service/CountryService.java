package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.country.CountryCodeAndCurrency;
import user.card.generator.repository.CountryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    public void saveAllCountry(List<Country> countries) {
        countryRepository.saveAll(countries);
    }

    public Country findByCountryCode(String countryCode) {
       return countryRepository.findByCountryCode(countryCode);
    }

    public void generateCountries() {
        List<Country> countries = new ArrayList<>();
        Map<String, String> countriesAndCurrencies = CountryCodeAndCurrency.getProperties();
        for (Map.Entry<String, String> entry : countriesAndCurrencies.entrySet()) {
            Country country = new Country(entry.getKey(), entry.getValue());
            countries.add(country);
        }
        saveAllCountry(countries);
    }


}
