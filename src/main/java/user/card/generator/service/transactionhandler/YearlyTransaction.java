package user.card.generator.service.transactionhandler;

import user.card.generator.domain.city.City;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.AbstractVendor;
import user.card.generator.domain.vendor.Vendor;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;

public class YearlyTransaction extends AbstractTransaction {

    private TransactionType transactionType;
    private Person person;

    public YearlyTransaction(TransactionType transactionType, Person person) {
        this.transactionType = transactionType;
        this.person = person;
    }

    @Override
    public Map<LocalDate, List<SimplePreTransaction>> generate(List<LocalDate> days, TransactionProperty transactionProperty, Year year) {
        Map<LocalDate, List<SimplePreTransaction>> result = new HashMap<>();
        Random random = new Random();
        int occasionMin = transactionProperty.getYearlyOccasionMin();
        int occasionMax = transactionProperty.getYearlyOccasionMax();
        int amountMin = transactionProperty.getYearlyAmountMin();
        int amountMax = transactionProperty.getYearlyAmountMax();
        int occasion = occasionMin + random.nextInt(occasionMax - occasionMin);
        int numberOfDays = days.size();
        for (int i = 0; i < occasion; i++) {
            int amount = amountMin + random.nextInt(amountMax - amountMin);
            LocalDate date;
            do {                                                //ne essen ugyanarra a napre több tranzakció
                date = days.get(random.nextInt(numberOfDays));
            } while (result.get(date) != null);
            SimplePreTransaction simplePreTransaction = new SimplePreTransaction(date, amount, transactionType,null);
            result.put(date, Arrays.asList(simplePreTransaction));
        }
        return result;
    }

    @Override
    public List<Vendor> selectVendors(TransactionType transactionType, Person person, double vendorInHomeCityRate, double atmInHomeCityRate) {
        return null;
    }

    @Override
    public List<ATM> selectATMs(TransactionType transactionType, Person person, double vendorInHomeCityRate, double atmInHomeCityRate, double bankOfAtmToBankOfUserRate) {
        return null;
    }


}
