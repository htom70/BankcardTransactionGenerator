package user.card.generator.domain.city;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Vendor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @ManyToOne
    private County county;

    @OneToMany
    private Set<Vendor> vendors = new HashSet<>();

    @OneToMany
    private Set<ATM> atms = new HashSet<>();

    public City(String name) {
        this.name = name;
    }

    public void addVendor(Vendor vendor) {
        vendors.add(vendor);
    }

    public void addATM(ATM atm) {
        atms.add(atm);
    }


    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", county=" + county +
                '}';
    }
}
