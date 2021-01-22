package user.card.generator.service.transactionhandler;

import lombok.Data;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.SimplePreTransaction;
import user.card.generator.domain.transaction.TransactionType;
import user.card.generator.domain.vendor.ATM;
import user.card.generator.domain.vendor.Vendor;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractTransaction {

    private VendorAndAtmSelector vendorAndAtmSelector;

    public abstract Map<LocalDate,List<SimplePreTransaction>> generate(List<LocalDate> days, TransactionProperty transactionProperty, Year year);

    public abstract List<Vendor> selectVendors(TransactionType transactionType, Person person, double vendorInHomeCityRate, double atmInHomeCityRate);
    public abstract List<ATM> selectATMs(TransactionType transactionType, Person person, double vendorInHomeCityRate, double atmInHomeCityRate, double bankOfAtmToBankOfUserRate);
}
