package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.country.City;
import user.card.generator.repository.CityRepository;

import java.util.List;

@Transactional
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void saveCity(City city) {
        cityRepository.save(city);
    }

    public void saveAllCities(List<City> cities) {
        cityRepository.saveAll(cities);
    }
}
