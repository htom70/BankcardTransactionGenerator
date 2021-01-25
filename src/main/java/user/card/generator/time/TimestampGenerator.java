package user.card.generator.time;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class TimestampGenerator {

    public static  Timestamp  generate(Random random,LocalDate localDate) {
        int randomHour = random.nextInt(24);
        int randomMin = random.nextInt(60);
        int randomSec = random.nextInt(60);
        int randomNanosecs = random.nextInt(1000000);
        LocalDateTime dateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), randomHour, randomMin, randomSec, randomNanosecs);
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        return timestamp;
    }
}
