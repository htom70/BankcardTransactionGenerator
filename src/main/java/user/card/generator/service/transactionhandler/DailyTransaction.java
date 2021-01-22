package user.card.generator.service.transactionhandler;

import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.AbstractVendor;
import user.card.generator.domain.vendor.Vendor;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class DailyTransaction extends AbstractTransaction {

    private TransactionType transactionType;
    private Person person;

    public DailyTransaction(TransactionType transactionType, Person person) {
        this.transactionType = transactionType;
        this.person = person;
    }

    @Override
    public Map<LocalDate, List<SimplePreTransaction>> generate(List<LocalDate> days, TransactionProperty transactionProperty, Year year) {
        Random random = new Random();
        Map<LocalDate, List<SimplePreTransaction>> result = new HashMap<>();
        int occasionMin = transactionProperty.getDailyOccasionMin();
        int occasionMax = transactionProperty.getDailyOccasionMax();
        int amountMin = transactionProperty.getDailyAmountMin();
        int amountMax = transactionProperty.getDailyAmountMax();
        for (int i = 1; i < 12; i++) {
            LocalDate startDate = LocalDate.of(year.getValue(), i, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.getMonth().length(startDate.isLeapYear()));
            List<LocalDate> daysInCurrentmonth = days.stream()
                    .filter(date -> (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate)))
                    .collect(Collectors.toList());
            int numberOfDayInCurrentMonth = daysInCurrentmonth.size();
            int dayIndex = 0;
            LocalDate date = null;
            int amount = 0;
            while (dayIndex < numberOfDayInCurrentMonth) {
                date = daysInCurrentmonth.get(dayIndex);
                amount = amountMin + random.nextInt(amountMax - amountMin);
                dayIndex += occasionMin + random.nextInt(occasionMax - occasionMin);
            }
            SimplePreTransaction simplePreTransaction = new SimplePreTransaction(date, amount, transactionType,"a");
            if (result.get(date) == null) {
                result.put(date, Arrays.asList(simplePreTransaction));
            } else result.get(date).add(simplePreTransaction);
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

