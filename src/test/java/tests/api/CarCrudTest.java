package tests.api;

import api.adapters.CarAdapter;
import api.models.CarRq;
import api.models.CarRs;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CarCrudTest extends BaseApiTest {

    CarAdapter carAdapter = new CarAdapter();

    @Test
    void getCarTest() {
        CarRq carRq = CarRq.builder()
                .mark("Audi")
                .model("A6")
                .engineType("CNG")
                .price(BigDecimal.valueOf(15000.0))
                .build();

        CarRs created = carAdapter.createCar(carRq, accessToken);
        CarRs received = carAdapter.getCar(created.getId(), accessToken);
        assertEquals(created.getId(), received.getId());
        carAdapter.deleteCar(created.getId(), accessToken);
    }

    @Test
    void deleteCarTest() {
        CarRq carRq = CarRq.builder()
                .mark("Kia")
                .model("Rio")
                .engineType("Gasoline")
                .price(BigDecimal.valueOf(5000.0))
                .build();

        CarRs car = carAdapter.createCar(carRq, accessToken);
        carAdapter.deleteCar(car.getId(), accessToken);
    }

    @Test
    void createDieselCarTest() {
        createAndCheck("BMW", "X5", "Diesel", BigDecimal.valueOf(10000.0));
    }

    @Test
    void createPetrolCarTest() {
        createAndCheck("Audi", "A4", "Hydrogenic", BigDecimal.valueOf(9000.0));
    }

    @Test
    void createElectricCarTest() {
        createAndCheck("Tesla", "Model3", "Electric", BigDecimal.valueOf(50000.0));
    }

    @Test
    void createCheapCarTest() {
        createAndCheck("Lada", "Granta", "PHEV", BigDecimal.valueOf(1000.0));
    }

    @Test
    void createExpensiveCarTest() {
        createAndCheck("Tesla", "ModelX", "Electric", BigDecimal.valueOf(90000.0));
    }

    @Test
    void createJapaneseCarTest() {
        createAndCheck("Toyota", "Camry", "Diesel", BigDecimal.valueOf(8000.0));
    }

    @Test
    void createGermanCarTest() {
        createAndCheck("Mercedes", "E200", "Diesel", BigDecimal.valueOf(17000.0));
    }

    private void createAndCheck(String mark,
                                String model,
                                String engineType,
                                BigDecimal price) {

        CarRq carRq = CarRq.builder()
                .mark(mark)
                .model(model)
                .engineType(engineType)
                .price(price)
                .build();

        CarRs car = carAdapter.createCar(carRq, accessToken);

        assertEquals(mark, car.getMark());

        carAdapter.deleteCar(car.getId(), accessToken);
    }
}
