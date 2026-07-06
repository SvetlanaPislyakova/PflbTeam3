package tests.api.car;

import api.adapters.CarAdapter;
import api.adapters.UserAdapter;
import api.models.car.CarRq;
import api.models.car.CarRqFactory;
import api.models.car.CarRs;
import api.models.user.UserInfoRs;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;


public class SellCarApiTest {

    private final UserAdapter userAdapter = new UserAdapter();
    private final CarAdapter carAdapter = new CarAdapter();
    SoftAssertions softly = new SoftAssertions();

    @Test
    @Owner("Akhunov Gayaz")
    @DisplayName("API - Продажа автомобиля: проверка удаления авто из имущества пользователя")
    @Description("Создаём пользователя, создаём авто, покупаем авто, продаём авто и проверяем, что авто больше не в списке пользователя")
    void sellCarTest() {
        UserRq userRq = UserRqFactory
                .validUser()
                .toBuilder()
                .money(BigDecimal.valueOf(100000))
                .build();

        Integer userId = userAdapter.createUserAndGetId(userRq);

        CarRq carRq = CarRqFactory
                .validCar()
                .toBuilder()
                .price(BigDecimal.valueOf(15000))
                .build();

        CarRs carRs = carAdapter.createCar(carRq);
        Integer carId = carRs.getId();

        userAdapter.buyCar(userId, carId);

        UserInfoRs infoBeforeSell = userAdapter.getUserInfo(userId);
        softly.assertThat(infoBeforeSell.getCars()).isNotEmpty();

        userAdapter.sellCar(userId, carId);

        UserInfoRs infoAfterSell = userAdapter.getUserInfo(userId);
        List<CarRs> carsAfterSell = infoAfterSell.getCars();
        softly.assertThat(carsAfterSell)
                .as("Массив 'cars' должен быть пустым после успешной продажи автомобиля")
                .isEmpty();
        softly.assertAll();
    }

    @Test
    @Owner("Akhunov Gayaz")
    @DisplayName("API - Продажа автомобиля: проверка восстановления баланса после продажи")
    @Description("Создаём пользователя с известным балансом, создаём авто, покупаем и продаём; проверяем баланс")
    void sellCarCheckBalanceTest() {
        BigDecimal initialBalance = BigDecimal.valueOf(50000);

        UserRq userRq = UserRqFactory
                .validUser()
                .toBuilder()
                .money(initialBalance)
                .build();

        Integer userId = userAdapter.createUserAndGetId(userRq);

        BigDecimal carPrice = BigDecimal.valueOf(12500.50);
        CarRq carRq = CarRqFactory
                .validCar()
                .toBuilder()
                .price(carPrice)
                .build();

        CarRs carRs = carAdapter.createCar(carRq);
        Integer carId = carRs.getId();

        userAdapter.buyCar(userId, carId);

        UserInfoRs infoAfterBuy = userAdapter.getUserInfo(userId);
        BigDecimal expectedAfterBuy = initialBalance.subtract(carPrice);
        softly.assertThat(infoAfterBuy.getMoney()).isEqualByComparingTo(expectedAfterBuy);

        userAdapter.sellCar(userId, carId);

        UserInfoRs infoAfterSell = userAdapter.getUserInfo(userId);
        softly.assertThat(infoAfterSell.getMoney()).isEqualByComparingTo(initialBalance);
        softly.assertAll();
    }
}