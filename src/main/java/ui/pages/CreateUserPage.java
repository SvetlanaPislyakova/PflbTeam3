package ui.pages;

import lombok.extern.log4j.Log4j2;
import ui.dto.User;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class CreateUserPage extends BasePage {

    private final String RADIO_BTN = "//input[@value='%s']";
    private final String tableName = "Create new user";
    private final Table table = new Table(tableName);


    @Override
    public CreateUserPage openPage() {
        log.info("Открыть страницу '{}'", tableName);
        open(baseUrl + "#/create/user");
        return this;
    }

    @Override
    public CreateUserPage isPageOpened() {
        log.info("Проверить, что страница '{}' открыта", tableName);
        table.checkTableVisible();
        return this;
    }

    public CreateUserPage createNewUser(User user) {
        table.setValueToInput("First Name", user.getFirstName());
        table.setValueToInput("Last Name", user.getLastName());
        table.setValueToInput("Age", String.valueOf(user.getAge()));
        table.setValueToInput("Money", String.valueOf(user.getMoney()));
        $x(String.format(RADIO_BTN, user.getSex())).click();
        table.clickPushToApiBtn();
        return this;
    }

    public String getStatusMessage() {
        return table.getMessagePushToApi();
    }

    public Integer getStatusCode() {
        return table.getStatus();
    }

    public Integer getUserId() {
        return table.getResultInt();
    }
}
