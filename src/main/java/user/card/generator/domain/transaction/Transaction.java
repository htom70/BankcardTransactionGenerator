package user.card.generator.domain.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import user.card.generator.domain.ResponseCode;
import user.card.generator.domain.city.City;
import user.card.generator.domain.field.FieldGenerator;
import user.card.generator.domain.name.WomanName;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
@Getter
@Setter
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String cardNumber;
    private TransactionType transactionType;
    private Timestamp timestamp;
    private int amount;
    private String currencyName;
    private ResponseCode responseCode;
    private String countryName;
    private String vendorCode;

    private Double field1;
    private Double field2;
    private Double field3;
    private Double field4;
    private Double field5;
    private Double field6;
    private Double field7;
    private Double field8;
    private Double field9;
    private Double field10;
    private Double field11;
    private Double field12;
    private Double field13;
    private Double field14;
    private Double field15;
    private Double field16;
    private Double field17;
    private Double field18;
    private Double field19;
    private Double field20;
    private Double field21;
    private Double field22;
    private Double field23;
    private Double field24;
    private Double field25;
    private String field26;
    private String field27;
    private String field28;
    private String field29;
    private String field30;
    private String field31;
    private String field32;
    private String field33;
    private String field34;
    private String field35;
    private LocalDate field36;
    private LocalDate field37;
    private LocalDate field38;
    private LocalDate field39;
    private LocalDate field40;
    private boolean fraud;


    public Transaction(String cardNumber, TransactionType transactionType, Timestamp timestamp, int amount, String currencyName, ResponseCode responseCode, String countryName,
                       String vendorCode) {
        this.transactionType = transactionType;
        this.timestamp = timestamp;
        this.amount = amount;
        this.currencyName = currencyName;
        this.responseCode = responseCode;
        this.countryName = countryName;
        this.vendorCode = vendorCode;
        this.cardNumber = cardNumber;
    }

    public void setAllFields(List<City> cities) {
        int rate;
        FieldGenerator fieldGenerator = new FieldGenerator();
        Random random = new Random();
        setField1(fieldGenerator.generateFromZeroToOne(random));
        setField2(fieldGenerator.generateFromZeroToOne(random));
        rate = random.nextInt(100);
        if (rate < 80) {
            setField3(fieldGenerator.generateFromZeroToOne(random));
        }
//        rate = random.nextInt(100);
        if (rate < 60) {
            setField4(fieldGenerator.generateFromZeroToOne(random));
        }
//        rate = random.nextInt(100);
        if (rate < 40) {
            setField5(fieldGenerator.generateFromZeroToOne(random));
        }
//        rate = random.nextInt(100);
        if (rate < 20) {
            setField6(fieldGenerator.generateFromZeroToOne(random));
        }
        setField7(fieldGenerator.generateFromMinusOneToOne(random));
        setField8(fieldGenerator.generateFromMinusOneToOne(random));
//        rate = random.nextInt(100);
        if (rate < 80) {
            setField9(fieldGenerator.generateFromMinusOneToOne(random));
        }
//        rate = random.nextInt(100);
        if (rate < 60) {
            setField10(fieldGenerator.generateFromMinusOneToOne(random));
        }
//        rate = random.nextInt(100);
        if (rate < 40) {
            setField11(fieldGenerator.generateFromMinusOneToOne(random));
        }
//        rate = random.nextInt(100);
        if (rate < 20) {
            setField12(fieldGenerator.generateFromMinusOneToOne(random));
        }
        setField13(fieldGenerator.generateFromMinusThousandToThousand(random));
//        rate = random.nextInt(100);
        if (rate < 80) {
            setField14(fieldGenerator.generateFromMinusThousandToThousand(random));
        }
//        rate = random.nextInt(100);
        if (rate < 60) {
            setField15(fieldGenerator.generateFromMinusThousandToThousand(random));
        }
//        rate = random.nextInt(100);
        if (rate < 20) {
            setField16(fieldGenerator.generateFromMinusThousandToThousand(random));
        }
//        rate = random.nextInt(100);
        if (rate < 60) {
            setField17(fieldGenerator.generateFromZeroToFiveHundredThousand(random));
        }
//        rate = random.nextInt(100);
        if (rate < 40) {
            setField18(fieldGenerator.generateFromZeroToFiveHundredThousand(random));
        }
//        rate = random.nextInt(100);
        if (rate < 20) {
            setField19(fieldGenerator.generateFromZeroToFiveHundredThousand(random));
        }
//        rate = random.nextInt(100);
        if (rate < 20) {
            setField20(fieldGenerator.generateFromZeroToFiveHundredThousand(random));
        }
        setField21(fieldGenerator.generateFromMinusHundredToHundred(random));
//        rate = random.nextInt(100);
        if (rate < 70) {
            setField22(fieldGenerator.generateFromMinusHundredToHundred(random));
        }
//        rate = random.nextInt(100);
        if (rate < 50) {
            setField23(fieldGenerator.generateFromMinusHundredToHundred(random));
        }
//        rate = random.nextInt(100);
        if (rate < 30) {
            setField24(fieldGenerator.generateFromMinusHundredToHundred(random));
        }
//        rate = random.nextInt(100);
        if (rate < 10) {
            setField25(fieldGenerator.generateFromMinusHundredToHundred(random));
        }
        setField26(fieldGenerator.generateMixedLastName(random));
//        rate = random.nextInt(100);
        if (rate < 60) {
            setField27(fieldGenerator.generateMixedLastName(random));
        }
        setField28(fieldGenerator.generateWomanLastName(random));
//        rate = random.nextInt(100);
        if (rate < 50) {
            setField29(fieldGenerator.generateWomanLastName(random));
        }
//        rate = random.nextInt(100);
        if (rate < 80) {
            if (checkIfField26containsAWomanName()) {
                setField30(fieldGenerator.generateWomanLastName(random));
            }
        }
        setField31(fieldGenerator.generateCityName(random,cities));
//        rate = random.nextInt(100);
        if (rate < 80) {
            setField32(fieldGenerator.generateCityName(random,cities));
        }
        int placeIndex = random.nextInt(cities.size());
        City currentCity = cities.get(placeIndex);
        setField33(currentCity.getName());
        setField34(currentCity.getName());
        setField35(fieldGenerator.generateCountyCode(currentCity));
        setField36(fieldGenerator.generateDate(random, 1940, 2020));
        setField37(fieldGenerator.generateDate(random, 1990,2020));
        setField38(fieldGenerator.generateDate(random, 2020, 2020));
        rate = random.nextInt(100);
        if (rate < 70) {
            setField39(fieldGenerator.generateDate(random, 2020,2020));
        }
        rate = random.nextInt(100);
        if (rate < 50) {
            setField40(fieldGenerator.generateDate(random, 2020,2020));
        }
    }

    public void setFraud(boolean fraud) {
        this.fraud = fraud;
    }

    private boolean checkIfField26containsAWomanName() {
        WomanName[] names = WomanName.values();
        List<String> nameCollection = new ArrayList<>();
        for (int i = 0; i <names.length ; i++) {
            String name = names[i].toString();
            nameCollection.add(name);
        }
        return nameCollection.contains(field26);
    }
}
