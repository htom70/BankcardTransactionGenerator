package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.city.City;
import user.card.generator.domain.city.County;
import user.card.generator.domain.country.Country;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    public City findByName(String name);

    @Query(value = "SELECT c FROM City c JOIN c.county county JOIN county.country country WHERE country =:country")
    public List<City> findAllByCountry(@Param("country") Country country);

    public List<City> findByCounty(County county);

    public List<City> findAllByNameNotIn(List<String> citiNames);

    public List<City> findAllByNameIn(List<String> citieNames);

}
