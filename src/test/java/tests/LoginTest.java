package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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
                Arguments.of("test@test.com", password),
                Arguments.of(email, "test"),
                Arguments.of("test@test.com", "test")
        );
    }

    @ParameterizedTest(name = "Негативный логин c email: {0}, password: {1}")
    @MethodSource("negativeCreds")
    @DisplayName("Негативная авторизация с некорректными кредами")
    public void negativeLoginWith(String login, String pass) {
        loginSteps.login(login, pass)
                .acceptAlert("Bad request")
                .checkNegativeLogin();
    }


    @ParameterizedTest(name = "Ввод невалидных данных в поле 'email': {0}")
    @ValueSource(strings = {"test", "test.com"})
    @DisplayName("Ввод невалидных данных в поле 'email'")
    public void validateEmailInput(String email) {
        String errorMsg = "incorrect Email";
        loginSteps.checkErrorEmailMsg(email, errorMsg)
                .acceptAlert("Incorrect input data");
    }

    @ParameterizedTest(name = "Ввод невалидных данных в поле 'password': {0}")
    @ValueSource(strings = {"ok", "loremipsum"})
    @DisplayName("Ввод невалидных данных в поле 'email'")
    public void validatePasswordInput(String password) {
        String errorMsg = "password length must be more than 3 symbols and less than 8 symbols";
        loginSteps.checkErrorPasswordMsg(password, errorMsg)
                .acceptAlert("Incorrect input data");
    }
}
