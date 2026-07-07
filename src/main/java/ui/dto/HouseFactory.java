package ui.dto;

import java.math.BigDecimal;

public class HouseFactory {

    public static House validHouse() {
        return House.builder()
                .floors(3)
                .price(BigDecimal.valueOf(1_000_000))
                .warmCoveredParking(1)
                .warmNotCoveredParking(0)
                .coldCoveredParking(0)
                .coldNotCoveredParking(0)
                .build();
    }

    public static House houseWithNullFloors() {
        return validHouse().toBuilder()
                .floors(null)
                .build();
    }

    public static House houseWithNullPrice() {
        return validHouse().toBuilder()
                .price(null)
                .build();
    }

    public static House houseWithNegativePrice() {
        return validHouse().toBuilder()
                .price(BigDecimal.valueOf(-100))
                .build();
    }
}
