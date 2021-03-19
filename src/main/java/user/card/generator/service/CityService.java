package user.card.generator.service;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.city.City;
import user.card.generator.domain.city.County;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.repository.CityRepository;
import user.card.generator.repository.CountyRepository;
import user.card.generator.repository.TransactionRepository;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Transactional
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    CountryService countryService;

    @Autowired
    TransactionRepository transactionRepository;

    public void saveCity(City city) {
        cityRepository.save(city);
    }

    public void saveAllCities(List<City> cities) {
        cityRepository.saveAll(cities);
    }

    public City findByNameAndQueryVendors(String name) {
        return cityRepository.findByNameAndQueryVendors(name);
    }

    public City findByNameAndQueryAtms(String name) {
        return cityRepository.findByNameAndQueryAtms(name);
    }

    public List<City> findByNameNotIn(List<String> citinames) {
        return cityRepository.findAllByNameNotIn(citinames);
    }

    public List<City> findAllByNameNotInAndQueryVendors(List<String> citinames) {
        return cityRepository.findAllByNameNotInAndQueryVendors(new HashSet<>(citinames));
    }

    public List<City> findAllByCountry(Country country) {
        return cityRepository.findAllByCountry(country);
    }

    public List<City> findAllByActiveVendors(List<Vendor> vendors) {
        return cityRepository.findAllByActiveVendors(new HashSet<>(vendors));
    }

    public List<City> findAllInNormalTransactionByCountry(Country country) {
        List<Transaction> normalTransactions = transactionRepository.findAllNormalTransaction();
        return cityRepository.findAllByCountry(country);
    }

    public List<City> findByNameIn(List<String> citieNames) {
        return cityRepository.findAllByNameIn(citieNames);
    }

    public Map<String, List<City>> getCitiesByNames() {
        Map<String, List<City>> result = new HashMap<>();
        List<City> cities = cityRepository.findAll();
        City cityOfBudapest = cityRepository.findByNameAndQueryVendors("Budapest");
        City cityIOfDebrecen = cityRepository.findByNameAndQueryVendors("Debrecen");
        City cityOfSzeged = cityRepository.findByNameAndQueryVendors("Szeged");
        City cityOfNyiregyhaza = cityRepository.findByNameAndQueryVendors("Nyíregyháza");
        City cityOfMiskolc = cityRepository.findByNameAndQueryVendors("Miskolc");
        City cityOfPécs = cityRepository.findByNameAndQueryVendors("Pécs");
        City cityOfGyor = cityRepository.findByNameAndQueryVendors("Győr");
        result.put("Budapest", new ArrayList<>(Arrays.asList(cityOfBudapest)));
        result.put("Debrecen", new ArrayList<>(Arrays.asList(cityIOfDebrecen)));
        result.put("Szeged", new ArrayList<>(Arrays.asList(cityOfSzeged)));
        result.put("Nyiregyhaza", new ArrayList<>(Arrays.asList(cityOfNyiregyhaza)));
        result.put("Miskolc", new ArrayList<>(Arrays.asList(cityOfMiskolc)));
        result.put("Pécs", new ArrayList<>(Arrays.asList(cityOfPécs)));
        result.put("Gyor", new ArrayList<>(Arrays.asList(cityOfGyor)));
        cities.remove(cityOfBudapest);
        cities.remove(cityIOfDebrecen);
        cities.remove(cityOfSzeged);
        cities.remove(cityOfNyiregyhaza);
        cities.remove(cityOfMiskolc);
        cities.remove(cityOfPécs);
        cities.remove(cityOfGyor);
        result.put("others", cities);
        return result;
    }

    @Transactional
    public void citiesReadFromCsv() {
        Reader reader = null;

        List<County> counties = new ArrayList<>();
        List<City> cities = new ArrayList<>();

        Instant start = Instant.now();
        try {
            reader = Files.newBufferedReader(Paths.get("/home/mki/csv/iranyitoszamok_utf8.csv"), Charset.forName("UTF-8"));
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> records = csvReader.readAll();
            Iterator<String[]> iterator = records.iterator();
            String[] heads = iterator.next();
            City city = null;
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                String actualPostalCodeName = record[0];
                String actualCityName = record[1];
                String actualCountyName = record[2];
                if (actualCityName != "" && actualCityName != null) {
                    city = cityRepository.findByName(actualCityName);
                    if (city==null) {
                        city = new City(actualCityName);
                        cityRepository.save(city);
                    }
                }
                if (actualCountyName != "" && actualCountyName != null) {
                    County county = countyRepository.findByName(actualCountyName);
                    if (county == null) {
                        Country country = countryService.findByCountryCode("HU");
                        county = new County(actualCountyName, country);

                        countyRepository.save(county);
                    }
                    city.setCounty(county);
                }
//                cityRepository.save(city);
                cities.add(city);
            }
            cityRepository.saveAll(cities);
            System.out.println("City list length: " + cities.size());
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis();
            System.out.println("Elapsed time: " + elapsedTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
