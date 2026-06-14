package api.models;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserRq {

    private static Faker faker = new Faker();

    private final Long id;
    @Builder.Default
    private final String firstName = faker.name().firstName();
    @Builder.Default
    private final String secondName = faker.name().lastName();
    @Builder.Default
    private final Integer age = faker.number().numberBetween(10, 100);
    @Builder.Default
    private final String sex = faker.options().option("MALE", "FEMALE");
    @Builder.Default
    private final BigDecimal money = BigDecimal.valueOf
            (faker.number().randomDouble(5, 0, 1000000));
}
