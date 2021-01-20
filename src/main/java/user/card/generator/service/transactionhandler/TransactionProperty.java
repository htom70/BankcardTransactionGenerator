package user.card.generator.service.transactionhandler;

import lombok.Data;

@Data
public class TransactionProperty {

    private int yearlyOccasionMin;
    private int yearlyOccasionMax;
    private int yearlyAmountMin;
    private int yearlyAmountMax;
    private int monthlyOccasionMin;
    private int monthlyOccasionMax;
    private int monthlyAmountMin;
    private int monthlyAmountMax;
    private int dailyOccasionMin;
    private int dailyOccasionMax;
    private int dailyAmountMin;
    private int dailyAmountMax;
    private int OncemonthlyStartDateOrdinalNumberDayMin;
    private int OncemonthlyStartDateOrdinalNumberDayMax;
    private int OnceMonthlyAmountMin;
    private int OnceMonthlyAmountMax;
    private int intraDayOccasionMin;
    private int intraDayOccasionMax;
    private int intraDayAmountMin;;
    private int intraDayAmountMax;;


}
