package user.card.generator.service.transaction.vendorandatm;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.ATMservice;
import user.card.generator.service.VendorService;

import java.util.List;

@Data
public class VendorAndAtmSelector {

    @Autowired
    VendorService vendorService;

    @Autowired
    ATMservice atMservice;

    private Person person;
    private double vendorByHomeRate;
    private double atmByHomeRate;
    private double atmByPrivateBankRate;
    private List<Vendor> vendorsAtHome;
    private List<Vendor> vendorsInAlienCities;
    private List<ATM> atmsAtHome;
    private List<ATM> atmsInAlienCities;

    public VendorAndAtmSelector(Person person, double vendorByHomeRate, double atmByHomeRate, double atmByPrivateBankRate) {
        this.person = person;
        this.vendorByHomeRate = vendorByHomeRate;
        this.atmByHomeRate = atmByHomeRate;
        this.atmByPrivateBankRate = atmByPrivateBankRate;
    }

    public List<Vendor> getVendorsAtHome() {
        if (vendorsAtHome.isEmpty() || vendorsAtHome == null) {
            vendorsAtHome=vendorService.findAllByCity(person.getCity());
        }
        return vendorsAtHome;
    }

    public List<Vendor> getVendorsInAlienCities() {
        if (vendorsInAlienCities.isEmpty() || vendorsInAlienCities == null) {
            vendorsInAlienCities=vendorService.findAllByCityIsNot(person.getCity());
        }
        return vendorsInAlienCities;
    }

    public List<ATM> getAtmsAtHome() {
        if (atmsAtHome.isEmpty() || atmsAtHome == null) {
            atmsAtHome=atMservice.findAllByCity(person.getCity());
        }
        return atmsAtHome;
    }

    public List<ATM> getAtmsInAlienCities() {
        if (atmsInAlienCities.isEmpty() || atmsInAlienCities == null) {
            atmsInAlienCities=atMservice.findAllByCityIsNot(person.getCity());
        }
        return atmsInAlienCities;
    }
}
