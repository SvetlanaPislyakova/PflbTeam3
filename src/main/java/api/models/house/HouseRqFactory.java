package api.models.house;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.List;

public class HouseRqFactory {

    private static final Faker FAKER = new Faker();

    public static HouseRq validHouse() {
        return HouseRq.builder()
                .floorCount(FAKER.number().numberBetween(1, 10))
                .price(BigDecimal.valueOf(FAKER.number().randomDouble(2, 10000, 1000000)))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
    }
}
