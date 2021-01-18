package user.card.generator.service;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.city.City;
import user.card.generator.domain.city.County;
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

    public void saveCity(City city) {
        cityRepository.save(city);
    }

    public void saveAllCities(List<City> cities) {
        cityRepository.saveAll(cities);
    }

    public Map<String, List<City>> citiesByNames() {
        Map<String, List<City>> result = new HashMap<>();
        List<City> cities = cityRepository.findAll();
        City cityOfBudapest = cityRepository.findByName("Budapest");
        City cityIOfDebrecen = cityRepository.findByName("Debrecen");
        City cityOfSzeged = cityRepository.findByName("Szeged");
        City cityOfNyiregyhaza = cityRepository.findByName("Nyíregyháza");
        City cityOfMiskolc = cityRepository.findByName("Miskolc");
        City cityOfPécs = cityRepository.findByName("Pécs");
        City cityOfGyor = cityRepository.findByName("Gy?r");
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
            reader = Files.newBufferedReader(Paths.get("C:\\Temp\\iranyitoszamok.csv"), Charset.forName("ISO-8859-1"));
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
                        county = new County(actualCountyName);
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
        County county = countyRepository.findByName("Pest");
        List<City> citiesInPestMegye = cityRepository.findByCounty(county);
        for (City city : citiesInPestMegye) {
            System.out.println(city);
        }
    }
}
