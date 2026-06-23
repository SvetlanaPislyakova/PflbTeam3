package tests.ui.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTest;
import ui.dto.User;
import ui.dto.Car;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SellCarTest extends BaseTest {
    private static final BigDecimal SUFFICIENT_MONEY = BigDecimal.valueOf(20000000);

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Продажа автомобиля пользователем")
    public void sellCarSuccess() {
        User user = User.builder().build();
        userSteps.createNewUser(user);
        Integer userID = userSteps.checkCreateUserAndGetId();

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        Long carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        addMoneyPage.openPage()
                .addMoneyToUser(userID, SUFFICIENT_MONEY);

        carSteps.buyNewCar(userID.longValue(), carID);
        assertTrue(carSteps.isCarBought(userID.longValue(), carID), "Покупка не удалась");

        carSteps.sellNewCar(userID.longValue(), carID);
        assertEquals(200, carSteps.checkSellStatusCode(), "Статус продажи должен быть 200");
    }
}