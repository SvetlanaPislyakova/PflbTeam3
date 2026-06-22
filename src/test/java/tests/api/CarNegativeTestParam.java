package tests.api;

import api.adapters.CarAdapter;
import api.models.CarRq;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

public class CarNegativeTestParam extends BaseApiTest  {

    private final CarAdapter carAdapter = new CarAdapter();

    @ParameterizedTest
    @CsvSource({
            ",X5,Diesel,10000",
            "BMW,,Diesel,10000",
            "BMW,X5,,10000",
            "BMW,X5,Petrol,10000",
            "BMW,X5,Diesel,-100",
            "BMW,X5,Diesel,0",
            "'',X5,Diesel,10000",
            "BMW,X5,Diesel,1000000000000",
            "123,X5,Diesel,10000",
            "BMW,123,Diesel,10000"
    })
    void createInvalidCarTest(
            String mark,
            String model,
            String engineType,
            BigDecimal price) {

        CarRq car = CarRq.builder()
                .mark(mark)
                .model(model)
                .engineType(engineType)
                .price(price)
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }
}
