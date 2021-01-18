package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.vendor.ATM;

@Repository
public interface ATMrepository extends JpaRepository<ATM,Long> {
}
