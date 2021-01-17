package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.city.County;

@Repository
public interface CountyRepository extends JpaRepository<County, Long> {

    public County findByName(String name);
}
