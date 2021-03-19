package user.card.generator.service.transaction.vendorandatm;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Bank;
import user.card.generator.service.ATMservice;

import java.util.*;

@Setter
@Service
public class AtmSelector {

    @Autowired
    ATMservice atMservice;

    private List<City> storedCity = new ArrayList<>();
    private Person storedPerson;
    private int homeRatePercent;
    private int privateBankPercent;
    private List<ATM> atms = new ArrayList<>();
    List<ATM> selectedAtmsInHomeCityUponPrivateBank = new ArrayList<>();
    Map<City, List<ATM>> selectedAtmsInHomeCityUponPrivateBankStoredByCity = new HashMap<>();
    List<ATM> selectedAtmsInForeignCitiesUponPrivateBank = new ArrayList<>();
    Map<City, List<ATM>> selectedAtmsInForeignCitiesUponPrivateBankStoredByCity = new HashMap<>();
    List<ATM> selectedAtmsInHomeCityUponForeignBank = new ArrayList<>();
    Map<City, List<ATM>> selectedAtmsInHomeCityUponForeignBankStoredByCity = new HashMap<>();
    List<ATM> selectedAtmsInForeignCityUponForeignBank = new ArrayList<>();
    Map<City, List<ATM>> selectedAtmsInForeignCityUponForeignBankStoredByCity = new HashMap<>();


    private Random random = new Random();


    public ATM selectAtm(Person person) {
//        Instant start = Instant.now();
        ATM result;
        City homeCity = person.getCity();
        Bank privateBank = person.getBank();
        List<ATM> atmsInHomeCity = new ArrayList<>();
        List<ATM> atmsInForeignCities = new ArrayList<>();
        if (atms.isEmpty()) {
            atms = atMservice.findAll();
        }
        if (!storedCity.contains(homeCity)) {
            storedCity.add(homeCity);
//            atmsInHomeCity = atms.parallelStream()
//                    .filter(atm -> atm.getCity().equals(currentCity))
//                    .collect(Collectors.toList());
            atmsInHomeCity = atMservice.findAllByCity(homeCity);
//            atmsInForeignCities = atms.parallelStream()
//                    .filter(atm -> !atm.getCity().equals(currentCity))
//                    .collect(Collectors.toList());
            atmsInForeignCities = atMservice.findAllByCityIsNot(homeCity);

//            selectedAtmsInHomeCityUponPrivateBank = atmsInHomeCity.parallelStream()
//                    .filter(atm -> atm.getBank().equals(person.getBank()))
//                    .collect(Collectors.toList());
            selectedAtmsInHomeCityUponPrivateBank = atMservice.findAllByCityAndBank(homeCity, privateBank);

//            selectedAtmsInForeignCitiesUponPrivateBank = atmsInForeignCities.parallelStream()
//                    .filter(atm -> atm.getBank().equals(person.getBank()))
//                    .collect(Collectors.toList());
            selectedAtmsInForeignCitiesUponPrivateBank = atMservice.findAllByCityIsNotAndBank(homeCity, privateBank);

//            selectedAtmsInHomeCityUponForeignBank = atmsInHomeCity.parallelStream()
//                    .filter(atm -> !atm.getBank().equals(person.getBank()))
//                    .collect(Collectors.toList());
            selectedAtmsInHomeCityUponForeignBank = atMservice.findAllByCityAndBankIsNot(homeCity, privateBank);

//            selectedAtmsInForeignCityUponForeignBank = atmsInForeignCities.parallelStream()
//                    .filter(atm -> !atm.getCity().equals(homeCity))
//                    .collect(Collectors.toList());
            selectedAtmsInForeignCityUponForeignBank = atMservice.findAllByCityIsNotAndBankIsNot(homeCity, privateBank);

            selectedAtmsInHomeCityUponForeignBankStoredByCity.put(homeCity, selectedAtmsInHomeCityUponForeignBank);
            selectedAtmsInForeignCitiesUponPrivateBankStoredByCity.put(homeCity, selectedAtmsInForeignCitiesUponPrivateBank);
            selectedAtmsInHomeCityUponPrivateBankStoredByCity.put(homeCity, selectedAtmsInHomeCityUponPrivateBank);
            selectedAtmsInForeignCityUponForeignBankStoredByCity.put(homeCity, selectedAtmsInForeignCityUponForeignBank);
        }

//        if (!person.equals(storedPerson)) {
//            storedPerson = person;
//            atmsInForeignCities.addAll(atms);
//            atmsInHomeCity = atms.parallelStream()
//                    .filter(atm -> atm.getCity().equals(person.getCity()))
//                    .collect(Collectors.toList());
//            atmsInForeignCities.removeAll(atmsInHomeCity);
//            selectedAtmsInHomeCityUponPrivateBank = atmsInHomeCity.parallelStream()
//                    .filter(atm -> atm.getBank().equals(person.getBank()))
//                    .collect(Collectors.toList());
//
//            selectedAtmsInForeignCitiesUponPrivateBank = atmsInForeignCities.parallelStream()
//                    .filter(atm -> atm.getBank().equals(person.getBank()))
//                    .collect(Collectors.toList());
//            selectedAtmsInHomeCityUponForeignBank.addAll(atmsInHomeCity);
//            selectedAtmsInHomeCityUponForeignBank.removeAll(selectedAtmsInHomeCityUponPrivateBank);
//            selectedAtmsInForeignCityUponForeignBank.addAll(atmsInForeignCities);
//            selectedAtmsInForeignCityUponForeignBank.removeAll(selectedAtmsInForeignCitiesUponPrivateBank);
//            System.out.println("listák aktualizálva");
//        }
        int numberForSelectAtmInHomeCity = random.nextInt(100);
        int numberForSelectAtmUponPrivateBank = random.nextInt(100);
        selectedAtmsInHomeCityUponPrivateBank = selectedAtmsInHomeCityUponPrivateBankStoredByCity.get(homeCity);
        selectedAtmsInHomeCityUponForeignBank = selectedAtmsInHomeCityUponForeignBankStoredByCity.get(homeCity);
        selectedAtmsInForeignCitiesUponPrivateBank=selectedAtmsInForeignCitiesUponPrivateBankStoredByCity.get(homeCity);
        selectedAtmsInForeignCityUponForeignBank = selectedAtmsInForeignCityUponForeignBankStoredByCity.get(homeCity);

        if (numberForSelectAtmInHomeCity < homeRatePercent && numberForSelectAtmUponPrivateBank < privateBankPercent) {
            result = selectedAtmsInHomeCityUponPrivateBank.get(random.nextInt(selectedAtmsInHomeCityUponPrivateBank.size()));
        } else if (numberForSelectAtmInHomeCity < homeRatePercent && numberForSelectAtmUponPrivateBank >= privateBankPercent) {
            result = selectedAtmsInHomeCityUponForeignBank.get(random.nextInt(selectedAtmsInHomeCityUponForeignBank.size()));
        } else if (numberForSelectAtmInHomeCity > homeRatePercent && numberForSelectAtmUponPrivateBank < privateBankPercent) {
            result = selectedAtmsInForeignCitiesUponPrivateBank.get(random.nextInt(selectedAtmsInForeignCitiesUponPrivateBank.size()));
        } else {
            result = selectedAtmsInForeignCityUponForeignBank.get(random.nextInt(selectedAtmsInForeignCityUponForeignBank.size()));
        }
//        Instant end = Instant.now();
//        long elapsedTime = Duration.between(start, end).toMillis();
//        if (elapsedTime > 0) {
//            System.out.println("selectAtm futási ideje: " + elapsedTime + " ms");
//        }
        return result;
    }
}
