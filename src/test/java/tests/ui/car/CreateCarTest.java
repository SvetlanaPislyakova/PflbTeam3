package tests.ui.car;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTest;
import ui.dto.Car;
import ui.dto.User;
import ui.pages.BuyOrSaleCarPage;

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
        buyOrSaleCarPage.openPage();
        sleep(5000);
        buyOrSaleCarPage.setData();
        sleep(5000);
    }


    @Test
    @DisplayName("Покупка нового автомобиля")
    public void buyNewCar() {
        User user = User.builder().build();
        userSteps.createNewUser(user);
        Long userID = userSteps.checkCreateUserAndGetId();
        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        sleep(5000);
        //addMoneyPage.openPage()
                       // .addMoneyToUser(userID,20000000);
        carSteps.buyNewCar();
        sleep(5000);
    }
}