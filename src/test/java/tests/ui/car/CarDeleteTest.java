package tests.ui.car;

import api.adapters.CarAdapter;
import api.models.CarRq;
import api.models.CarRs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarDeleteTest extends BaseTest {

    private final CarAdapter carAdapter = new CarAdapter();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("UI - Удаление автомобиля через страницу All DELETE")
    public void deleteCarThroughUI() {
        // 1. Создаем машину через API
        CarRq carRq = CarRq.builder()
                .mark("Toyota")
                .model("Camry")
                .engineType("Diesel")
                .price(BigDecimal.valueOf(25000.00))
                .build();
        CarRs carRs = carAdapter.createCar(carRq, token);
        Integer carId = carRs.getId();

        // 2. Удаляем машину через UI
        allDeletePage.openPage()
                .isPageOpened()
                .deleteCar(carId);

        // 3. Проверяем статус
        assertThat(allDeletePage.getCarStatusCode()).isEqualTo(204);

        // 4. Проверяем в БД
        assertThat(dbSteps.isCarExistsInDB(carId)).isFalse();
    }
}