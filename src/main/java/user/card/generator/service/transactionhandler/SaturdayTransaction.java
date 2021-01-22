package user.card.generator.service.transactionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Vendor;
import user.card.generator.service.VendorService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class SaturdayTransaction extends AbstractTransaction {

    @Autowired
    VendorService vendorService;

    private TransactionType transactionType;
    private Person person;
    private List<Vendor> availableVendors = new ArrayList<>();


    public SaturdayTransaction(TransactionType transactionType, Person person) {
        this.transactionType = transactionType;
        this.person = person;
        this.availableVendors = selectVendors(transactionType, person, 1, 0);
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
            List<LocalDate> saturdays = days.stream()
                    .filter(date -> (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate)))
                    .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY)
                    .collect(Collectors.toList());
            int amount;
            for (LocalDate currentSaturday : saturdays) {
                amount = amountMin + random.nextInt(amountMax - amountMin);
                List<Vendor> availableVendors=getVendorAndAtmSelector().selectAvailableVendors(person);
                int numOfVendors = availableVendors.size();
                Vendor currentVendor = availableVendors.get(random.nextInt(numOfVendors));
                SimplePreTransaction simplePreTransaction = new SimplePreTransaction(currentSaturday, amount, transactionType, currentVendor.getVendorCode());
                if (result.get(currentSaturday) == null) {
                    result.put(currentSaturday, Arrays.asList(simplePreTransaction));
                } else result.get(currentSaturday).add(simplePreTransaction);
            }
        }
        return result;
    }

    @Override
    public List<Vendor> selectVendors(TransactionType transactionType, Person person, double vendorInHomeCityRate, double atmInHomeCityRate) {
        Random random = new Random();
        List<Vendor> selectedVendors = new ArrayList<>();
        List<Vendor> vendorsInCity = vendorService.findAllByCity(person.getCity());
        int sumOfVendorsInCity = vendorsInCity.size();
        int numberOfVendorsToBeSelected = 3 + random.nextInt(1);
        for (int i = 0; i < numberOfVendorsToBeSelected; i++) {
            selectedVendors.add(vendorsInCity.get(random.nextInt(sumOfVendorsInCity)));
        }
        return selectedVendors;
    }

    @Override
    public List<ATM> selectATMs(TransactionType transactionType, Person person, double vendorInHomeCityRate, double atmInHomeCityRate, double bankOfAtmToBankOfUserRate) {
        return null;
    }

    public Vendor selectVendor(Random random) {
        int numberOfAvailableVendors = availableVendors.size();
        return availableVendors.get(random.nextInt(numberOfAvailableVendors));
    }
}
