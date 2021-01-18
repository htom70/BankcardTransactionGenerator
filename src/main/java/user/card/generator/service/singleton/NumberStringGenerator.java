package user.card.generator.service.singleton;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class NumberStringGenerator {

    public List<String> generateDifferentCodeStrings(int digitNumber, int number) {
        Random random = new Random();
        List<String> result = new ArrayList<>();
        String code;
        for (int i = 0; i <number ; i++) {
            do {
                code = generateCardNumberString(random, digitNumber);
            } while (result.contains(code));
            result.add(code);
        }
        return result;
    }

    private String generateCardNumberString(Random random, int digitNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digitNumber; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        String result = stringBuilder.toString();
        return result;
    }
}
