package api.models.user;

import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class UserRqFactory {

    private static final Faker faker = new Faker();

    public static UserRq validUser() {
        return UserRq.builder()
                .age(faker.number().numberBetween(10, 100))
                .firstName(faker.name().firstName())
                .secondName(faker.name().lastName())
                .money(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000)))
                .sex(faker.options().option("MALE", "FEMALE"))
                .build();
    }

    public static UserRq validUserWithId(Integer id) {
        return validUser().toBuilder()
                .id(id)
                .build();
    }

    public static UserRq userWithNullFirstName() {
        return validUser().toBuilder()
                .firstName(null)
                .build();
    }

    public static UserRq userWithNullSecondName() {
        return validUser().toBuilder()
                .secondName(null)
                .build();
    }

    public static UserRq userWithNullAge() {
        return validUser().toBuilder()
                .age(null)
                .build();
    }

    public static UserRq userWithNullMoney() {
        return validUser().toBuilder()
                .money(null)
                .build();
    }
}
