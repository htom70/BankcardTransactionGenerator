package user.card.generator.service;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.city.City;
import user.card.generator.domain.city.County;
import user.card.generator.domain.country.Country;
import user.card.generator.repository.CityRepository;
import user.card.generator.repository.CountyRepository;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountyRepository countyRepository;

    @Autowired
    CountryService countryService;

    public void saveCity(City city) {
        cityRepository.save(city);
    }

    public void saveAllCities(List<City> cities) {
        cityRepository.saveAll(cities);
    }

    public City findByName(String name) {
        return cityRepository.findByName(name);
    }

    public List<City> findByNameNotIn(List<String> citinames) {
        return cityRepository.findAllByNameNotIn(citinames);
    }

    public List<City> findAllByCountry(Country country) {
        return cityRepository.findAllByCountry(country);
    }

    public List<City> findByNameIn(List<String> citieNames) {
        return cityRepository.findAllByNameIn(citieNames);
    }

    public Map<String, List<City>> getCitiesByNames() {
        Map<String, List<City>> result = new HashMap<>();
        List<City> cities = cityRepository.findAll();
        City cityOfBudapest = cityRepository.findByName("Budapest");
        City cityIOfDebrecen = cityRepository.findByName("Debrecen");
        City cityOfSzeged = cityRepository.findByName("Szeged");
        City cityOfNyiregyhaza = cityRepository.findByName("Nyíregyháza");
        City cityOfMiskolc = cityRepository.findByName("Miskolc");
        City cityOfPécs = cityRepository.findByName("Pécs");
        City cityOfGyor = cityRepository.findByName("Győr");
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
            reader = Files.newBufferedReader(Paths.get("C:\\Temp\\iranyitoszamok_utf8.csv"), Charset.forName("UTF-8"));
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
                cityRepository.save(city);
                cities.add(city);
            }
            System.out.println("City list length: " + cities.size());
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis();
            System.out.println("Elapsed time: " + elapsedTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
