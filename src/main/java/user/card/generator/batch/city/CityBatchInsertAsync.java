package user.card.generator.batch.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.domain.country.City;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CityBatchInsertAsync {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${jdbc.batch_insert_size}")
    private int batchSize;

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchInsertAsync(List<City> cities) {

        String sql = "INSERT INTO city (county, name, postal_code)" + "VALUES(?,?,?)";
        final AtomicInteger sublists = new AtomicInteger();

        CompletableFuture[] futures = cities.stream()
                .collect(Collectors.groupingBy(t -> sublists.getAndIncrement() / batchSize))
                .values()
                .stream()
                .map(ul -> runBatchInsert(ul, sql))
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> run = CompletableFuture.allOf(futures);
        try {
            run.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private CompletableFuture<Void> runBatchInsert(List<City> cities, String sql) {
        return CompletableFuture.runAsync(() -> {
            jdbcTemplate.batchUpdate(sql, new CityBatchPreparedStatementSetter(cities));
        }, executor);
    }
}
