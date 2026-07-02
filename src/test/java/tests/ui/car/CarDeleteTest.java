package tests.ui.car;

import api.adapters.CarAdapter;
import api.models.car.CarRq;
import api.models.car.CarRs;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.ui.base.BaseTest;
import io.qameta.allure.Description;

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
    @Description("Удаление авто через страницу All DELETE с проверкой в БД")
    public void deleteCarThroughUI() {
        CarRq carRq = CarRq.builder()
                .mark("Toyota")
                .model("Camry")
                .engineType("Diesel")
                .price(BigDecimal.valueOf(25000.00))
                .build();
        CarRs carRs = carAdapter.createCar(carRq);
        Integer carId = carRs.getId();

        assertThat(dbSteps.isCarExistsInDB(carId)).isTrue();

        allDeletePage.openPage()
                .isPageOpened()
                .deleteCar(carId);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(allDeletePage.getCarStatusCode()).isEqualTo(204);
        softly.assertThat(dbSteps.isCarExistsInDB(carId)).isFalse();
        softly.assertAll();
    }
}