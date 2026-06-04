package tests;

import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest{

    @Test
    public void successLogin() {
        loginSteps.login(email, password)
                .acceptAlert();
    }
}
