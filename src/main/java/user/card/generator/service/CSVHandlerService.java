package user.card.generator.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import user.card.generator.domain.city.City;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.repository.CountryRepository;
import user.card.generator.repository.TransactionRepository;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class CSVHandlerService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void writeData(String filePath) {

        Instant start = Instant.now();
        int numberOfRows = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM creditcard_transaction.transaction", Integer.class);
        System.out.println("Rekordok száma: " + numberOfRows);
        int pages=numberOfRows/1000000;
        Page<Transaction> transactions = null;
//        List<Transaction> transactions = transactionRepository.findAll();

        File file = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            CSVWriter writer = new CSVWriter(fileWriter);
            String[] header = {"cardNumber", "transaction type", "timestamp", "amount", "currency", "country", "response code", "vendor code",
                    "field1", "field2", "field3", "field4", "field5", "field6", "field7", "field8", "field9", "field10", "field11", "field12", "field13",
                    "field14", "field15", "field16", "field17", "field18", "field19", "field20", "field21", "field22", "field23", "field24", "field25",
                    "field26", "field27", "field28", "field29", "field30", "field31", "field32", "field33", "field34", "field35", "field36", "field37",
                    "field38", "field39", "field40","fraud"};
            writer.writeNext(header);
            String[] data = new String[49];
            for (int i = 0; i < pages+1; i++) {
                transactions= transactionRepository.findAll(PageRequest.of(i, 1000000));
                for (Transaction transaction : transactions) {
                    List<Optional<String>> extraFields = transaction.getAllExtraFields();
                    data[0] = transaction.getCardNumber();
                    data[1] = transaction.getTransactionType().toString();
                    data[2] = String.valueOf(transaction.getTimestamp());
                    data[3] = String.valueOf(transaction.getAmount());
                    data[4] = transaction.getCurrencyName();
                    data[5] = transaction.getCountryName();
                    data[6] = transaction.getResponseCode().toString();
                    data[7] = transaction.getVendorCode();
                    for (int j = 0; j < extraFields.size(); j++) {
                        data[j + 7] = extraFields.get(j).isPresent() ? extraFields.get(j).get() : "null";
                    }
                    writer.writeNext(data, true);
                }
            }
            writer.close();
            Instant end = Instant.now();
            long elapsedTime = Duration.between(start, end).toMillis()/1000;
            System.out.println("CSV írás időszükséglete: " + elapsedTime+" másodperc");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
