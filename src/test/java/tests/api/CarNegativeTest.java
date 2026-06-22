package tests.api;

import api.adapters.CarAdapter;
import api.models.CarRq;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarNegativeTest extends BaseApiTest {

    CarAdapter carAdapter = new CarAdapter();

    @Test
    void createCarWithoutMarkTest() {
        CarRq car = CarRq.builder()
                .model("X5")
                .engineType("Diesel")
                .price(BigDecimal.valueOf(10000.0))
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }

    @Test
    void createCarWithoutModelTest() {
        CarRq car = CarRq.builder()
                .mark("BMW")
                .engineType("Diesel")
                .price(BigDecimal.valueOf(10000.0))
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }

    @Test
    void createCarWithoutEngineTest() {
        CarRq car = CarRq.builder()
                .mark("BMW")
                .model("X5")
                .price(BigDecimal.valueOf(10000.0))
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }

    @Test
    void createCarWithoutPriceTest() {
        CarRq car = CarRq.builder()
                .mark("BMW")
                .model("X5")
                .engineType("Diesel")
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }

    @Test
    void createCarWithNegativePriceTest() {
        CarRq car = CarRq.builder()
                .mark("BMW")
                .model("X5")
                .engineType("Diesel")
                .price(BigDecimal.valueOf(-100.0))
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }

    @Test
    void createCarWithZeroPriceTest() {
        CarRq car = CarRq.builder()
                .mark("BMW")
                .model("X5")
                .engineType("Diesel")
                .price(BigDecimal.valueOf(0.0))
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }

    @Test
    void getNotExistingCarTest() {
        assertThrows(Exception.class,
                () -> carAdapter.getCar(999999, accessToken));
    }

    @Test
    void deleteNotExistingCarTest() {
        assertThrows(Exception.class,
                () -> carAdapter.deleteCar(999999, accessToken));
    }

    @Test
    void createCarWithEmptyMarkTest() {
        CarRq car = CarRq.builder()
                .mark("")
                .model("X5")
                .engineType("Diesel")
                .price(BigDecimal.valueOf(10000.0))
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }

    @Test
    void createCarWithEmptyModelTest() {
        CarRq car = CarRq.builder()
                .mark("BMW")
                .model("")
                .engineType("Diesel")
                .price(BigDecimal.valueOf(10000.0))
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }
}
