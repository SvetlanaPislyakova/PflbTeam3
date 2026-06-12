package ui.pages;

import ui.dto.User;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

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
        table.setValueToInput("Age", user.getAge());
        table.setValueToInput("Money", user.getMoney());
        $x("//*[text()='FEMALE']/input").click();
        table.clickPushToApi();
        return this;
    }

    public String getStatusMessage() {
        return table.getMessagePushToApi();
    }

    public int getStatusCode() {
        return table.getStatus();
    }

    public int getUserId() {
        return table.getResultInt();
    }
}
