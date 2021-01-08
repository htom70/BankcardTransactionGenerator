package user.card.generator.domain.country;

import java.util.HashMap;
import java.util.Map;

public class CountryCodeAndCurrency {

    private static final Map<String, String> namesAndCurrencies = new HashMap<String, String>() {
        {
            put("HU", "HUF");
            put("DE", "EUR");
            put("AT", "EUR");
            put("IT", "EUR");
            put("HR", "HRK");
            put("SK", "EUR");
            put("SP", "EUR");
            put("GR", "EUR");
            put("GR", "EUR");
            put("JP", "JPY");
            put("US", "USD");
            put("RO", "RON");
            put("PL", "PLN");
            put("RU", "RUB");
            put("CA", "CAD");
        }
    };

    public static Map<String,String> getProperties() {
        return namesAndCurrencies;
    }
}
