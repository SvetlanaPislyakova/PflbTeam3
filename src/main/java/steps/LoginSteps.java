package steps;

import io.qameta.allure.Step;
import pages.LoginPage;
import pages.MainPage;
import wrappers.DropDown;
import wrappers.Input;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.switchTo;


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

    public LoginSteps checkInput() {
        new DropDown("Users").selectOption("Create new");
        sleep(2000);
        new Input("Create new user", "First Name").write("Kotik");
        new Input("Create new user", "Last Name").write("Obormotik");
        new Input("Create new user", "Age").write("12");
        new Input("Create new user", "Money").write("250000");
        sleep(2000);
        new DropDown("Users").selectOption("Add money");
        new Input("Add money", "User ID").write("13000");
        new Input("Add money", "Money").write("333000");
        sleep(2000);
        new DropDown("Cars").selectOption("Create new");
        new Input("Create new car", "Engine Type").write("Engine Type");
        new Input("Create new car", "Mark").write("Mark");
        new Input("Create new car", "Model").write("Model");
        new Input("Create new car", "Price").write("20000");
        sleep(2000);
        $(byText("All POST")).click();
        switchTo().window(1);
        sleep(2000);
        new Input("Create new user", "First Name").write("Name");
        new Input("Create new user", "Last Name").write("Obormotik");
        new Input("Create new user", "Age").write("12");
        new Input("Create new user", "Money").write("250000");
        sleep(2000);
        return this;
    }
}
