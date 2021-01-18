package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.repository.ATMrepository;

import java.util.List;

@Service
public class ATMservice {

    @Autowired
    ATMrepository atMrepository;

    public void saveAllATMs(List<ATM> atms) {
        atMrepository.saveAll(atms);
    }

    public void generateHungarianATMs() {
        
    }
}
