package api.models.car;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarRs {

    private int id;
    private String mark;
    private String model;
    private String engineType;
    private BigDecimal price;
}
