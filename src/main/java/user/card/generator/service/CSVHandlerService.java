package user.card.generator.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import user.card.generator.domain.city.City;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.repository.CountryRepository;
import user.card.generator.repository.TransactionRepository;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CSVHandlerService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public CSVHandlerService(CountryRepository countryRepository, TransactionRepository transactionRepository) {
        this.countryRepository = countryRepository;
        this.transactionRepository = transactionRepository;
    }

    public void writeData(String filePath) {

        Page<Transaction> transactions = transactionRepository.findAll(PageRequest.of(1,1000));

        File file = new File(filePath);
        try {
            FileWriter fileWriter = new FileWriter(file);
            CSVWriter writer = new CSVWriter(fileWriter);
            String[] header = {"cardNumber", "transaction type", "timestamp", "amount", "currency", "country", "response code", "vendor code", "product category", "bank name", "card's issuer bank"};
            writer.writeNext(header);
            for (Transaction transaction : transactions) {
                String[] data = {transaction.getCardNumber(), transaction.getTransactionType().toString(), String.valueOf(transaction.getTimestamp()),
                        String.valueOf(transaction.getAmount()), transaction.getCurrencyName(), transaction.getCountryName(), transaction.getResponseCode().toString(),
                        transaction.getVendorCode(), "BB"};
                writer.writeNext(data, true);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
