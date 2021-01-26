package user.card.generator.service.transaction.vendorandatm;

import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.service.ATMservice;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@Service
public class AtmSelector {

    @Autowired
    ATMservice atMservice;

    private Person storedPerson;
    private int homeRatePercent;
    private int privateBankPercent;
    private List<ATM> atms = new ArrayList<>();
    List<ATM> selectedAtmsInHomeCityUponPrivateBank = new ArrayList<>();
    List<ATM> selectedAtmsInForeignCitiesUponPrivateBank = new ArrayList<>();
    List<ATM> selectedAtmsInHomeCityUponForeignBank = new ArrayList<>();
    List<ATM> selectedAtmsInForeignCityUponForeignBank = new ArrayList<>();

    private Random random = new Random();


    public ATM selectAtm(Person person) {
        ATM result;
        List<ATM> atmsInHomeCity = new ArrayList<>();
        List<ATM> atmsInForeignCities = new ArrayList<>();
        if (atms.isEmpty()) {
            atms = atMservice.findAll();
        }
        if (!person.equals(storedPerson)) {
            storedPerson = person;
            atmsInForeignCities.addAll(atms);
            atmsInHomeCity = atms.parallelStream()
                    .filter(atm -> atm.getCity().equals(person.getCity()))
                    .collect(Collectors.toList());
            atmsInForeignCities.removeAll(atmsInHomeCity);
            selectedAtmsInHomeCityUponPrivateBank = atmsInHomeCity.parallelStream()
                    .filter(atm -> atm.getBank().equals(person.getBank()))
                    .collect(Collectors.toList());
            selectedAtmsInForeignCitiesUponPrivateBank = atmsInForeignCities.parallelStream()
                    .filter(atm -> atm.getBank().equals(person.getBank()))
                    .collect(Collectors.toList());
            selectedAtmsInHomeCityUponForeignBank.addAll(atmsInHomeCity);
            selectedAtmsInHomeCityUponForeignBank.removeAll(selectedAtmsInHomeCityUponPrivateBank);
            selectedAtmsInForeignCityUponForeignBank.addAll(atmsInForeignCities);
            selectedAtmsInForeignCityUponForeignBank.removeAll(selectedAtmsInForeignCitiesUponPrivateBank);
            System.out.println("listák aktualizálva");
        }
        int numberForSelectAtmInHomeCity = random.nextInt(100);
        int numberForSelectAtmUponPrivateBank = random.nextInt(100);

        if (numberForSelectAtmInHomeCity < homeRatePercent && numberForSelectAtmUponPrivateBank < privateBankPercent) {
            result = selectedAtmsInHomeCityUponPrivateBank.get(random.nextInt(selectedAtmsInHomeCityUponPrivateBank.size()));
        } else if (numberForSelectAtmInHomeCity < homeRatePercent && numberForSelectAtmUponPrivateBank >= privateBankPercent) {
            result = selectedAtmsInHomeCityUponForeignBank.get(random.nextInt(selectedAtmsInHomeCityUponForeignBank.size()));
        } else if (numberForSelectAtmInHomeCity > homeRatePercent && numberForSelectAtmUponPrivateBank < privateBankPercent) {
            result = selectedAtmsInForeignCitiesUponPrivateBank.get(random.nextInt(selectedAtmsInForeignCitiesUponPrivateBank.size()));
        } else {
            result = selectedAtmsInForeignCityUponForeignBank.get(random.nextInt(selectedAtmsInForeignCityUponForeignBank.size()));
        }
        return result;
    }
}
