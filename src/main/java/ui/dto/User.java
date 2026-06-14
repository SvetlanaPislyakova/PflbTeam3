package ui.dto;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class User {

    private static final Faker faker = new Faker();

    private final Long id;
    @Builder.Default
    private final String firstName = faker.name().firstName();
    @Builder.Default
    private final String lastName = faker.name().lastName();
    @Builder.Default
    private final Integer age = faker.number().numberBetween(10, 80);
    @Builder.Default
    private final String Sex = faker.options().option("MALE", "FEMALE");
    @Builder.Default
    private final BigDecimal money = BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000));
}
