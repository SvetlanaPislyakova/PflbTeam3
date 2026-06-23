package tests.ui.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tests.BaseTest;
import ui.dto.Car;
import ui.dto.User;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BuyOrSaleTest extends BaseTest {
    private static final BigDecimal SUFFICIENT_MONEY = BigDecimal.valueOf(10000000);
    private static final BigDecimal INSUFFICIENT_MONEY = BigDecimal.valueOf(100);

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Успешная покупка автомобиля с достаточными средствами")
    public void buyCarWithSufficientMoney() {
        User buyer = User.builder().build();
        userSteps.createNewUser(buyer);
        Integer buyerID = userSteps.checkCreateUserAndGetId();

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        Long carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        addMoneyPage.openPage()
                .addMoneyToUser(buyerID, SUFFICIENT_MONEY);

        carSteps.buyNewCar(buyerID.longValue(), carID);
        assertTrue(carSteps.isCarBought(buyerID.longValue(), carID),
                "Автомобиль должен быть успешно куплен");
    }

    @Test
    @DisplayName("Ошибка при покупке с недостаточными средствами")
    public void buyCarWithInsufficientMoney() {
        User buyer = User.builder().build();
        userSteps.createNewUser(buyer);
        Integer buyerID = userSteps.checkCreateUserAndGetId();

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        Long carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        addMoneyPage.openPage()
                .addMoneyToUser(buyerID, INSUFFICIENT_MONEY);

        assertThrows(Exception.class, () ->
                carSteps.buyNewCar(buyerID.longValue(), carID),
                "Должна быть ошибка при недостаточных средствах");
    }

    @Test
    @DisplayName("Ошибка при попытке купить несуществующий автомобиль")
    public void buyNonExistentCar() {
        User buyer = User.builder().build();
        userSteps.createNewUser(buyer);
        Integer buyerID = userSteps.checkCreateUserAndGetId();

        long nonExistentCarID = 999999L;

        addMoneyPage.openPage()
                .addMoneyToUser(buyerID, SUFFICIENT_MONEY);

        assertThrows(Exception.class, () ->
                carSteps.buyNewCar(buyerID.longValue(), nonExistentCarID),
                "Должна быть ошибка при покупке несуществующего автомобиля");
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    @DisplayName("Множественные покупки одним пользователем")
    public void multiplePurchasesByUser(Long carCount) {
        User buyer = User.builder().build();
        userSteps.createNewUser(buyer);
        Integer buyerID = userSteps.checkCreateUserAndGetId();

        addMoneyPage.openPage()
                .addMoneyToUser(buyerID, BigDecimal.valueOf(100000000));

        for (int i = 0; i < carCount; i++) {
            Car car = Car.builder().build();
            carSteps.createNewCar(car);
            Long carID = carSteps.checkCreateCarAndGetId();
            carSteps.buyNewCar(buyerID.longValue(), carID);
            createdCarIds.add(carID);
        }

        assertTrue(true, "Множественные покупки должны быть успешными");
    }

    @Test
    @DisplayName("Одиночная продажа автомобиля")
    public void sellCar() {
        User seller = User.builder().build();
        userSteps.createNewUser(seller);
        Integer sellerID = userSteps.checkCreateUserAndGetId();

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        Long carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        addMoneyPage.openPage()
                .addMoneyToUser(sellerID, SUFFICIENT_MONEY);

        carSteps.buyNewCar(sellerID.longValue(), carID);
        assertTrue(carSteps.isCarBought(sellerID.longValue(), carID),
                "Автомобиль должен быть куплен для последующей продажи");
    }

    @Test
    @DisplayName("Проверка статуса после покупки")
    public void checkStatusAfterPurchase() {
        User buyer = User.builder().build();
        userSteps.createNewUser(buyer);
        Integer buyerID = userSteps.checkCreateUserAndGetId();

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        Long carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        addMoneyPage.openPage()
                .addMoneyToUser(buyerID, SUFFICIENT_MONEY);

        carSteps.buyNewCar(buyerID.longValue(), carID);

        assertEquals(200, carSteps.checkBuyStatusCode(),
                "Статус должен быть 200 OK");
    }
}
