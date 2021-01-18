package user.card.generator.batch.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.batch.city.CityBatchPreparedStatementSetter;
import user.card.generator.domain.country.City;
import user.card.generator.domain.person.Person;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PersonBatchInsertAsync {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${jdbc.batch_insert_size}")
    private int batchSize;

    private static final ExecutorService executor = Executors.newFixedThreadPool(100);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchInsertAsync(List<Person> people) {

        String sql = "INSERT INTO person (card_number, income, person_category)" + "VALUES(?,?,?)";
        final AtomicInteger sublists = new AtomicInteger();

        Instant start = Instant.now();

        CompletableFuture[] futures = people.stream()
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
        Instant end = Instant.now();
        long elapsedTime = Duration.between(start, end).toMillis();
        System.out.println("Person batch running time: " + elapsedTime);
    }

    private CompletableFuture<Void> runBatchInsert(List<Person> people, String sql) {
        return CompletableFuture.runAsync(() -> {
            jdbcTemplate.batchUpdate(sql, new PersonBatchPreparedStatementSetter(people));
        }, executor);
    }
}
