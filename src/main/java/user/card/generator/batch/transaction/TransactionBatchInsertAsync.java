package user.card.generator.batch.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import user.card.generator.batch.person.PersonBatchPreparedStatementSetter;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.Transaction;

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
public class TransactionBatchInsertAsync {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${jdbc.batch_insert_size}")
    private int batchSize;

    private static final ExecutorService executor = Executors.newFixedThreadPool(32);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchInsertAsync(List<Transaction> transactions) {

        String sql = "INSERT INTO transaction (card_number, transaction_type, timestamp,amount,currency_name,response_code,country_name, vendor_code," +
                "field1,field2,field3,field4,field5,field6,field7,field8,field9,field10,field11,field12,field13,field14,field15,field16,field17, "+
                "field18,field19,field20,field21,field22,field23,field24,field25,field26,field27,field28,field29,field30,field31,field32,field33,"+
                "field34,field35,field36,field37,field38,field39,field40,fraud"+
                ")" + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final AtomicInteger sublists = new AtomicInteger();

        Instant start = Instant.now();
        CompletableFuture[] futures = transactions.stream()
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
        System.out.println("Elapsed Time: " + elapsedTime);
    }

    private CompletableFuture<Void> runBatchInsert(List<Transaction> transactions, String sql) {
        return CompletableFuture.runAsync(() -> {
            jdbcTemplate.batchUpdate(sql, new TransactionBatchPreparedStatementSetter(transactions));
        }, executor);
    }

}
