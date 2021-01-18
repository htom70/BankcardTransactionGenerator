package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.vendor.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {

    public Bank findByName(String name);
}
