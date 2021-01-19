package user.card.generator.domain.city;

import lombok.Data;
import lombok.NoArgsConstructor;
import user.card.generator.domain.country.Country;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class County {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @ManyToOne
    private Country country;

    public County(String name, Country country) {
        this.name = name;
        this.country = country;
    }
}
