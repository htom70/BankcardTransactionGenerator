package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.city.City;
import user.card.generator.domain.city.County;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.vendor.Vendor;

import java.util.List;
import java.util.Set;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c left join fetch c.vendors left join fetch c.atms where c.name = :name")
    public City findByNameAndQueryVendors(@Param("name") String name);

    @Query("select c from City c left join fetch c.atms left join fetch c.vendors where c.name = :name")
    public City findByNameAndQueryAtms(@Param("name") String name);

    public City findByName(String name);

    @Query(value = "SELECT c FROM City c LEFT JOIN FETCH c.atms JOIN c.county county JOIN county.country country WHERE country =:country")
    public List<City> findAllByCountry(@Param("country") Country country);

    public List<City> findByCounty(County county);

    public List<City> findAllByNameNotIn(List<String> citiNames);

    @Query("select c from City c left join fetch c.vendors left join fetch c.atms where c.name NOT IN (:citiNames)")
    public List<City> findAllByNameNotInAndQueryVendors(@Param("citiNames") Set<String> citiNames);

    public List<City> findAllByNameIn(List<String> citieNames);

    @Query(value = "SELECT c FROM City c JOIN c.vendors v WHERE v IN (:activeVendors)")
//    @Query(value = "SELECT c FROM City c JOIN c.vendors vendors WHERE vendors IN (:activeVendors)")
    public List<City> findAllByActiveVendors(@Param("activeVendors") Set<Vendor> activeVendors);

}
