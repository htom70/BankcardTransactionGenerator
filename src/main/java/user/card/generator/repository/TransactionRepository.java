package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import user.card.generator.domain.transaction.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
