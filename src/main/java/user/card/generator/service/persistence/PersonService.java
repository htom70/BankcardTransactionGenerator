package user.card.generator.service.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.person.Person;
import user.card.generator.repository.PersonRepository;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public void saveALlPeople(List<Person> people) {
        personRepository.saveAll(people);
    }
}
