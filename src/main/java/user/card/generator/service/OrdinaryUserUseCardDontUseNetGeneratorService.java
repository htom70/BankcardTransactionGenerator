package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import user.card.generator.repository.PersonRepository;
import user.card.generator.repository.TransactionRepository;
import user.card.generator.repository.VendorRepository;

public class OrdinaryUserUseCardDontUseNetGeneratorService {

    @Value("${ordinaryUserUseCardDontUseInternet.limitRateToIncome.Min}")
    private double ordinaryUserUseCardDontUseInternetLimitRateToIncomeMin;
    @Value("${ordinaryUserUseCardDontUseInternet.limitRateToIncome.Max}")
    private double ordinaryUserUseCardDontUseInternetLimitRateToIncomeMax;
    @Value("${ordinaryUserUseCardDontUseInternet.limitRateToIncomeInChristmasPeriod}")
    private double ordinaryUserUseCardDontUseInternetLimitRateToIncomeInChristmasPeriod;
    //POS daily
    @Value("${ordinaryUserUseCardDontUseInternet.Pos.daily.Number.Min}")
    private int ordinaryUserUseCardDontUseInternetPosDailyNumberMin;
    @Value("${ordinaryUserUseCardDontUseInternet.Pos.daily.Number.Max}")
    private int ordinaryUserUseCardDontUseInternetPosDailyNumberMax;
    @Value("${ordinaryUserUseCardDontUseInternet.Pos.daily.amount.Min}")
    private int ordinaryUserUseCardDontUseInternetPosDailyAmountMin;
    @Value("${ordinaryUserUseCardDontUseInternet.Pos.daily.amount.Max}")
    private int ordinaryUserUseCardDontUseInternetPosDailyAmountMax;
    //POS yearly
    @Value("${ordinaryUserDontUseCard.Pos.yearly.occasion.Max}")
    private int ordinaryUserDontUseCardPosYearlyOccasionMax;
    @Value("${ordinaryUserDontUseCard.Pos.yearly.amount.Min}")
    private int ordinaryUserDontUseCardPosYearlyAmountMin;
    @Value("${ordinaryUserDontUseCard.Pos.yearly.amount.Max}")
    private int ordinaryUserDontUseCardPosYearlyAmountMax;
    //POS Christmas period
    @Value("${ordinaryUserDontUseCard.Pos.christmasPeriod.beginDay}")
    private int ordinaryUserDontUseCardPosChristmasPeriodBeginDay;
    @Value("${ordinaryUserDontUseCard.Pos.christmasPeriod.occasion.Min}")
    private int ordinaryUserDontUseCardPosDailyOccasionMin;
    @Value("${ordinaryUserDontUseCard.Pos.christmasPeriod.occasion.Max}")
    private int ordinaryUserDontUseCardPosDailyOccasionMax;
    @Value("${ordinaryUserDontUseCard.Pos.christmasPeriod.amount.Min}")
    private int ordinaryUserDontUseCardPosDailyAmountMin;
    @Value("${ordinaryUserDontUseCard.Pos.christmasPeriod.amount.Max}")
    private int ordinaryUserDontUseCardPosDailyAmountMax;

    //ATM
    @Value("${ordinaryUserUseCardDontUseInternet.ATM.monthly.occasion.Min}")
    private int ordinaryUserUseCardDontUseInternetATMmonthlyOccasionMin;
    @Value("${ordinaryUserUseCardDontUseInternet.ATM.monthly.occasion.Max}")
    private int ordinaryUserUseCardDontUseInternetATMmonthlyOccasionMax;
    @Value("${ordinaryUserUseCardDontUseInternet.ATM.monthly.amount.Min}")
    private int ordinaryUserUseCardDontUseInternetATMmonthlyAmountMin;
    @Value("${ordinaryUserUseCardDontUseInternet.ATM.monthly.amount.Max}")
    private int ordinaryUserUseCardDontUseInternetATMmonthlyAmountMax;
    //NET
    @Value("${ordinaryUserUseCardDontUseInternet.NET.monthlyInDecember.occasion.Min}")
    private int ordinaryUserUseCardDontUseInternetNETmonthlyInDecemberOccasionMin;
    @Value("${ordinaryUserUseCardDontUseInternet.NET.monthlyInDecember.occasion.Max}")
    private int ordinaryUserUseCardDontUseInternetNETmonthlyInDecemberOccasionMax;
    @Value("${ordinaryUserUseCardDontUseInternet.NET.monthlyInDecember.amount.Min}")
    private int ordinaryUserUseCardDontUseInternetNETmonthlyInDecemberAmountMin;
    @Value("${ordinaryUserUseCardDontUseInternet.NET.monthlyInDecember.amount.Max}")
    private int ordinaryUserUseCardDontUseInternetNETmonthlyInDecemberAmountMax;
    @Value("${ordinaryUserUseCardDontUseInternet.NET.monthlyInDecember.limit}")
    private int ordinaryUserUseCardDontUseInternetNETmonthlyInDecemberLimit;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public OrdinaryUserUseCardDontUseNetGeneratorService(PersonRepository personRepository, VendorRepository vendorRepository, TransactionRepository transactionRepository) {
        this.personRepository = personRepository;
        this.vendorRepository = vendorRepository;
        this.transactionRepository = transactionRepository;
    }
}
