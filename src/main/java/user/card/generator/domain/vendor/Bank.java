package user.card.generator.domain.vendor;

import lombok.Data;
import lombok.NoArgsConstructor;
import user.card.generator.domain.country.Country;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String name;

    @ManyToOne
    private Country country;

    public Bank(String name,Country country) {
        this.name = name;
        this.country = country;
    }
}
