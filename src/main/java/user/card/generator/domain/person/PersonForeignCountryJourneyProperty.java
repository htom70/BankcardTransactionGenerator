package user.card.generator.domain.person;

public class PersonForeignCountryJourneyProperty {

    private int occasion;
    private int dayNumberMin;
    private int dayNumberMax;
    private String[] countries;

    public PersonForeignCountryJourneyProperty(int occasion, int dayNumberMin, int dayNumberMax, String[] countries) {
        this.occasion = occasion;
        this.dayNumberMin = dayNumberMin;
        this.dayNumberMax = dayNumberMax;
        this.countries = countries;
    }

    public int getOccasion() {
        return occasion;
    }

    public void setOccasion(int occasion) {
        this.occasion = occasion;
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
}
