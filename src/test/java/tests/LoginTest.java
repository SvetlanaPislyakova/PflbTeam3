package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest{

    @Test
    @DisplayName("Успешная авторизация")
    public void successLogin() {
        loginSteps.login(email, password)
                .acceptAlert()
                .checkSuccessLogin();
    }
}