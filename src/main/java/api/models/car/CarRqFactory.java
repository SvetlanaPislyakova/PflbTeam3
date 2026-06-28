package api.models.car;

import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class CarRqFactory {

    private static final Faker faker = new Faker();

    public static CarRq validCar() {
        return CarRq.builder()
                .mark(faker.company().name())
                .model(faker.harryPotter().character())
                .price(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 10000)))
                .engineType(faker.options().option("CNG", "Diesel", "Gasoline", "Hydrogenic", "PHEV", "Electric"))
                .build();
    }
}
