package user.card.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.person.PersonCategory;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    public Person findByCardNumber(String cardNumber);

    @Query(value = "SELECT p FROM Person p WHERE p.personCategory =:category")
    public List<Person> findPeopleUponCategory(@Param("category") PersonCategory personCategory);

    public List<Person> findAllByCardNumber(String cardNumber);

}
