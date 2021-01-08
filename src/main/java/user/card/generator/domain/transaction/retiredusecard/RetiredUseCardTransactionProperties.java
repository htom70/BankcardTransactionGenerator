package user.card.generator.domain.transaction.retiredusecard;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class RetiredUseCardTransactionProperties {

    @Value("${retiredUseCard.minIncome}")
    private int retiredUseCardMinIncome;
    @Value("${retiredUseCard.avarageIncome}")
    private int retiredUseCardAvarageIncome;
    @Value("${retiredUseCard.maxIncome}")
    private int retiredUseCardMaxIncome;
    @Value("${retiredUseCard.limitRateToIncome.Min}")
    private int retiredUseCardLimitRateToIncomeMin;
    @Value("${retiredUseCard.limitRateToIncome.Max}")
    private int retiredUseCardLimitRateToIncomeMax;
    @Value("${retiredUseCard.firstTypeForeignCountryJourney.entityRate}")
    private double retiredUseCardFirstTypeForeignCountryJourneyEntityRate;
    @Value("${retiredUseCard.firstTypeForeignCountryJourney.dayNumber.Min}")
    private int retiredUseCardFirstTypeForeignCountryJourneyDayNumberMin;
    @Value("${retiredUseCard.firstTypeForeignCountryJourney.dayNumber.Max}")
    private int retiredUseCardFirstTypeForeignCountryJourneyDayNumberMax;
    @Value("${retiredUseCard.firstTypeForeignCountryJourney.countryList}")
    private int retiredUseCardFirstTypeForeignCountryJourneyCountryList;
    //POS
    @Value("${retiredUseCard.Pos.daily.occasion.Min}")
    private int retiredUseCardPosDailyOccasionMin;
    @Value("${retiredUseCard.Pos.daily.occasion.Max}")
    private int retiredUseCardPosDailyOccasionMax;
    @Value("${retiredUseCard.Pos.daily.amount.Min}")
    private int retiredUseCardPosDailyAmountMin;
    @Value("${retiredUseCard.Pos.daily.amount.Max}")
    private int retiredUseCardPosDailyAmountMax;
    @Value("${retiredUseCard.Pos.daily.storeNumber.Max}")
    private int retiredUseCardPosDailyStoreNumberMax;
    //Saturday
    @Value("${retiredUseCard.Pos.saturday}")
    private int retiredUseCardPosSaturday;
    @Value("${retiredUseCard.Pos.saturday.amount.Min}")
    private int retiredUseCardPosSaturdayAmountMin;
    @Value("${retiredUseCard.Pos.saturday.amount.Max}")
    private int retiredUseCardPosSaturdayAmountMax;
    @Value("${retiredUseCard.Pos.saturday.storeNumber.Min}")
    private int retiredUseCardPosSaturdayStoreNumberMin;
    @Value("${retiredUseCard.Pos.saturday.storeNumber.Max}")
    private int retiredUseCardPosSaturdayStoreNumberMax;
    //Christmas
    @Value("${retiredUseCard.Pos.christmasPeriod.beginDay}")
    private int retiredUseCardPosChristmasPeriodBeginDay;
    @Value("${retiredUseCard.Pos.christmasPeriod.occasion.Min}")
    private int retiredUseCardPosChristmasPeriodOccasionMin;
    @Value("${retiredUseCard.Pos.christmasPeriod.occasion.Max}")
    private int retiredUseCardPosChristmasPeriodOccasionMax;
    @Value("${retiredUseCard.Pos.christmasPeriod.amount.Min}")
    private int retiredUseCardPosChristmasPeriodAmountMin;
    @Value("${retiredUseCard.Pos.christmasPeriod.amount.Max}")
    private int retiredUseCardPosChristmasPeriodAmountMax;
    //ATM
    @Value("${retiredUseCard.ATM.monthly.occasion.Min}")
    private int retiredUseCardATMmonthlyOccasionMin;
    @Value("${retiredUseCard.ATM.monthly.occasion.Max}")
    private int retiredUseCardATMmonthlyOccasionMax;
    @Value("${retiredUseCard.ATM.monthly.amount.Min}")
    private int retiredUseCardATMmonthlyAmountMin;
    @Value("${retiredUseCard.ATM.monthly.amount.Max}")
    private int retiredUseCardATMmonthlyAmountMax;
}
