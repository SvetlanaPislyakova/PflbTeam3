package ui.pages;

import lombok.extern.log4j.Log4j2;
import ui.dto.User;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class CreateUserPage extends BasePage {

    private final String tableName = "Create new user";
    private final Table table = new Table(tableName);


    @Override
    public CreateUserPage openPage() {
        open(baseUrl + "#/create/user");
        return this;
    }

    @Override
    public CreateUserPage isPageOpened() {
        table.checkTableVisible();
        return this;
    }

    public CreateUserPage createNewUser(User user) {
        table.setValueToInput("First Name", user.getFirstName());
        table.setValueToInput("Last Name", user.getLastName());
        table.setValueToInput("Age", String.valueOf(user.getAge()));
        table.setValueToInput("Money", String.valueOf(user.getMoney()));
        $x(String.format("//input[@value='%s']", user.getSex())).click();
        table.clickPushToApiBtn();
        return this;
    }

    public String getStatusMessage() {
        return table.getMessagePushToApi();
    }

    public int getStatusCode() {
        return table.getStatus();
    }

    public Long getUserId() {
        return table.getResultInt();
    }
}