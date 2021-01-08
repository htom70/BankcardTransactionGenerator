package user.card.generator.domain.transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionPropertyInitializer {

//    @Value("${retiredUseCard.minIncome}")
//    private int retiredUseCardMinIncome;
//    @Value("${retiredUseCard.averageIncome}")
//    private int retiredUseCardAverageIncome;
//    @Value("${retiredUseCard.maxIncome}")
//    private int retiredUseCardMaxIncome;
//    @Value("${retiredUseCard.limitRateToIncome.Min}")
//    private double retiredUseCardLimitRateToIncomeMin;
//    @Value("${retiredUseCard.limitRateToIncome.Max}")
//    private double retiredUseCardLimitRateToIncomeMax;
//    @Value("${retiredUseCard.firstTypeForeignCountryJourney.entityRate}")
//    private double retiredUseCardFirstTypeForeignCountryJourneyEntityRate;
//    @Value("${retiredUseCard.firstTypeForeignCountryJourney.weekNumber.Min}")
//    private int retiredUseCardFirstTypeForeignCountryJourneyWeekNumberMin;
//    @Value("${retiredUseCard.firstTypeForeignCountryJourney.weekNumber.Max}")
//    private int retiredUseCardfirstTypeForeignCountryJourneyWeekNumberMax;
//    @Value("${retiredUseCard.firstTypeForeignCountryJourney.countryList}")
//    private String retiredUseCardfirstTypeForeignCountryJourneyCountryList;
//    @Value("${retiredUseCard.firstTypeForeignCountryJourney.limitRateToNormalMonth.Max}")
//    private double retiredUseCardfirstTypeForeignCountryJourneyLimitRateToNormalMonthMax;
//    @Value("${retiredUseCard.firstTypeForeignCountryJourney.foreignToDomesticTransactionRate.Max}")
//    private double retiredUseCardfirstTypeForeignCountryJourneyForeignToDomesticTransactionRateMax;
//
//    @Value("${retiredUseCard.Pos.daily.occasion.Min}")
//    private int retiredUseCardPosDailyOccasionMin;
//    @Value("${retiredUseCard.Pos.daily.occasion.Max}")
//    private int retiredUseCardPosDailyOccasionMax;
//    @Value("${retiredUseCard.Pos.daily.amount.Min}")
//    private int retiredUseCardPosDailyAmountMin;
//    @Value("${retiredUseCard.Pos.daily.amount.Max}")
//    private int retiredUseCardPosDailyAmountMax;
//    @Value("${retiredUseCard.Pos.daily.storeNumber.Max}")
//    private int retiredUseCardPosDailyStoreNumberMax;
//    @Value("${retiredUseCard.Pos.saturday.storeNumber.Min}")
//    private int retiredUseCardPosSaturdayStoreNumberMin;
//    @Value("${retiredUseCard.Pos.saturday.storeNumber.Max}")
//    private int retiredUseCardPosSaturdayStoreNumberMax;
//    @Value("${retiredUseCard.Pos.saturday.amount.Min}")
//    private int retiredUseCardPosSaturdayAmountMin;
//    @Value("${retiredUseCard.Pos.saturday.amount.Max}")
//    private int retiredUseCardPosSaturdayAmountMax;
//    @Value("${retiredUseCard.Pos.christmasPeriod.beginDay}")
//    private int retiredUseCardPosChristmasPeriodBeginDay;
//    @Value("${retiredUseCard.Pos.christmasPeriod.daily.occasion.Min}")
//    private int retiredUseCardPosChristmasPeriodDailyOccasionMin;
//    @Value("${retiredUseCard.Pos.christmasPeriod.daily.occasion.Max}")
//    private int retiredUseCardPosChristmasPeriodDailyOccasionMax;
//    @Value("${retiredUseCard.Pos.christmasPeriod.daily.amount.Min}")
//    private int retiredUseCardPosChristmasPeriodDailyAmountMin;
//    @Value("${retiredUseCard.Pos.christmasPeriod.daily.amount.Max}")
//    private int retiredUseCardPosChristmasPeriodDailyAmountMax;
//    @Value("${retiredUseCard.Pos.christmasPeriod.limitRate}")
//    private double retiredUseCardPosChristmasPeriodLimitRate;
//    @Value("${retiredUseCard.ATM.monthly.occasion.Min}")
//    private int retiredUseCardAtmMonthlyOccasionNumberMin;
//    @Value("${retiredUseCard.ATM.monthly.occasion.Max}")
//    private int retiredUseCardAtmMonthlyOccasionNumberMax;
//    @Value("${retiredUseCard.ATM.monthly.amount.Min}")
//    private int retiredUseCardAtmMonthlyAmountMin;
//    @Value("${retiredUseCard.ATM.monthly.amount.Max}")
//    private int retiredUseCardAtmMonthlyAmountMax;

//    @Value("${retiredUseCard.PosPurchase.firstTypeForeignCountryJourney.entityRate}")
//    private double retiredUseCardPosPurchaseFirstTypeForeignCountryJourneyEntityRate;
//    @Value("${retiredUseCard.PosPurchase.firstTypeForeignCountryJourney.weekNumber.Min}")
//    private int retiredUseCardPosPurchaseFirstTypeForeignCountryJourneyWeekNumberMin;
//    @Value("${retiredUseCard.PosPurchase.firstTypeForeignCountryJourney.weekNumber.Max}")
//    private int retiredUseCardPosPurchaseFirstTypeForeignCountryJourneyWeekNumberMax;
//    @Value("${retiredUseCard.PosPurchase.firstTypeForeignCountryJourney.countryList}")
//    private String retiredUseCardPosPurchaseFirstTypeForeignCountryJourneyCountryList;
//    @Value("${retiredUseCard.PosPurchase.firstTypeForeignCountryJourney.limitRate.Max}")
//    private double retiredUseCardPosPurchaseFirstTypeForeignCountryJourneyLimitRateMax;
//    @Value("${retiredUseCard.ATMtransaction.monthly.occasion.Min}")
//    private int retiredUseCardATMtransactionMonthlyOccasionNumberMin;
//    @Value("${retiredUseCard.ATMtransaction.monthly.occasion.Max}")
//    private int retiredUseCardATMtransactionMonthlyOccasionNumberMax;
//    @Value("${retiredUseCard.ATMtransaction.monthly.amount.Min}")
//    private int retiredUseCardATMtransactionMonthlyAmountMin;
//    @Value("${retiredUseCard.ATMtransaction.monthly.amount.Max}")
//    private int retiredUseCardATMtransactionMonthlyAmountMax;

    //    @Value("${retiredDontUseCard.minIncome}")
//    private int retiredDontUseCardMinIncome;
//    @Value("${retiredDontUseCard.averageIncome}")
//    private int retiredDontUseCardAverageIncome;
//    @Value("${retiredDontUseCard.maxIncome}")
//    private int retiredDontUseCardMaxIncome;
//
//    @Value("${ordinaryUserUseCardAndInternet.averageIncome}")
//    private int ordinaryUserUseCardAndInternetAverageIncome;
//    @Value("${ordinaryUserUseCardAndInternet.deviationIncome}")
//    private int ordinaryUserUseCardAndInternetDeviationIncome;
//    @Value("${ordinaryUserUseCardAndInternet.netPurchase.monthlyOccasionNumberMin}")
//    private int ordinaryUserUseCardAndInternetMonthlyOccasionNumberMin;
//    @Value("${ordinaryUserUseCardAndInternet.netPurchase.monthlyOccasionNumberMax}")
//    private int ordinaryUserUseCardAndInternetMonthlyOccasionNumberMax;
//    @Value("${ordinaryUserUseCardAndInternet.netPurchase.monthlyAmountMin}")
//    private int ordinaryUserUseCardAndInternetMonthlyAmountMin;
//    @Value("${ordinaryUserUseCardAndInternet.netPurchase.monthlyAmountMax}")
//    private int ordinaryUserUseCardAndInternetMonthlyAmountMax;
//    @Value("${ordinaryUserUseCardAndInternet.netPurchase.monthlyDecemberOccasionNumberMin}")
//    private int ordinaryUserUseCardAndInternetNetPurchaseMonthlyDecemberOccasionNumberMin;
//    @Value("${ordinaryUserUseCardAndInternet.netPurchase.monthlyDecemberOccasionNumberMax}")
//    private int ordinaryUserUseCardAndInternetNetPurchaseMonthlyDecemberOccasionNumberMax;
//    @Value("${ordinaryUserUseCardAndInternet.netPurchase.monthlyDecemberAmountMin}")
//    private int ordinaryUserUseCardAndInternetNetPurchaseMonthlyDecemberAmountMin;
//    @Value("${ordinaryUserUseCardAndInternet.netPurchase.monthlyDecemberAmountMax}")
//    private int ordinaryUserUseCardAndInternetNetPurchaseMonthlyDecemberAmountMax;
//    @Value("${ordinaryUserUseCardAndInternet.netPurchase.monthlySumMax}")
//    private int ordinaryUserUseCardAndInternetNetPurchaseMonthlySumMax;
//
//    @Value("${ordinaryUserUseCardDontUseInternet.averageIncome}")
//    private int ordinaryUserUseCardDontUseInternetAverageIncome;
//    @Value("${ordinaryUserUseCardDontUseInternet.deviationIncome}")
//    private int ordinaryUserUseCardDontUseInternetDeviationIncome;
//    @Value("${ordinaryUserUseCardDontUseInternet.netPurchase.monthlyDecemberOccasionNumberMin}")
//    private int ordinaryUserUseCardDontUseInternetNetPurchaseMonthlyDecemberOccasionNumberMin;
//    @Value("${ordinaryUserUseCardDontUseInternet.netPurchase.monthlyDecemberOccasionNumberMax}")
//    private int ordinaryUserUseCardDontUseInternetNetPurchaseMonthlyDecemberOccasionNumberMax;
//    @Value("${ordinaryUserUseCardDontUseInternet.netPurchase.monthlyDecemberAmountMin}")
//    private int ordinaryUserUseCardDontUseInternetNetPurchaseMonthlyDecemberAmountMin;
//    @Value("${ordinaryUserUseCardDontUseInternet.netPurchase.monthlyDecemberAmountMax}")
//    private int ordinaryUserUseCardDontUseInternetNetPurchaseMonthlyDecemberAmountMax;
//    @Value("${ordinaryUserUseCardDontUseInternet.netPurchase.monthlySumMax}")
//    private int ordinaryUserUseCardDontUseInternetNetPurchaseMonthlySumMax;
//
//    @Value("${ordinaryUserDontUseCard.averageIncome}")
//    private int ordinaryUserDontUseCardAverageIncome;
//    @Value("${ordinaryUserDontUseCard.deviationIncome}")
//    private int ordinaryUserDontUseCardDeviationIncome;
//
//    @Value("${vipUser.minIncome}")
//    private int vipUserMinIncome;
//    @Value("${vipUser.averageIncome}")
//    private int vipUserAverageIncome;
//    @Value("${vipUser.netPurchase.dailyOccasionNumberMin}")
//    private int vipUserNetPurchaseDailyOccasionNumberMin;
//    @Value("${vipUser.netPurchase.dailyOccasionNumberMax}")
//    private int vipUserNetPurchaseDailyOccasionNumberMax;
//    @Value("${vipUser.netPurchase.dailyAmountMin}")
//    private int vipUserNetPurchaseDailyAmountMin;
//    @Value("${vipUser.netPurchase.dailyAmountMax}")
//    private int vipUserNetPurchaseDailyAmountMax;
//    @Value("${vipUser.netPurchase.monthlyDecemberOccasionNumberMin}")
//    private int vipUserNetPurchaseMonthlyDecemberOccasionNumberMin;
//    @Value("${vipUser.netPurchase.monthlyDecemberOccasionNumberMax}")
//    private int vipUserNetPurchaseMonthlyDecemberOccasionNumberMax;
//    @Value("${vipUser.netPurchase.monthlyDecemberAmountMin}")
//    private int vipUserNetPurchaseMonthlyDecemberAmountMin;
//    @Value("${vipUser.netPurchase.monthlyDecemberAmountMax}")
//    private int vipUserNetPurchaseMonthlyDecemberAmountMax;
//    @Value("${vipUser.netPurchase.monthlySumMax}")
//    private int vipUserNetPurchaseMonthlySumMax;


    public TransactionProperties getRetiredUseCardAndInternetProperties() {
        return null;
    }

    public TransactionProperties getRetiredDontUseCardAndInternetProperties() {
        return null;
    }

    public TransactionProperties getOrdinaryUserUseCardAndInternetProperties() {
        return null;
    }

    public TransactionProperties getOrdinaryUserUseCardDontUseInternetProperties() {
        return null;
    }

    public TransactionProperties getordinaryUserDontUseCardProperties() {
        return null;
    }

    public TransactionProperties getVipUserProperties() {
        return null;
    }
}
