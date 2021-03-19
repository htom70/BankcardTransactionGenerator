package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.city.City;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Bank;

import java.util.List;

@Repository
public interface ATMrepository extends JpaRepository<ATM,Long> {

    public List<ATM> findAllByCity(City city);

    public List<ATM> findAllByCityIsNot(City city);

    public List<ATM> findAllByCityAndBank(City city, Bank bank);

    public List<ATM> findAllByCityIsNotAndBank(City city, Bank bank);

    public List<ATM> findAllByCityAndBankIsNot(City city, Bank bank);

    public List<ATM> findAllByCityIsNotAndBankIsNot(City city, Bank bank);
}
