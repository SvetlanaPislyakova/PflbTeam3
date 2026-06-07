package steps;

import io.qameta.allure.Step;
import pages.LoginPage;
import pages.MainPage;
import wrappers.Input;

import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();

    @Step("Авторизация с email '{email}' и password '{password}'")
    public LoginSteps login(String email, String password) {
        loginPage.openPage()
                .isPageOpened()
                .login(email, password);
        return this;
    }

    public LoginSteps acceptAlert() {
        assertThat(loginPage.getAlertMsg()).isEqualTo("Successful authorization");
        loginPage.acceptAlert();
        return this;
    }

    public LoginSteps rejectAlert() {
        assertThat(loginPage.getAlertMsg()).isEqualTo("Successful authorization");
        loginPage.rejectAlert();
        return this;
    }

    public LoginSteps checkSuccessLogin() {
        mainPage.openPageCreateUser();
        return this;
    }
}
