package steps;

import dto.User;
import io.qameta.allure.Step;
import org.assertj.core.api.AssertionsForClassTypes;
import pages.LoginPage;
import pages.MainPage;
import wrappers.Table;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
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

    public LoginSteps createUser() {
        User user = User.builder().build();
        Table table = new Table("Create new user");
        table.setValueToInput("First Name", user.getFirstName());
        table.setValueToInput("Last Name", user.getLastName());
        table.setValueToInput("Age", user.getAge());
        table.setValueToInput("Money", user.getMoney());
        $x("//*[text()='FEMALE']/input").click(); // Гаяз сделает радиобаттон
        table.clickPushToApi();
        sleep(2000);
        System.out.println(table.getMessagePushToApi());
        System.out.println(table.getResultInt());
        return this;
    }

    public LoginSteps createCar() {
        Table table = new Table("Create new car");
        table.setValueToInput("Engine Type", "Engine");
        table.setValueToInput("Mark", "Mark");
        table.setValueToInput("Model", "Model");
        table.setValueToInput("Price", "300.01");
        table.clickPushToApi();
        sleep(2000);
        System.out.println(table.getMessagePushToApi());
        System.out.println(table.getResultInt());
        return this;
    }

    public LoginSteps addMoney() {
        Table table = new Table("Add money");
        table.setValueToInput("Money", "30000");
        sleep(1000);
        table.setValueToInput("User ID", "13093");
        table.clickPushToApi();
        sleep(2000);
        System.out.println(table.getMessagePushToApi());
        System.out.println(table.getResultDouble());
        return this;
    }

    public LoginSteps checkSort(String label) {
        Table table = new Table("Read all users");
        sleep(2000);
        List<String> first = table.getListOfValues(label);
        System.out.println(first);
        first.sort(Comparator.comparingInt(Integer::parseInt));
        System.out.println(first);
        $x(String.format("//button[contains(text(), '%s')]", label)).click();
        sleep(2000);
        List<String> sorted = table.getListOfValues(label);
        System.out.println(sorted);
        AssertionsForClassTypes.assertThat(first).isEqualTo(sorted);
        $x(String.format("//button[contains(., '%s')]", label)).click();
        sleep(2000);
        List<String> sorted2 = table.getListOfValues(label);
        first.sort(Collections.reverseOrder(Comparator.comparingInt(Integer::parseInt)));
        System.out.println(sorted2);
        System.out.println(first);
        AssertionsForClassTypes.assertThat(first).isEqualTo(sorted2);
        return this;
    }

    public LoginSteps checkSortCar(String label) {
        Table table = new Table("Read all cars");
        sleep(2000);
        List<String> first = table.getListOfValues(label);
        System.out.println(first);
        first.sort(Comparator.naturalOrder());
        System.out.println(first);
        $x(String.format("//button[contains(text(), '%s')]", label)).click();
        sleep(2000);
        List<String> sorted = table.getListOfValues(label);
        System.out.println(sorted);
        AssertionsForClassTypes.assertThat(first).isEqualTo(sorted);
        $x(String.format("//button[contains(., '%s')]", label)).click();
        sleep(2000);
        List<String> sorted2 = table.getListOfValues(label);
        first.sort(Comparator.reverseOrder());
        System.out.println(sorted2);
        System.out.println(first);
        AssertionsForClassTypes.assertThat(first).isEqualTo(sorted2);
        return this;
    }

    public LoginSteps createHouse() {
        Table table = new Table("Create new house");
        table.setValueToInput("Floor", "2");
        table.setValueToInput("Price", "2000");
        sleep(2000);
        table.clickPushToApi();
        sleep(2000);
        System.out.println(table.getMessagePushToApi());
        return this;
    }
}
