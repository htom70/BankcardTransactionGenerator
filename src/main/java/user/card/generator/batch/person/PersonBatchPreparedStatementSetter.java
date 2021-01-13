package user.card.generator.batch.person;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import user.card.generator.domain.country.City;
import user.card.generator.domain.person.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PersonBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

    private List<Person> people;

    public PersonBatchPreparedStatementSetter(List<Person> people) {
        super();
        this.people = people;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
        Person person = people.get(i);
        preparedStatement.setString(1, person.getCardNumber());
        preparedStatement.setString(2, String.valueOf(person.getIncome()));
        preparedStatement.setString(3, String.valueOf(person.getPersonType().ordinal()));
    }

    @Override
    public int getBatchSize() {
        return people.size();
    }
}
