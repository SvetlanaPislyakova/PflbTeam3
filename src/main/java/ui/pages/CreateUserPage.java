package ui.pages;

import ui.dto.User;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

public class CreateUserPage extends BasePage {

    private final String tableName = "Create new user";

    @Override
    public CreateUserPage openPage() {
        open(baseUrl + "#/create/user");
        return this;
    }

    @Override
    public CreateUserPage isPageOpened() {
        Table table = new Table(tableName);
        table.checkTableVisible();
        return this;
    }

    public CreateUserPage createNewUser(User user) {
        Table table = new Table(tableName);
        table.setValueToInput("First Name", user.getFirstName());
        table.setValueToInput("Last Name", user.getLastName());
        table.setValueToInput("Age", user.getAge());
        table.setValueToInput("Money", user.getMoney());
        $x("//*[text()='FEMALE']/input").click();
        table.clickPushToApi();
        return this;
    }

    public String getStatusMessage() {
        Table table = new Table(tableName);
        return table.getMessagePushToApi();
    }

    public int getStatusCode() {
        Table table = new Table(tableName);
        return table.getStatus();
    }

    public int getUserId() {
        Table table = new Table(tableName);
        return table.getResultInt();
    }
}
