package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.repository.TransactionRepository;
import user.card.generator.repository.VendorRepository;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    VendorRepository vendorRepository;

    public void saveAll(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
        transactionRepository.flush();
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findAllNormalTransaction() {
        return transactionRepository.findAllNormalTransaction();
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
