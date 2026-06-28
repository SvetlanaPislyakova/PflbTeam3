package tests.ui.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tests.BaseTest;
import ui.dto.Car;
import ui.dto.User;
import ui.dto.UserFactory;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class CreateCarTest extends BaseTest {

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Создание нового автомобиля с валидными данными")
    public void createCarWithValidData() {
        Car car = Car.builder().build();
        carSteps.createNewCar(car);

        int carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);
        assertNotNull(carID, "ID автомобиля должен быть создан");
        assertTrue(carID > 0, "ID автомобиля должен быть положительным");
    }

    @ParameterizedTest
    @DisplayName("Создание автомобилей с разными параметрами")
    @CsvSource({
            "CNG, Toyota, Camry, 100000",
            "Diesel, BMW, X5, 200000",
            "Gasoline, Mercedes, E-Class, 250000",
            "PHEV, Audi, A8, 300000",
            "Hydrogenic, Lexus, LS, 350000"
    })
    public void createCarWithVariousData(String engineType, String mark, String model, String price) {
        Car car = Car.builder()
                .engineType(engineType)
                .mark(mark)
                .model(model)
                .price(new BigDecimal(price))
                .build();
    }
}
