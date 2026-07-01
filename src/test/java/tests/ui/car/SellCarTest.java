package tests.ui.car;

import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import io.qameta.allure.Description;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tests.ui.base.BaseTest;
import ui.dto.Car;
import java.math.BigDecimal;

import static com.codeborne.selenide.Selenide.sleep;

public class SellCarTest extends BaseTest {

    SoftAssertions softly = new SoftAssertions();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Продажа автомобиля пользователем")
    @Description("Тест проверяет продажу автомобиля")
    public void sellCarSuccess() {
        UserRq seller = UserRqFactory.validUser().toBuilder().money(BigDecimal.valueOf(10000000)).build();
        Integer sellerID = userAdapter.createUserAndGetId(seller,token);

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        int carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        carSteps.buyNewCar(sellerID, carID);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(carSteps.isCarBought(sellerID, carID)).isTrue();

        carSteps.sellNewCar(sellerID, carID);
        softly.assertThat(carSteps.checkStatusCode()).isEqualTo(200);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("Множественная продажа автомобилей одним пользователем")
    @Description("Тест проверяет продажу нескольких автомобилей одним пользователем")
    public void multipleSalesByUser(int carCount) {
        UserRq seller = UserRqFactory.validUser()
                .toBuilder()
                .money(BigDecimal.valueOf(100000000))
                .build();
        Integer sellerID = userAdapter.createUserAndGetId(seller, token);
        createdUserIds.add(sellerID);

        for (int i = 0; i < carCount; i++) {
            Car car = Car.builder().build();
            carSteps.createNewCar(car);
            int carID = carSteps.checkCreateCarAndGetId();
            createdCarIds.add(carID);

            carSteps.buyNewCar(sellerID, carID);
            carSteps.sellNewCar(sellerID, carID);

            softly.assertThat(carSteps.checkStatusCode()).isEqualTo(200);
        }
    }

    @Test
    @DisplayName("Ошибка при продаже с недействительным ID пользователя")
    @Description("Тест проверяет попытку продажи с недействительным ID пользователя")
    public void sellCarWithInvalidUserId() {
        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        int carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        int invalidUserID = 999999999;
        carSteps.sellNewCar(invalidUserID, carID);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(carSteps.checkStatusCode()).isNotEqualTo(200);
        softly.assertAll();
    }
}