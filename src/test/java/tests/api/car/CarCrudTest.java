package tests.api.car;

import api.adapters.CarAdapter;
import api.models.car.CarRq;
import api.models.car.CarRqFactory;
import api.models.car.CarRs;
import io.qameta.allure.Description;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.api.base.BaseApiTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarCrudTest extends BaseApiTest {

    CarAdapter carAdapter = new CarAdapter();

    @Test
    @DisplayName("Получение списка автомобилей")
    public void getCars() {
        List<CarRs> cars = carAdapter.getCars(accessToken);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(cars).isNotNull();
            softly.assertThat(cars).isNotEmpty();
            for(CarRs car : cars) {
                softly.assertThat(car.getId()).isNotNull();
                softly.assertThat(car.getEngineType()).isNotNull();
                softly.assertThat(car.getMark()).isNotNull();
                softly.assertThat(car.getModel()).isNotNull();
             softly.assertThat(String.valueOf(car.getPrice())).isNotBlank();
            }
        });
    }

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
    @DisplayName("Изменение автомобиля")
    @Description("Тест проверяет создание и последующее изменение автомобиля, затем удаляет автомобиль")
    void updateCarTest() {
        CarRq original = CarRqFactory.validCar();

        Integer carID = carAdapter.createCarAndGetId(original, accessToken);

        try {
            CarRq updatedRq = original.toBuilder()
                    .price(BigDecimal.valueOf(12345.00))
                    .model("UpdatedModel")
                    .build();

            CarRs updatedRs = carAdapter.updateCar(carID, updatedRq, accessToken);

            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(carID.intValue()).isEqualTo(updatedRs.getId());
            softly.assertThat(updatedRq.getModel()).isEqualTo(updatedRs.getModel());
            softly.assertThat(0).isEqualTo(updatedRq.getPrice().compareTo(updatedRs.getPrice()));

            CarRs received = carAdapter.getCar(carID, accessToken);
            softly.assertThat(updatedRq.getModel()).isEqualTo(received.getModel());
            softly.assertThat(0).isEqualTo(updatedRq.getPrice().compareTo(received.getPrice()));

        } finally {
            carAdapter.deleteCar(carID, accessToken);
        }
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
