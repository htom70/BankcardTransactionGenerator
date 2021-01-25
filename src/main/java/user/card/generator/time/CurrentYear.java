package user.card.generator.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrentYear {

    private Year year;
    private List<LocalDate> days = new ArrayList<>();

    public CurrentYear(int yearNumber) {
        year = Year.of(yearNumber);
        init();
    }

    private void init() {
        LocalDate startDate = LocalDate.of(year.getValue(),1,1);
        LocalDate endDate = LocalDate.of(year.getValue()+1, 1, 1);
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        days = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays)
                .collect(Collectors.toList());
    }

    public Year getYear() {
        return year;
    }

    public List<LocalDate> getDays() {
        return days;
    }

    public List<LocalDate> getDaysInMonthWithoutSaturdays(Month currentMonth) {
        List<LocalDate> daysInMonth = new ArrayList<>();
        int lengthOfMonth = currentMonth.length(year.isLeap());
        LocalDate startDate = LocalDate.of(year.getValue(), currentMonth.getValue(), 1);
        LocalDate endDate = LocalDate.of(year.getValue(), currentMonth.getValue(), lengthOfMonth);
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        daysInMonth=Stream.iterate(startDate, date -> date.plusDays(1))
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY)
                .limit(numOfDays)
                .collect(Collectors.toList());
        return daysInMonth;
    }

    public List<LocalDate> getSaturdays() {
        List<LocalDate> saturdays = new ArrayList<>();
        saturdays = days.stream()
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY)
                .collect(Collectors.toList());
        return saturdays;
    }

    public List<LocalDate> getSaturdaysInMonth(Month currentMonth) {
        int lengthOfMonth = currentMonth.length(year.isLeap());
        LocalDate startDate = LocalDate.of(year.getValue(), currentMonth.getValue(), 1);
        LocalDate endDate = LocalDate.of(year.getValue(), currentMonth.getValue(), lengthOfMonth);
        List<LocalDate> saturdays = new ArrayList<>();
        saturdays = days.stream()
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY)
                .collect(Collectors.toList());
        return saturdays;
    }

    public List<LocalDate> getDaysOfMonth(Month currentMonth) {
        int lengthOfMonth = currentMonth.length(year.isLeap());
        LocalDate startDate = LocalDate.of(year.getValue(), currentMonth.getValue(), 1);
        LocalDate endDate = LocalDate.of(year.getValue(), currentMonth.getValue(), lengthOfMonth);
        List<LocalDate> daysOfMonth  = days.stream()
                .filter(date -> (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate)))
                .collect(Collectors.toList());
        return daysOfMonth;
    }

    public List<List<LocalDate>> getDaysOfMonths() {
        List<List<LocalDate>> daysOfMonths = new ArrayList<>();
        Month[] months = Month.values();
        for (int i = 0; i < months.length; i++) {
            Month currentMonth = months[i];
            int lengthOfMonth = currentMonth.length(year.isLeap());
            LocalDate startDate = LocalDate.of(year.getValue(), currentMonth.getValue(), 1);
            LocalDate endDate = LocalDate.of(year.getValue(), currentMonth.getValue(), lengthOfMonth);
            List<LocalDate> daysOfCurrentMonth = days.stream()
                    .filter(date -> (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate)))
                    .collect(Collectors.toList());
            daysOfMonths.addAll(Collections.singleton(daysOfCurrentMonth));
        }
        return daysOfMonths;
    }

    public Map<Integer, List<LocalDate>> getMonthsAndDaysInMonth(List<LocalDate> days) {
        Map<Integer, List<LocalDate>> result = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            Month currentMonth = Month.of(i);
            int lengthOfMonth = currentMonth.length(year.isLeap());
            LocalDate startDate = LocalDate.of(year.getValue(), currentMonth.getValue(), 1);
            LocalDate endDate = LocalDate.of(year.getValue(), currentMonth.getValue(), lengthOfMonth);
            List<LocalDate> daysOfMonth  = days.stream()
                    .filter(date -> (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate)))
                    .collect(Collectors.toList());
            result.put(i, daysOfMonth);
        }
        return result;
    }




}
