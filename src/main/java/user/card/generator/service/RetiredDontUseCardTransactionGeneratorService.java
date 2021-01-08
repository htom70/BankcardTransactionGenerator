package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.card.generator.domain.AtmOwnerBank;
import user.card.generator.domain.ProductCategory;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.country.Vendor;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.person.PersonCategory;
import user.card.generator.domain.transaction.Transaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.repository.PersonRepository;
import user.card.generator.repository.TransactionRepository;
import user.card.generator.repository.VendorRepository;
import user.card.generator.time.TimestampGenerator;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;

@Service
public class RetiredDontUseCardTransactionGeneratorService {

    @Value("${retiredDontUseCard.ATM.monthly.transactionDate.Min}")
    private int retiredDontUseCardATMmonthlyTransactionDateMin;
    @Value("${retiredDontUseCard.ATM.monthly.transactionDate.Max}")
    private int retiredDontUseCardATMmonthlyTransactionDateMax;
    @Value("${retiredDontUseCard.ATM.monthly.amount.Min}")
    private int retiredDontUseCardATMmonthlyAmountMin;
    @Value("${retiredDontUseCard.ATM.monthly.amount.Max}")
    private int retiredDontUseCardATMmonthlyAmountMax;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public RetiredDontUseCardTransactionGeneratorService(PersonRepository personRepository, VendorRepository vendorRepository, TransactionRepository transactionRepository) {
        this.personRepository = personRepository;
        this.vendorRepository = vendorRepository;
        this.transactionRepository = transactionRepository;
    }

    private List<Person> retiredDontUseCardPeople = new ArrayList<>();

    public void generate(Random random, Year year) {
        retiredDontUseCardPeople = personRepository.findPeopleUponCategory(PersonCategory.RETIRED_DONT_USE_CARD_AND_INTERNET);
        for (Person person : retiredDontUseCardPeople) {
            generateCurrentPersonTransactions(person, random, year);
        }
    }

    private void generateCurrentPersonTransactions(Person person, Random random, Year year) {
        Set<Transaction> transactions = new HashSet<>();
        for (int i = 1; i <= 12; i++) {
            int dayOrdinalNumber = retiredDontUseCardATMmonthlyTransactionDateMin +
                    random.nextInt(retiredDontUseCardATMmonthlyTransactionDateMax - retiredDontUseCardATMmonthlyTransactionDateMin);
            LocalDate localDate = LocalDate.of(year.getValue(), i, dayOrdinalNumber);
            Timestamp timestamp = TimestampGenerator.generate(random, localDate);
            int amount = retiredDontUseCardATMmonthlyAmountMin + random.nextInt(retiredDontUseCardATMmonthlyAmountMax - retiredDontUseCardATMmonthlyAmountMin);
            List<Vendor> vendors = vendorRepository.findAllByCountryName("HU");
            String vendorCode = vendors.get(random.nextInt(vendors.size())).getVendorCode();
            AtmOwnerBank[] ownerBanks = AtmOwnerBank.values();
            AtmOwnerBank ownerBank = ownerBanks[random.nextInt(ownerBanks.length)];
            Transaction transaction = new Transaction(person.getCardNumber(), TransactionType.ATM, timestamp, amount, "HUF", ResponseCode.OK,
                    "HU", vendorCode, ProductCategory.CASH, ownerBank);
            transactionRepository.save(transaction);
            transactions.add(transaction);
        }
        person.setTransactions(transactions);
        personRepository.save(person);
    }
}
