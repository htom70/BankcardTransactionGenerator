package user.card.generator.domain.transaction.retiredusecard;

import lombok.Data;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.transaction.SimpleTransaction;
import user.card.generator.time.CurrentYear;

import java.time.LocalDate;
import java.util.*;


@Data
public class RetiredUseCardTransactionHandler {

    private RetiredUseCardTransactionProperties retiredUseCardTransactionProperties;
    private int income;
    private double limitRateToIncomeMin;
    private double limitRateToIncomeMax;
    private int incomeMaxInChristmasPeriod;
    private int incomeMaxInTravelPeriod;
    private static final int RETIRED_NUMBER = 100;


    public RetiredUseCardTransactionHandler() {
        this.retiredUseCardTransactionProperties = new RetiredUseCardTransactionProperties();
    }

    public void generate(CurrentYear currentYear, Random random) {
        List<LocalDate> days = currentYear.getDays();
        List<LocalDate> allSaturdays = currentYear.getSaturdays();
        List<LocalDate> daysOfTravel = new ArrayList<>();
        List<LocalDate> realSaturdays = new ArrayList<>();
        List<LocalDate> normalTransactionDays = new ArrayList<>();
        Map<LocalDate, List<SimpleTransaction>> simpleDailyTransactions = new HashMap<>();
        List<LocalDate> saturdaysWithoutTravellingDays = new ArrayList<>();
        List<LocalDate> remainderDaysWithoutTravellingAndSaturdays = new ArrayList<>();
        int numOfTravellingUser = (int) (RETIRED_NUMBER * retiredUseCardTransactionProperties.getRetiredUseCardFirstTypeForeignCountryJourneyEntityRate());
        List<Integer> indexesOfTravellingUsers = new ArrayList<>();
        for (int i = 0; i < numOfTravellingUser - 1; i++) {
            int index = random.nextInt(RETIRED_NUMBER);
            indexesOfTravellingUsers.add(index);
        }

        for (int i = 0; i < RETIRED_NUMBER; i++) {
            Person person = new Person();

            if (indexesOfTravellingUsers.contains(i)) createTravellingDays(random, days, daysOfTravel);
            createRealSaturdays(days, allSaturdays, realSaturdays);


        }
    }

    private void createTravellingDays(Random random, List<LocalDate> days, List<LocalDate> daysOfTravel) {
        //külföldi utazás
        int begindayOfTravelling = random.nextInt(358);
        for (int j = 0; j <7 ; j++) {
            LocalDate dateOfTravel = days.get(begindayOfTravelling + j);
            daysOfTravel.add(dateOfTravel);
        }
        days.removeAll(daysOfTravel);
    }

    private void createRealSaturdays(List<LocalDate> days, List<LocalDate> allSaturdays, List<LocalDate> realSaturdays) {
        for (LocalDate saturday : allSaturdays) {
            if (days.contains(saturday)) {
                realSaturdays.add(saturday);
            }
        }
        days.removeAll(realSaturdays);
    }

    private void generateATMTransactions() {
    }

    private void generatePosTransactions() {
    }

    private void createIncome(Random random) {
        int minInCome = retiredUseCardTransactionProperties.getRetiredUseCardMinIncome();
        int averageIncome = retiredUseCardTransactionProperties.getRetiredUseCardAvarageIncome();
        int maxIncome = retiredUseCardTransactionProperties.getRetiredUseCardMaxIncome();
        income = (int) (averageIncome + random.nextGaussian() * (maxIncome - minInCome));
    }


}
