package tests.api;

import api.adapters.CarAdapter;
import api.models.car.CarRq;
import api.models.car.CarRs;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CarCrudTest extends BaseApiTest {

    CarAdapter carAdapter = new CarAdapter();

    @Test
    @DisplayName("Получение автомобиля по ID")
    @Description("Тест проверяет создание автомобиля, его получение по ID и последующее удаление")
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
    @DisplayName("Удаление автомобиля")
    @Description("Тест проверяет создание и последующее удаление автомобиля")
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
    @DisplayName("Создание дизельного автомобиля")
    @Description("Тест проверяет создание автомобиля с дизельным двигателем (BMW X5)")
    void createDieselCarTest() {
        createAndCheck("BMW", "X5", "Diesel", BigDecimal.valueOf(10000.0));
    }

    @Test
    @DisplayName("Создание водородного автомобиля")
    @Description("Тест проверяет создание автомобиля с водородным двигателем (Audi A4)")
    void createPetrolCarTest() {
        createAndCheck("Audi", "A4", "Hydrogenic", BigDecimal.valueOf(9000.0));
    }

    @Test
    @DisplayName("Создание электрического автомобиля")
    @Description("Тест проверяет создание электромобиля (Tesla Model3)")
    void createElectricCarTest() {
        createAndCheck("Tesla", "Model3", "Electric", BigDecimal.valueOf(50000.0));
    }

    @Test
    @DisplayName("Создание дешевого автомобиля")
    @Description("Тест проверяет создание недорогого автомобиля (Lada Granta)")
    void createCheapCarTest() {
        createAndCheck("Lada", "Granta", "PHEV", BigDecimal.valueOf(1000.0));
    }

    @Test
    @DisplayName("Создание дорогого автомобиля")
    @Description("Тест проверяет создание дорогого электромобиля (Tesla ModelX)")
    void createExpensiveCarTest() {
        createAndCheck("Tesla", "ModelX", "Electric", BigDecimal.valueOf(90000.0));
    }

    @Test
    @DisplayName("Создание японского автомобиля")
    @Description("Тест проверяет создание японского автомобиля (Toyota Camry)")
    void createJapaneseCarTest() {
        createAndCheck("Toyota", "Camry", "Diesel", BigDecimal.valueOf(8000.0));
    }

    @Test
    @DisplayName("Создание немецкого автомобиля")
    @Description("Тест проверяет создание немецкого автомобиля (Mercedes E200)")
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
