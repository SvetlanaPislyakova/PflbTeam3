package tests.api;

import api.adapters.CarAdapter;
import api.models.CarRq;
import api.models.CarRs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarCrudTest extends BaseApiTest {

    CarAdapter carAdapter = new CarAdapter();

    @Test
    void createCarTest() {
        CarRq carRq = CarRq.builder()
                .mark("BMW")
                .model("X5")
                .engineType("Diesel")
                .price(10000.0)
                .build();

        CarRs car = carAdapter.createCar(carRq, accessToken);
        assertEquals("BMW", car.getMark());
        carAdapter.deleteCar(car.getId(), accessToken);
    }

    @Test
    void getCarTest() {
        CarRq carRq = CarRq.builder()
                .mark("Audi")
                .model("A6")
                .engineType("Petrol")
                .price(15000.0)
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
                .engineType("Petrol")
                .price(5000.0)
                .build();

        CarRs car = carAdapter.createCar(carRq, accessToken);
        carAdapter.deleteCar(car.getId(), accessToken);
    }

    @Test
    void createDieselCarTest() {
        createAndCheck("BMW", "X5", "Diesel", 10000.0);
    }

    @Test
    void createPetrolCarTest() {
        createAndCheck("Audi", "A4", "Petrol", 9000.0);
    }

    @Test
    void createElectricCarTest() {
        createAndCheck("Tesla", "Model3", "Electric", 50000.0);
    }

    @Test
    void createCheapCarTest() {
        createAndCheck("Lada", "Granta", "Petrol", 1000.0);
    }

    @Test
    void createExpensiveCarTest() {
        createAndCheck("Tesla", "ModelX", "Electric", 90000.0);
    }

    @Test
    void createJapaneseCarTest() {
        createAndCheck("Toyota", "Camry", "Petrol", 8000.0);
    }

    @Test
    void createGermanCarTest() {
        createAndCheck("Mercedes", "E200", "Diesel", 17000.0);
    }

    private void createAndCheck(String mark,
                                String model,
                                String engineType,
                                Double price) {

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
