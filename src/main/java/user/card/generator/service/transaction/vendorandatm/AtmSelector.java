package user.card.generator.service.transaction.vendorandatm;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.service.ATMservice;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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
    Map<City, List<ATM>> selectedAtmsInHomeCityUponPrivateBankStoredCity = new HashMap<>();
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
        City currentCity = person.getCity();
        List<ATM> atmsInHomeCity = new ArrayList<>();
        List<ATM> atmsInForeignCities = new ArrayList<>();
        if (atms.isEmpty()) {
            atms = atMservice.findAll();
        }
        if (!storedCity.contains(currentCity)) {
            storedCity.add(currentCity);
            atmsInHomeCity = atms.parallelStream()
                    .filter(atm -> atm.getCity().equals(person.getCity()))
                    .collect(Collectors.toList());
            atmsInForeignCities = atms.parallelStream()
                    .filter(atm -> !atm.getCity().equals(currentCity))
                    .collect(Collectors.toList());

            selectedAtmsInHomeCityUponPrivateBank = atmsInHomeCity.parallelStream()
                    .filter(atm -> atm.getBank().equals(person.getBank()))
                    .collect(Collectors.toList());
            selectedAtmsInHomeCityUponPrivateBankStoredCity.put(currentCity, selectedAtmsInHomeCityUponPrivateBank);

            selectedAtmsInForeignCitiesUponPrivateBank = atmsInForeignCities.parallelStream()
                    .filter(atm -> atm.getBank().equals(person.getBank()))
                    .collect(Collectors.toList());
            selectedAtmsInForeignCitiesUponPrivateBankStoredByCity.put(currentCity, selectedAtmsInForeignCitiesUponPrivateBank);

            selectedAtmsInHomeCityUponForeignBank = atmsInHomeCity.parallelStream()
                    .filter(atm -> !atm.getBank().equals(person.getBank()))
                    .collect(Collectors.toList());
            selectedAtmsInHomeCityUponForeignBankStoredByCity.put(currentCity, selectedAtmsInHomeCityUponForeignBank);

            selectedAtmsInForeignCityUponForeignBank = atmsInForeignCities.parallelStream()
                    .filter(atm -> !atm.getCity().equals(currentCity))
                    .collect(Collectors.toList());
            selectedAtmsInForeignCityUponForeignBankStoredByCity.put(currentCity, selectedAtmsInForeignCityUponForeignBank);
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
        selectedAtmsInHomeCityUponPrivateBank = selectedAtmsInHomeCityUponPrivateBankStoredCity.get(currentCity);
        selectedAtmsInHomeCityUponForeignBank = selectedAtmsInHomeCityUponForeignBankStoredByCity.get(currentCity);
        selectedAtmsInForeignCitiesUponPrivateBank=selectedAtmsInForeignCitiesUponPrivateBankStoredByCity.get(currentCity);
        selectedAtmsInForeignCityUponForeignBank = selectedAtmsInForeignCityUponForeignBankStoredByCity.get(currentCity);

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
