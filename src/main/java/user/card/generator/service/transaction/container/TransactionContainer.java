package user.card.generator.service.transaction.container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.person.PersonCategory;
import user.card.generator.service.CityService;
import user.card.generator.service.CountryService;
import user.card.generator.service.PersonService;
import user.card.generator.time.CurrentYear;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionContainer {

    @Autowired
    PersonService personService;

    @Autowired
    RetiredDontUseCardTransaction retiredDontUseCardTransaction;

    @Autowired
    RetiredUseCardAndInternetTransaction retiredUseCardAndInternetTransaction;

    @Autowired
    OrdinaryUserUseCardAndInternetTransaction ordinaryUserUseCardAndInternetTransaction;

    @Autowired
    OrdinaryUseCardDontUseInternetTransaction ordinaryUseCardDontUseInternetTransaction;

    @Autowired
    OrdinaryUserDontUseCardTransaction ordinaryUserDontUseCardTransaction;

    @Autowired
    VipUserTransaction vipUserTransaction;

    @Autowired
    CityService cityService;

    @Autowired
    CountryService countryService;

    public void process(CurrentYear currentYear) {
        Country country = countryService.findByCountryCode("HU");
        List<City> cities = cityService.findAllByCountry(country);
        List<Person> retiredDontUseCardPeople = personService.findPeopleUponCategory(PersonCategory.RETIRED_DONT_USE_CARD_AND_INTERNET);
        retiredDontUseCardTransaction.processTransaction(retiredDontUseCardPeople, currentYear, cities);
        List<Person> retiredUseCardPeople = personService.findPeopleUponCategory(PersonCategory.RETIRED_USE_CARD_AND_INTERNET);
        retiredUseCardAndInternetTransaction.processTransaction(retiredUseCardPeople,currentYear);
        List<Person> ordinaryUserUseCardAndInternetPeople = personService.findPeopleUponCategory(PersonCategory.ORDINARYUSER_USE_CARD_AND_INTERNET);
        ordinaryUserUseCardAndInternetTransaction.processTransaction(ordinaryUserUseCardAndInternetPeople,currentYear);
        List<Person> ordinaryUserUseCardDontUseInternetPeople = personService.findPeopleUponCategory(PersonCategory.ORDINARYUSER_USE_CARD_AND_INTERNET);
        ordinaryUseCardDontUseInternetTransaction.processTransaction(ordinaryUserUseCardDontUseInternetPeople,currentYear);
        List<Person> ordinaryUserDontUseCardAndInternetPeople = personService.findPeopleUponCategory(PersonCategory.ORDINARYUSER_USE_CARD_AND_INTERNET);
        ordinaryUserDontUseCardTransaction.processTransaction(ordinaryUserDontUseCardAndInternetPeople,currentYear);
        List<Person> vipPeople = personService.findPeopleUponCategory(PersonCategory.VIP_USER);
        vipUserTransaction.processTransaction(vipPeople, currentYear);


        //        List<Person> testUsers = new ArrayList<>();
//        testUsers.add(retiredUseCard.get(0));
//        retiredUseCardAndInternetTransaction.processTransaction(testUsers,currentYear);
    }


}
