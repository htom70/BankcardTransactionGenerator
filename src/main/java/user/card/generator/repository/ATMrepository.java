package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.city.City;
import user.card.generator.domain.vendor.ATM;

import java.util.List;

@Repository
public interface ATMrepository extends JpaRepository<ATM,Long> {

    public List<ATM> findAllByCityIn(List<City> cities);}
