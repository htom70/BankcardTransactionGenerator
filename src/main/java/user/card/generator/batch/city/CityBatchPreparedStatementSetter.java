package user.card.generator.batch.city;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import user.card.generator.domain.country.City;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CityBatchPreparedStatementSetter implements BatchPreparedStatementSetter {

    private List<City> cities;

    public CityBatchPreparedStatementSetter(List<City> cities) {
        super();
        this.cities = cities;
    }

    @Override
    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
        City city = cities.get(i);
        preparedStatement.setString(1, city.getPostalCode());
        preparedStatement.setString(2, city.getName());
        preparedStatement.setString(3, city.getCounty());
    }

    @Override
    public int getBatchSize() {
        return cities.size();
    }
}
