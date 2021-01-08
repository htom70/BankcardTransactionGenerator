package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user.card.generator.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
