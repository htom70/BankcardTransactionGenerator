package user.card.generator.domain.vendor;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.stereotype.Service;
import user.card.generator.domain.city.City;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vendor {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String vendorCode;

    @ManyToOne
    private City city;

    public Vendor(String vendorCode, City city) {
        this.city = city;
        this.vendorCode = vendorCode;
    }
}


