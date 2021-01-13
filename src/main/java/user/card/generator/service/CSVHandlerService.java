package user.card.generator.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import user.card.generator.domain.country.City;
import user.card.generator.domain.country.Country;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.repository.CountryRepository;
import user.card.generator.repository.TransactionRepository;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
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
                        transaction.getVendorCode(), transaction.getProductCategory().toString(), "BB", transaction.getAtmOwnerBank().toString()};
                writer.writeNext(data, true);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<City> citiesReadFromCsv()  {
        Reader reader = null;
        List<City> cities = new ArrayList<>();
        try {
            reader = Files.newBufferedReader(Paths.get("C:\\Users\\machine\\Downloads\\iranyitoszamok.csv"),Charset.forName("ISO-8859-1"));
            CSVReader csvReader = new CSVReader(reader);

            String[] firstLine=csvReader.readNext();
            List<String[]> records = csvReader.readAll();
            Iterator<String[]> iterator = records.iterator();
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                City city = new City();
                city.setPostalCode(record[0]);
                city.setName(record[1]);
                city.setCounty(record[2]);
                cities.add(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("City list length: " + cities.size());
        return cities;
    }
}
