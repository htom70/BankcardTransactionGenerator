package user.card.generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import user.card.generator.domain.person.Person;
import user.card.generator.domain.person.PersonCategory;
import user.card.generator.repository.CountryRepository;
import user.card.generator.repository.PersonRepository;
import user.card.generator.repository.VendorRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserGenerator {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public UserGenerator(PersonRepository personRepository,
                         CountryRepository countryRepository,
                         VendorRepository vendorRepository) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
        this.vendorRepository = vendorRepository;
    }

    private List<LocalDate> days = new ArrayList<>();


    @Value("${yearNumber}")
    private int yearNumber;
    @Value("${userNumber}")
    private int userNumber;

    @Value("${retiredUseCard.boundary}")
    private int retiredUseCardBoundary;
    @Value("${retiredDontUseCard.boundary}")
    private int retiredDontUseCardBoundary;
    @Value("${ordinaryUserUseCardAndInternet.boundary}")
    private int ordinaryUserUseCardAndInternetBoundary;
    @Value("${ordinaryUserUseCardDontUseInternet.boundary}")
    private int ordinaryUserUseCardAndDontUseInternetBoundary;
    @Value("${ordinaryUserDontUseCard.boundary}")
    private int ordinaryDontUseCardBoundary;
    //retired use card
    @Value("${retiredUseCard.minIncome}")
    private int retiredUseCardMinIncome;
    @Value("${retiredUseCard.maxIncome}")
    private int retiredUseCardMaxIncome;
    //retired don't use card
    @Value("${retiredDontUseCard.minIncome}")
    private int retiredDontUseCardMinIncome;
    @Value("${retiredDontUseCard.maxIncome}")
    private int retiredDontUseCardMaxIncome;
    //ordinary user use card and internet
    @Value("${ordinaryUserUseCardAndInternet.minIncome}")
    private int ordinaryUserUseCardAndInternetMinIncome;
    @Value("${ordinaryUserUseCardAndInternet.maxIncome}")
    private int ordinaryUserUseCardAndInternetMaxIncome;
    //ordinary user use card and don't use internet
    @Value("${ordinaryUserUseCardDontUseInternet.minIncome}")
    private int ordinaryUserUseCardDontUseInternetMinIncome;
    @Value("${ordinaryUserUseCardDontUseInternet.maxIncome}")
    private int ordinaryUserUseCardDontUseInternetMaxIncome;
    //ordinary user don't use card and internet
    @Value("${ordinaryUserDontUseCard.minIncome}")
    private int ordinaryUserDontUseCardMinIncome;
    @Value("${ordinaryUserDontUseCard.maxIncome}")
    private int ordinaryUserDontUseCardMaxIncome;
    //vip user
    @Value("${vipUser.minIncome}")
    private int vipUserMinIncome;
    @Value("${vipUser.maxIncome}")
    private int vipUserMaxIncome;


    public void generate() {
        Random random = new Random();
        fillDays(2018);
        Instant start = Instant.now();
        fillDays(yearNumber);


        Instant finishVendor = Instant.now();
        long vendorGenerationElapsedTime = Duration.between(start, finishVendor).toMillis() / 1000;
        System.out.println("Vendor generálási idő: " + vendorGenerationElapsedTime);


//        if (personRepository.findAll().isEmpty()) {
//            generatePerson(random);
//        }
    }


    private void fillDays(int yearNumber) {
        LocalDate startDate = LocalDate.of(yearNumber, 1, 1);
        LocalDate endDate = LocalDate.of(yearNumber + 1, 1, 1);
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        days = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays)
                .collect(Collectors.toList());
    }


    private void generateAllTransaction(Random random) {
    }


    public void generatePerson(Random random) {
        for (int i = 0; i < userNumber; i++) {
            Person person;
            int income = 0;
            int minIncome = 0;
            int maxIncome = 0;
            String cardNumber;
            do {
                cardNumber = generateCardNumberString(random);
            } while (personRepository.findByCardNumber(cardNumber) != null);
            Integer j = random.nextInt(1000) + 1;
            System.out.println("j értéke: " + j);
            System.out.println("cardNumber: " + cardNumber);
            if (j <= retiredUseCardBoundary) {
                int minRetiredPay;
                int maxRetiredPay;
                int percentOfNotUsingRetiredPeople;
                int numForRetiredClassifying = random.nextInt(100);
                if (numForRetiredClassifying < 1) {
                    minRetiredPay = 28500;
                    maxRetiredPay = 49999;
                    percentOfNotUsingRetiredPeople = 95;
                    person = createRetiredAndSelectRetiredCategory(random, cardNumber,minRetiredPay, maxRetiredPay, percentOfNotUsingRetiredPeople);
                } else if (numForRetiredClassifying > 0 && numForRetiredClassifying <= 27) {
                    minRetiredPay = 50000;
                    maxRetiredPay = 99999;
                    percentOfNotUsingRetiredPeople = 80;
                    person = createRetiredAndSelectRetiredCategory(random, cardNumber, minRetiredPay, maxRetiredPay, percentOfNotUsingRetiredPeople);
                } else if (numForRetiredClassifying > 27 && numForRetiredClassifying <= 67) {
                    minRetiredPay = 100000;
                    maxRetiredPay = 149999;
                    percentOfNotUsingRetiredPeople = 70;
                    person = createRetiredAndSelectRetiredCategory(random, cardNumber, minRetiredPay, maxRetiredPay, percentOfNotUsingRetiredPeople);
                } else if (numForRetiredClassifying > 67 && numForRetiredClassifying <= 86) {
                    minRetiredPay = 150000;
                    maxRetiredPay = 199999;
                    percentOfNotUsingRetiredPeople = 50;
                    person = createRetiredAndSelectRetiredCategory(random, cardNumber, minRetiredPay, maxRetiredPay, percentOfNotUsingRetiredPeople);
                } else if (numForRetiredClassifying > 86 && numForRetiredClassifying <= 97) {
                    minRetiredPay = 200000;
                    maxRetiredPay = 299999;
                    percentOfNotUsingRetiredPeople = 20;
                    person = createRetiredAndSelectRetiredCategory(random, cardNumber, minRetiredPay, maxRetiredPay, percentOfNotUsingRetiredPeople);
                } else if (numForRetiredClassifying > 97) {
                    minRetiredPay = 300000;
                    maxRetiredPay = 1200000;
                    percentOfNotUsingRetiredPeople = 0;
                    person = createRetiredAndSelectRetiredCategory(random, cardNumber, minRetiredPay, maxRetiredPay, percentOfNotUsingRetiredPeople);
                }
                minIncome = retiredUseCardMinIncome;
                maxIncome = retiredUseCardMaxIncome;
                income = generateIncome(random, minIncome, maxIncome);
                person = new Person(cardNumber, PersonCategory.RETIRED_USE_CARD_AND_INTERNET, income);
            } else if (j > retiredUseCardBoundary & j <= retiredDontUseCardBoundary) {
                minIncome = retiredDontUseCardMinIncome;
                maxIncome = retiredDontUseCardMaxIncome;
                income = generateIncome(random, minIncome, maxIncome);
                person = new Person(cardNumber, PersonCategory.RETIRED_DONT_USE_CARD_AND_INTERNET, income);
            } else if (j > retiredDontUseCardBoundary & j <= ordinaryUserUseCardAndInternetBoundary) {
                minIncome = ordinaryUserUseCardAndInternetMinIncome;
                maxIncome = ordinaryUserUseCardAndInternetMaxIncome;
                income = generateIncome(random, minIncome, maxIncome);
                person = new Person(cardNumber, PersonCategory.ORDINARYUSER_USE_CARD_AND_INTERNET, income);
            } else if (j > ordinaryUserUseCardAndInternetBoundary & j <= ordinaryUserUseCardAndDontUseInternetBoundary) {
                minIncome = ordinaryUserUseCardDontUseInternetMinIncome;
                maxIncome = ordinaryUserUseCardDontUseInternetMaxIncome;
                income = generateIncome(random, minIncome, maxIncome);
                person = new Person(cardNumber, PersonCategory.ORDINARY_USER_USE_CARD_DONT_USE_INTERNET, income);
            } else if (j > ordinaryUserUseCardAndDontUseInternetBoundary & j <= ordinaryDontUseCardBoundary) {
                minIncome = ordinaryUserDontUseCardMinIncome;
                maxIncome = ordinaryUserDontUseCardMaxIncome;
                income = generateIncome(random, minIncome, maxIncome);
                person = new Person(cardNumber, PersonCategory.ORDINARY_USER_DONT_USE_CARD, income);
            } else {
                minIncome = vipUserMinIncome;
                maxIncome = vipUserMaxIncome;
                income = generateIncome(random, minIncome, maxIncome);
                person = new Person(cardNumber, PersonCategory.VIP_USER, income);
            }
            System.out.println(person.toString());
            personRepository.save(person);
        }
    }

    private Person createRetiredAndSelectRetiredCategory(Random random, String cardNumber, int minRetiredPay, int maxRetiredPay, int percentOfNotUsingRetiredPeople) {
        Person person;
        int retiredPay = minRetiredPay + random.nextInt(maxRetiredPay - minRetiredPay);
        int numForSelectCategoryWhetherUseCardRetired = random.nextInt(100);
        if (numForSelectCategoryWhetherUseCardRetired < percentOfNotUsingRetiredPeople) {
            person = new Person(cardNumber, PersonCategory.RETIRED_DONT_USE_CARD_AND_INTERNET, retiredPay);
        } else {
            person = new Person(cardNumber, PersonCategory.RETIRED_USE_CARD_AND_INTERNET, retiredPay);
        }
        return person;
    }

    private int generateIncome(Random random, int minIncom, int maxIncome) {
        return (int) (minIncom + random.nextInt(maxIncome - minIncom));
    }

    private String generateCardNumberString(Random random) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        String result = stringBuilder.toString();
        System.out.println("Card Number: " + result);
        return result;
    }
}

