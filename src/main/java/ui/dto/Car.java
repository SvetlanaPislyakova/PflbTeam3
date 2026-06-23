package ui.dto;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Car {
    private static final Faker faker = new Faker();

    @Builder.Default
    private final String engineType = generateEngineType();

    @Builder.Default
    private final String mark = faker.company().name();

    @Builder.Default
    private final String model = faker.harryPotter().character();

    @Builder.Default
    private final BigDecimal price = BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 999999999));

    private static String generateEngineType() {
        return new Faker().options().option("CNG", "Diesel", "Gasoline", "Hydrogenic", "PHEV");
    }
}