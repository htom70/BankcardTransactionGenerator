package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.city.City;
import user.card.generator.domain.city.County;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    public City findByName(String name);

    public List<City> findByCounty(County county);

    public List<City> findAllByNameNotIn(List<String> citiNames);

    public List<City> findAllByNameIn(List<String> citieNames);

}
