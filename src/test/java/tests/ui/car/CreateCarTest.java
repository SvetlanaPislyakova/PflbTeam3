package tests.ui.car;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTest;
import ui.dto.Car;
import ui.dto.User;
import ui.dto.UserFactory;
import ui.pages.BuyOrSaleCarPage;

import java.math.BigDecimal;

import static com.codeborne.selenide.Selenide.*;

public class CreateCarTest extends BaseTest {
    private final Faker faker = new Faker();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Создание нового автомобиля")
    public void createCar() {
        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        sleep(5000);
    }


    @Test
    @DisplayName("Покупка нового автомобиля")
    public void buyNewCar() {
        User user = UserFactory.validUser();
        userSteps.createNewUser(user);
        Integer userID = userSteps.checkCreateUserAndGetId();
        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        Long carID = carSteps.checkCreateCarAndGetId();
        sleep(5000);
        addMoneyPage.openPage()
                       .addMoneyToUser(userID, BigDecimal.valueOf(20000000));
        carSteps.buyNewCar(userID.longValue(),carID);
        sleep(5000);
    }
}
