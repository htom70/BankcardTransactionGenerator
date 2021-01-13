package user.card.generator.domain.field;

import user.card.generator.domain.country.City;
import user.card.generator.domain.name.ManName;
import user.card.generator.domain.name.WomanName;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldGenerator {

    public Double generateFromZeroToOne(Random random) {
        return random.nextDouble();
    }

    public Double generateFromMinusOneToOne(Random random) {
        return -1 + random.nextDouble() * 2;
    }

    public Double generateFromMinusThousandToThousand(Random random) {
        return -1000 + random.nextDouble() * 2000;
    }

    public Double generateFromZeroToFiveHundredThousand(Random random) {
        return random.nextDouble() * 500000;
    }

    public Double generateFromMinusHundredToHundred(Random random) {
        return -100 + random.nextDouble() * 200;
    }

    public String generateMixedLastName(Random random) {
        String result;
        int index = random.nextInt();
        if (index == 0) {
            result = generateManLastName(random);
        } else result = generateWomanLastName(random);
        return result;
    }

    public String generateWomanLastName(Random random) {
        WomanName[] womanNames = WomanName.values();
        return womanNames[random.nextInt(womanNames.length)].toString();
    }

    public String generateManLastName(Random random) {
        ManName[] manNames = ManName.values();
        return manNames[random.nextInt(manNames.length)].toString();
    }

    public String generateCityName(Random random, List<City> cities) {
        int size = cities.size();
        return cities.get(random.nextInt(size)).getName();
    }

    public String generatePostalCode(Random random, List<City> cities) {
        int size = cities.size();
        return cities.get(random.nextInt(size)).getPostalCode();
    }

    public String generateCountyCode(Random random, List<City> cities) {
        int size = cities.size();
        return cities.get(random.nextInt(size)).getCounty();
    }

    public LocalDate generateDate(Random random, LocalDate startDate, LocalDate endDate) {
        List<LocalDate> days = Stream.iterate(startDate, date -> date.plusDays(1))
                .filter(date -> date.isBefore(endDate))
                .collect(Collectors.toList());
        int size = days.size();
        return days.get(random.nextInt(size));
    }

}
