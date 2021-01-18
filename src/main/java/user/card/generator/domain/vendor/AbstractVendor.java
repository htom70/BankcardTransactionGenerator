package user.card.generator.domain.vendor;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ManyToAny;
import user.card.generator.domain.city.City;
import user.card.generator.domain.country.Country;

import javax.persistence.*;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractVendor {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;



}
