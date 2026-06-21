package ui.steps;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.pages.CreateUserPage;
import ui.pages.LoginPage;
import ui.pages.MainPage;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final CreateUserPage createUserPage = new CreateUserPage();

    @Step("Авторизация с email '{email}' и password '{password}'")
    public LoginSteps login(String email, String password) {
        loginPage.openPage()
                .isPageOpened()
                .fillEmailInput(email)
                .fillPasswordInput(password)
                .goBtnClick();
        return this;
    }

    @Step("Принять алерт '{message}'")
    public LoginSteps acceptAlert(String message) {
        log.info("Принять алерт '{}'", message);
        assertThat(loginPage.getAlertMsg()).isEqualTo(message);
        loginPage.acceptAlert();
        return this;
    }

    @Step("Отклонить алерт '{message}'")
    public LoginSteps rejectAlert(String message) {
        log.info("Отклонить алерт '{}'", message);
        assertThat(loginPage.getAlertMsg()).isEqualTo(message);
        loginPage.rejectAlert();
        return this;
    }

    @Step("Проверить успешность авторизации")
    public LoginSteps checkSuccessLogin() {
        log.info("Проверить успешность авторизации");
        createUserPage.openPage()
                        .isPageOpened();
        return this;
    }

    @Step("Проверить неуспешность авторизации")
    public LoginSteps checkNegativeLogin() {
        log.info("Проверить неуспешность авторизации");
        createUserPage.openPage();
        loginPage.isPageOpened();
        return this;
    }

    @Step("Проверка сообщения о некорректном вводе email")
    public LoginSteps checkErrorEmailMsg(String email, String message) {
        loginPage.openPage()
                .isPageOpened()
                .fillEmailInput(email);
        log.info("Проверить сообщение о невалидном email '{}'", message);
        assertThat(loginPage.getErrorEmailMessage()).isEqualTo(message);
        loginPage.goBtnClick();
        return this;
    }

    @Step("Проверка сообщения о некорректном вводе password")
    public LoginSteps checkErrorPasswordMsg(String password, String message) {
        loginPage.openPage()
                .isPageOpened()
                .fillPasswordInput(password);
        log.info("Проверить сообщение о невалидном password '{}'", message);
        assertThat(loginPage.getErrorPasswordMessage()).isEqualTo(message);
        loginPage.goBtnClick();
        return this;
    }
}
