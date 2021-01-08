package user.card.generator.domain.transaction;

import java.util.Arrays;

public class SimpleForeignCountryJourneyPropertyFromConfig {

    private double entityRate;
    private int occasionMin;
    private int occasionMax;
    private int dayNumberMin;
    private int dayNumberMax;
    private String[] countries;

    public SimpleForeignCountryJourneyPropertyFromConfig(int occasionMin, int occasionMax, int dayNumberMin, int dayNumberMax, String countriesString, double entityRate) {
        this.occasionMin = occasionMin;
        this.occasionMax = occasionMax;
        this.dayNumberMin = dayNumberMin;
        this.dayNumberMax = dayNumberMax;
        this.countries = splitCountriesString(countriesString);
        this.entityRate = entityRate;

    }

    private String[] splitCountriesString(String countriesString) {
        String[] result = Arrays.stream(countriesString.split(",")).map(String::trim).toArray(String[]::new);
        return result;
    }

    public int getOccasionMin() {
        return occasionMin;
    }

    public void setOccasionMin(int occasionMin) {
        this.occasionMin = occasionMin;
    }

    public int getOccasionMax() {
        return occasionMax;
    }

    public void setOccasionMax(int occasionMax) {
        this.occasionMax = occasionMax;
    }

    public int getDayNumberMin() {
        return dayNumberMin;
    }

    public void setDayNumberMin(int dayNumberMin) {
        this.dayNumberMin = dayNumberMin;
    }

    public int getDayNumberMax() {
        return dayNumberMax;
    }

    public void setDayNumberMax(int dayNumberMax) {
        this.dayNumberMax = dayNumberMax;
    }

    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
    }

    public double getEntityRate() {
        return entityRate;
    }

    public void setEntityRate(double entityRate) {
        this.entityRate = entityRate;
    }


}
