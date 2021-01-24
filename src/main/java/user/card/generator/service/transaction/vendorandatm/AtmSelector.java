package user.card.generator.service.transaction.vendorandatm;

import org.springframework.beans.factory.annotation.Autowired;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.service.ATMservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AtmSelector {

    @Autowired
    ATMservice atMservice;

    private int homeRatePercent;
    private int privateBankPercent;
    private List<ATM> atmsNotInCity = new ArrayList<>();
    private List<ATM> atmsInHomeCity = new ArrayList<>();
    private Random random;

    public AtmSelector(int homeRatePercent, int privateBankPercent) {
        this.homeRatePercent = homeRatePercent;
        this.privateBankPercent = privateBankPercent;
        random = new Random();
    }

    public ATM selectAtm(Person person) {
        List<ATM> atms = new ArrayList<>();
        List<ATM> selectedAtms;
        if (atmsInHomeCity.isEmpty()) {
            atmsInHomeCity = atMservice.findAllByCity(person.getCity());
        }
        if (atmsNotInCity.isEmpty()) {
            atmsNotInCity = atMservice.findAllByCityIsNot(person.getCity());
        }
        int numberForSelectAtmInHomeCity = random.nextInt(100);
        if (numberForSelectAtmInHomeCity < homeRatePercent) {
            atms.addAll(atmsInHomeCity);
        } else {
            atms.addAll(atmsNotInCity);
        }
        int numberForSelectAtmUponPrivateBank = random.nextInt(100);
        if (numberForSelectAtmUponPrivateBank < privateBankPercent) {
            selectedAtms = atms.stream()
                    .filter(atm -> atm.getBank().equals(person.getBank()))
                    .collect(Collectors.toList());
        } else {
            selectedAtms = atms.stream()
                    .filter(atm -> !atm.getBank().equals(person.getBank()))
                    .collect(Collectors.toList());
        }
        return selectedAtms.get(random.nextInt(selectedAtms.size()));
    }
}
