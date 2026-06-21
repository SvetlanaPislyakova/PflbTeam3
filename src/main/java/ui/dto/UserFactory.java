package ui.dto;

import api.models.user.UserRq;
import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class UserFactory {

    private static final Faker FAKER = new Faker();

    public static User validUser() {
        return User.builder()
                .age(FAKER.number().numberBetween(10, 100))
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .money(BigDecimal.valueOf(FAKER.number().randomDouble(2, 0, 1000000)))
                .sex(FAKER.options().option("MALE", "FEMALE"))
                .build();
    }

    public static User userWithNullFirstName() {
        return validUser().toBuilder()
                .firstName(null)
                .build();
    }

    public static User userWithNullLastName() {
        return validUser().toBuilder()
                .lastName(null)
                .build();
    }

    public static User userWithNullAge() {
        return validUser().toBuilder()
                .age(null)
                .build();
    }

    public static User userWithNullMoney() {
        return validUser().toBuilder()
                .money(null)
                .build();
    }

    public static User userWithNullSex() {
        return validUser().toBuilder()
                .sex(null)
                .build();
    }
}
