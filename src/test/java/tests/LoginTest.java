package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import wrappers.DropDown;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest extends BaseTest{

    @Test
    @DisplayName("Успешная авторизация с корректными кредами")
    public void successLogin() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization")
                .checkSuccessLogin();
    }

    @Test
    @DisplayName("Негативная авторизация с корректными кредами")
    public void negativeLogin() {
        loginSteps.login(email, password)
                .rejectAlert("Successful authorization")
                .checkNegativeLogin();
    }

    static Stream<Arguments> negativeCreds() {
        return Stream.of(
                Arguments.of("", password),
                Arguments.of(email, ""),
                Arguments.of("test", "test")
        );
    }

    @ParameterizedTest(name = "Негативный логин c email: {0}, password: {1}")
    @MethodSource("negativeCreds")
    @DisplayName("Негативная авторизация с некорректными кредами")
    public void negativeLoginWith(String login, String pass) {
        loginSteps.login(login, pass)
                .acceptAlert("Incorrect input data")
                .checkNegativeLogin();
    }







    @Test
    public void createUser() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        new DropDown("Users").selectOption("Create new");
        loginSteps.createUser();
    }

    @Test
    public void createUserFromAllPost() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        open("http://82.142.167.37:4881/#/create/all");
        loginSteps.createUser();
    }

    // с машинами 400, пока не поняла почему, но кнопка нажимается и сообщение читается
    @Test
    public void createCar() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        new DropDown("Cars").selectOption("Create new");
        loginSteps.createCar();
    }

    @Test
    public void createCarFromAllPost() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        open("http://82.142.167.37:4881/#/create/all");
        loginSteps.createCar();
    }

    @Test
    public void addMoney() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        open("http://82.142.167.37:4881/#/update/users/plusMoney");
        loginSteps.addMoney();
    }

    @Test
    public void addMoneyFromAllPost() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        open("http://82.142.167.37:4881/#/create/all");
        loginSteps.addMoney();
    }

    @Test
    public void checkSorting() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        open("http://82.142.167.37:4881/#/read/users");
        loginSteps.checkSort("ID");
    }

    @Test
    public void checkSortingCar() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        open("http://82.142.167.37:4881/#/read/cars");
        loginSteps.checkSortCar("Engine");
    }
}
