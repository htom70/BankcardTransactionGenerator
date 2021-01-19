package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.city.City;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.vendor.Bank;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {

    public Bank findByName(String name);

    @Query(value = "SELECT b FROM Bank b WHERE b.country =:country")
    public List<Bank> findAllByCountry(@Param("country") Country country);
}
