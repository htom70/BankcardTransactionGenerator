package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.country.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
