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

import static org.junit.jupiter.api.Assertions.*;

public class BuyCarTest extends BaseTest {

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Успешная покупка автомобиля с достаточными средствами")
    @Description("Тест проверяет покупку автомобиля с достаточными средствами")
    public void buyCarWithSufficientMoney() {
        UserRq buyer = UserRqFactory.validUser().toBuilder().money(BigDecimal.valueOf(10000000)).build();
        Integer buyerID = userAdapter.createUserAndGetId(buyer,token);

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        int carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        carSteps.buyNewCar(buyerID, carID);
        assertTrue(carSteps.isCarBought(buyerID, carID));
    }

    @Test
    @DisplayName("Ошибка при покупке с недостаточными средствами")
    @Description("Тест проверяет покупку автомобиля с недостаточными средствами")
    public void buyCarWithInsufficientMoney() {
        UserRq buyer = UserRqFactory.validUser().toBuilder().money(BigDecimal.valueOf(100)).build();
        Integer buyerID = userAdapter.createUserAndGetId(buyer,token);

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        int carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        carSteps.buyNewCar(buyerID, carID);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(carSteps.checkStatusCode()).isEqualTo(404);
        softly.assertThat(buyOrSaleCarPage.getStatusMessage()).contains("Status: AxiosError: Request failed with status code 404");
    }

    @Test
    @DisplayName("Ошибка при попытке купить несуществующий автомобиль")
    @Description("Тест проверяет возможность покупки несуществующего автомобиля")
    public void buyNonExistentCar() {
        UserRq buyer = UserRqFactory.validUser().toBuilder().money(BigDecimal.valueOf(10000000)).build();
        Integer buyerID = userAdapter.createUserAndGetId(buyer,token);

        int nonExistentCarID = 999999999;

        carSteps.buyNewCar(buyerID, nonExistentCarID);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(carSteps.checkStatusCode()).isEqualTo(404);
        softly.assertThat(buyOrSaleCarPage.getStatusMessage()).contains("Status: AxiosError: Request failed with status code 404");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("Множественные покупки одним пользователем")
    public void multiplePurchasesByUser(int carCount) {
        UserRq buyer = UserRqFactory.validUser().toBuilder().money(BigDecimal.valueOf(10000000)).build();
        Integer buyerID = userAdapter.createUserAndGetId(buyer,token);

        for (int i = 0; i < carCount; i++) {
            Car car = Car.builder().build();
            carSteps.createNewCar(car);
            int carID = carSteps.checkCreateCarAndGetId();
            carSteps.buyNewCar(buyerID,carID);
            createdCarIds.add(carID);
            assertTrue(carSteps.isCarBought(buyerID, carID));
        }

    }

    @Test
    @DisplayName("Проверка статуса после покупки")
    @Description("Тест проверяет множественную покупку автомобиля и сверяет статус код в каждой итерацииёё")
    public void checkStatusAfterPurchase() {
        UserRq buyer = UserRqFactory.validUser().toBuilder().money(BigDecimal.valueOf(10000000)).build();
        Integer buyerID = userAdapter.createUserAndGetId(buyer,token);

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        int carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);
        carSteps.buyNewCar(buyerID, carID);

        assertEquals(200, carSteps.checkStatusCode());
    }
}
