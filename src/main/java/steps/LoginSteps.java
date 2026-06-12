package steps;

import io.qameta.allure.Step;
import pages.LoginPage;
import pages.MainPage;

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

    @Step("Принять алерт '{message}'")
    public LoginSteps acceptAlert(String message) {
        assertThat(loginPage.getAlertMsg()).isEqualTo(message);
        loginPage.acceptAlert();
        return this;
    }

    @Step("Отклонить алерт '{message}'")
    public LoginSteps rejectAlert(String message) {
        assertThat(loginPage.getAlertMsg()).isEqualTo(message);
        loginPage.rejectAlert();
        return this;
    }

    @Step("Проверить успешность авторизации")
    public LoginSteps checkSuccessLogin() {
        mainPage.openPageCreateUser()
                .isPageOpened();
        return this;
    }

    @Step("Проверить неуспешность авторизации")
    public LoginSteps checkNegativeLogin() {
        mainPage.openPageCreateUser();
        loginPage.isPageOpened();
        return this;
    }
}
