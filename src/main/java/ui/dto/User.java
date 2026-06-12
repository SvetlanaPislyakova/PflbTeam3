package ui.dto;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private static Faker faker = new Faker();

    @Builder.Default
    private final String firstName = faker.name().firstName();
    @Builder.Default
    private final String lastName = faker.name().lastName();
    @Builder.Default
    private final String age = String.valueOf(faker.number().numberBetween(10, 80));
    @Builder.Default
    private final String Sex = "FEMALE";
    @Builder.Default
    private final String money = String.valueOf(faker.number().randomDouble(2, 0, 1000000));
}
