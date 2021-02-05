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
import java.util.Optional;
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
        rate = random.nextInt(100);
        if (rate < 60) {
            setField4(fieldGenerator.generateFromZeroToOne(random));
        }
        rate = random.nextInt(100);
        if (rate < 40) {
            setField5(fieldGenerator.generateFromZeroToOne(random));
        }
        rate = random.nextInt(100);
        if (rate < 20) {
            setField6(fieldGenerator.generateFromZeroToOne(random));
        }
        setField7(fieldGenerator.generateFromMinusOneToOne(random));
        setField8(fieldGenerator.generateFromMinusOneToOne(random));
        rate = random.nextInt(100);
        if (rate < 80) {
            setField9(fieldGenerator.generateFromMinusOneToOne(random));
        }
        rate = random.nextInt(100);
        if (rate < 60) {
            setField10(fieldGenerator.generateFromMinusOneToOne(random));
        }
        rate = random.nextInt(100);
        if (rate < 40) {
            setField11(fieldGenerator.generateFromMinusOneToOne(random));
        }
        rate = random.nextInt(100);
        if (rate < 20) {
            setField12(fieldGenerator.generateFromMinusOneToOne(random));
        }
        setField13(fieldGenerator.generateFromMinusThousandToThousand(random));
        rate = random.nextInt(100);
        if (rate < 80) {
            setField14(fieldGenerator.generateFromMinusThousandToThousand(random));
        }
        rate = random.nextInt(100);
        if (rate < 60) {
            setField15(fieldGenerator.generateFromMinusThousandToThousand(random));
        }
        rate = random.nextInt(100);
        if (rate < 20) {
            setField16(fieldGenerator.generateFromMinusThousandToThousand(random));
        }
        rate = random.nextInt(100);
        if (rate < 60) {
            setField17(fieldGenerator.generateFromZeroToFiveHundredThousand(random));
        }
        rate = random.nextInt(100);
        if (rate < 40) {
            setField18(fieldGenerator.generateFromZeroToFiveHundredThousand(random));
        }
        rate = random.nextInt(100);
        if (rate < 20) {
            setField19(fieldGenerator.generateFromZeroToFiveHundredThousand(random));
        }
        rate = random.nextInt(100);
        if (rate < 20) {
            setField20(fieldGenerator.generateFromZeroToFiveHundredThousand(random));
        }
        setField21(fieldGenerator.generateFromMinusHundredToHundred(random));
        rate = random.nextInt(100);
        if (rate < 70) {
            setField22(fieldGenerator.generateFromMinusHundredToHundred(random));
        }
        rate = random.nextInt(100);
        if (rate < 50) {
            setField23(fieldGenerator.generateFromMinusHundredToHundred(random));
        }
        rate = random.nextInt(100);
        if (rate < 30) {
            setField24(fieldGenerator.generateFromMinusHundredToHundred(random));
        }
        rate = random.nextInt(100);
        if (rate < 10) {
            setField25(fieldGenerator.generateFromMinusHundredToHundred(random));
        }
        setField26(fieldGenerator.generateMixedLastName(random));
        rate = random.nextInt(100);
        if (rate < 60) {
            setField27(fieldGenerator.generateMixedLastName(random));
        }
        setField28(fieldGenerator.generateWomanLastName(random));
        rate = random.nextInt(100);
        if (rate < 50) {
            setField29(fieldGenerator.generateWomanLastName(random));
        }
        rate = random.nextInt(100);
        if (rate < 80) {
            if (checkIfField26containsAWomanName()) {
                setField30(fieldGenerator.generateWomanLastName(random));
            }
        }
        setField31(fieldGenerator.generateCityName(random, cities));
        rate = random.nextInt(100);
        if (rate < 80) {
            setField32(fieldGenerator.generateCityName(random, cities));
        }
        int placeIndex = random.nextInt(cities.size());
        City currentCity = cities.get(placeIndex);
        setField33(currentCity.getName());
        setField34(currentCity.getName());
        setField35(fieldGenerator.generateCountyCode(currentCity));
        setField36(fieldGenerator.generateDate(random, 1940, 2020));
        setField37(fieldGenerator.generateDate(random, 1990, 2020));
        setField38(fieldGenerator.generateDate(random, 2020, 2020));
        rate = random.nextInt(100);
        if (rate < 70) {
            setField39(fieldGenerator.generateDate(random, 2020, 2020));
        }
        rate = random.nextInt(100);
        if (rate < 50) {
            setField40(fieldGenerator.generateDate(random, 2020, 2020));
        }
    }

    public void setFraud(boolean fraud) {
        this.fraud = fraud;
    }


    private boolean checkIfField26containsAWomanName() {
        WomanName[] names = WomanName.values();
        List<String> nameCollection = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            String name = names[i].toString();
            nameCollection.add(name);
        }
        return nameCollection.contains(field26);
    }

    public List<Optional<String>> getAllExtraFields() {
        List<Optional<String>> result = new ArrayList<>();
        if (this.getField1() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField1().toString());
            result.add(fieldWithValue);
        }
        if (this.getField2() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField2().toString());
            result.add(fieldWithValue);
        }
        if (this.getField3() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField3().toString());
            result.add(fieldWithValue);
        }
        if (this.getField4() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField4().toString());
            result.add(fieldWithValue);
        }
        if (this.getField5() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField5().toString());
            result.add(fieldWithValue);
        }
        if (this.getField6() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField6().toString());
            result.add(fieldWithValue);
        }
        if (this.getField7() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField7().toString());
            result.add(fieldWithValue);
        }
        if (this.getField8() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField8().toString());
            result.add(fieldWithValue);
        }
        if (this.getField9() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField9().toString());
            result.add(fieldWithValue);
        }
        if (this.getField10() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField10().toString());
            result.add(fieldWithValue);
        }
        if (this.getField11() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField11().toString());
            result.add(fieldWithValue);
        }
        if (this.getField12() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField12().toString());
            result.add(fieldWithValue);
        }
        if (this.getField13() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField13().toString());
            result.add(fieldWithValue);
        }
        if (this.getField14() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField14().toString());
            result.add(fieldWithValue);
        }
        if (this.getField15() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField15().toString());
            result.add(fieldWithValue);
        }
        if (this.getField16() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField16().toString());
            result.add(fieldWithValue);
        }
        if (this.getField17() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField17().toString());
            result.add(fieldWithValue);
        }
        if (this.getField18() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField18().toString());
            result.add(fieldWithValue);
        }
        if (this.getField19() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField19().toString());
            result.add(fieldWithValue);
        }
        if (this.getField20() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField20().toString());
            result.add(fieldWithValue);
        }
        if (this.getField21() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField21().toString());
            result.add(fieldWithValue);
        }
        if (this.getField22() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField22().toString());
            result.add(fieldWithValue);
        }
        if (this.getField23() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField23().toString());
            result.add(fieldWithValue);
        }
        if (this.getField24() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField24().toString());
            result.add(fieldWithValue);
        }
        if (this.getField25() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField25().toString());
            result.add(fieldWithValue);
        }
        if (this.getField26() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField26());
            result.add(fieldWithValue);
        }
        if (this.getField27() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField27());
            result.add(fieldWithValue);
        }
        if (this.getField28() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField28());
            result.add(fieldWithValue);
        }
        if (this.getField29() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField29());
            result.add(fieldWithValue);
        }
        if (this.getField30() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField30());
            result.add(fieldWithValue);
        }
        if (this.getField31() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField31());
            result.add(fieldWithValue);
        }
        if (this.getField32() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField32());
            result.add(fieldWithValue);
        }
        if (this.getField33() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField33());
            result.add(fieldWithValue);
        }
        if (this.getField34() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField34());
            result.add(fieldWithValue);
        }
        if (this.getField35() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField35());
            result.add(fieldWithValue);
        }
        if (this.getField36() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField36().toString());
            result.add(fieldWithValue);
        }
        if (this.getField37() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField37().toString());
            result.add(fieldWithValue);
        }
        if (this.getField38() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField38().toString());
            result.add(fieldWithValue);
        }
        if (this.getField39() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField39().toString());
            result.add(fieldWithValue);
        }
        if (this.getField40() == null) {
            Optional<String> empty = Optional.empty();
            result.add(empty);
        } else {
            Optional<String> fieldWithValue = Optional.of(this.getField40().toString());
            result.add(fieldWithValue);
        }
        result.add(this.getFruaud());
        return result;
    }

    private Optional<String> getFruaud() {
        int value = fraud ? 1 : 0;
        Optional<String> fieldWithValue = Optional.of(String.valueOf(value));
        return fieldWithValue;
    }
}
