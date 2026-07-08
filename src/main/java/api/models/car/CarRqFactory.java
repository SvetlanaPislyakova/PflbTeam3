package api.models.car;

import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class CarRqFactory {

    private static final Faker FAKER = new Faker();

    public static CarRq validCar() {
        return CarRq.builder()
                .mark(FAKER.lorem().word())
                .model(FAKER.lorem().word())
                .price(BigDecimal.valueOf(FAKER.number().randomDouble(2, 1000, 50000)))
                .engineType(FAKER.options().option("CNG", "Diesel", "Gasoline", "Hydrogenic", "PHEV", "Electric"))
                .build();
    }
}