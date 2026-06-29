package api.models;

import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class HouseRqFactory {

    private static final Faker FAKER = new Faker();

    public static HouseRq validHouse() {
        return HouseRq.builder()
                .floorCount(FAKER.number().numberBetween(1, 10))
                .price(BigDecimal.valueOf(FAKER.number().randomDouble(2, 10000, 1000000)))
                .build();
    }

    public static HouseRq houseWithNullFloors() {
        return validHouse().toBuilder()
                .floorCount(null)
                .build();
    }

    public static HouseRq houseWithNullPrice() {
        return validHouse().toBuilder()
                .price(null)
                .build();
    }
}