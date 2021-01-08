package user.card.generator.domain.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import user.card.generator.domain.person.PersonForeignCountryJourneyProperty;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class TransactionProperties  {

    private double foreignToDomesticTransactionRate;
    private double limitRateToNormalMonth;
    private int minIncome;
    private int averageIncome;
    private int maxIncome;
    private double limitRateToIncomeMin;
    private double limitRateToIncomeMax;
    private int posDailyOccasionMin;
    private int posDailyOccasionMax;
    private int posDailyNumberMin;
    private int posDailyNumberMax;
    private int posDailyAmountMin;
    private int posDailyAmountMax;
    private int posDailyStoreNumberMax;
    private int posYearlyNumberMin;
    private int posYearlyNumberMax;
    private int posYearlyAmountMin;
    private int posYearlyAmountMax;
    private int posSaturdayAmountMin;
    private int posSaturdayAmountMax;
    private int posSaturdayStoreNumberMin;
    private int posSaturdayStoreNumberMax;
    private int posChristmasPeriodBeginDay;
    private int posChristmasPeriodDailyOccasionMin;
    private int posChristmasPeriodDailyOccasionMax;
    private int posChristmasPeriodDailyAmountMin;
    private int posChristmasPeriodDailyAmountMax;
    private int atmMonthlyOccasionMin;
    private int atmMonthlyOccasionMax;
    private int atmMonthlyAmountMin;
    private int atmMonthlyAmountMax;
    private int atmMonthlyTransactionDateMin;
    private int atmMonthlyTransactionDateMax;
    private int atmMonthlyDecemberOccasionMin;
    private int atmMonthlyDecemberOccasionMax;
    private int atmMonthlyDecemberAmountMin;
    private int atmMonthlyDecemberAmountMax;
    private int netMonthlyOccasionMin;
    private int netMonthlyOccasionMax;
    private int netMonthlyAmountMin;
    private int netMonthlyAmountMax;
    private int netAtmMonthlyDecemberOccasionMin;
    private int netAtmMonthlyDecemberOccasionMax;
    private int netAtmMonthlyDecemberAmountMin;
    private int netAtmMonthlyDecemberAmountMax;

    private List<SimpleForeignCountryJourneyPropertyFromConfig> simpleForeignCountryJourneyPropertyFromConfigs = new ArrayList<>();


}
