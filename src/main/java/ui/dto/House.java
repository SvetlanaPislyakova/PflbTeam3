package ui.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class House {
    private final Integer floors;
    private final BigDecimal price;
    private final Integer warmCoveredParking;
    private final Integer warmNotCoveredParking;
    private final Integer coldCoveredParking;
    private final Integer coldNotCoveredParking;
}
