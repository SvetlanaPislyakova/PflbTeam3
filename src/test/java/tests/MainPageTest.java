package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainPageTest extends BaseTest {

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    public void checkDropDownUsers() {
        mainPage.openPageCreateUser()
                .isPageOpened();
        mainPage.openPageAddMoney()
                .isPageOpened();
        mainPage.openPageAllUsers()
                .isPageOpened();
    }
}
