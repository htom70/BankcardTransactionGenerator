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
    CityService cityService;

    @Autowired
    CountryService countryService;

    public  void process(CurrentYear currentYear) {
        Country country = countryService.findByCountryCode("HU");
        List<City> cities = cityService.findAllByCountry(country);
//        List<Person> retiredDontUseCard = personService.findPeopleUponCategory(PersonCategory.RETIRED_DONT_USE_CARD_AND_INTERNET);
//        retiredDontUseCardTransaction.processTransaction(retiredDontUseCard,currentYear,cities);
        List<Person> retiredUseCard = personService.findPeopleUponCategory(PersonCategory.RETIRED_USE_CARD_AND_INTERNET);
        retiredUseCardAndInternetTransaction.processTransaction(retiredUseCard,currentYear);
    }


}
