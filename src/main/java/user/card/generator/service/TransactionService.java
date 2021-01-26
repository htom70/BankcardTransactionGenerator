package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    public void saveAll(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
