package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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
    public void checkInput() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization")
                .checkInput();
    }

}