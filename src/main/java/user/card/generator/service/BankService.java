package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.BankInHungary;
import user.card.generator.domain.vendor.Bank;
import user.card.generator.repository.BankRepository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BankService {

    @Autowired
    BankRepository bankRepository;

    public void saveAllBanks(List<Bank> banks) {
        bankRepository.saveAll(banks);
    }

    public Bank findByName(String name) {
        return bankRepository.findByName(name);
    }

    public void generateHungarianBanks() {
        List<Bank> banks = Stream.of(BankInHungary.values())
                .map(n -> new Bank(n.toString()))
                .collect(Collectors.toList());
        saveAllBanks(banks);
    }
}
