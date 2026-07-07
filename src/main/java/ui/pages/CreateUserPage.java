package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.dto.User;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class CreateUserPage extends BasePage {
    private final String RADIO_BTN = "//input[@value='%s']";
    private final String tableName = "Create new user";
    private final Table table = new Table(tableName);

    @Override
    @Step("Открытие страницы создания пользователя")
    public CreateUserPage openPage() {
        log.info("Открыть страницу '{}'", tableName);
        open(baseUrl + "#/create/user");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы создания пользователя")
    public CreateUserPage isPageOpened() {
        log.info("Проверить, что страница '{}' открыта", tableName);
        table.checkTableVisible();
        return this;
    }

    @Step("Создание нового пользователя")
    public CreateUserPage createNewUser(User user) {
        table.setValueToInput("First Name", user.getFirstName());
        table.setValueToInput("Last Name", user.getLastName());
        table.setValueToInput("Age", String.valueOf(user.getAge()));
        table.setValueToInput("Money", String.valueOf(user.getMoney()));
        if (user.getSex() != null) $x(String.format(RADIO_BTN, user.getSex())).click();
        table.clickPushToApiBtn();
        return this;
    }

    @Step("Получение сообщения о статусе операции")
    public String getStatusMessage() {
        return table.getMessagePushToApi();
    }

    @Step("Получение кода статуса")
    public Integer getStatusCode() {
        return table.getStatus();
    }

    @Step("Получение ID созданного пользователя")
    public Integer getUserId() {
        return table.getResultInt();
    }
}
