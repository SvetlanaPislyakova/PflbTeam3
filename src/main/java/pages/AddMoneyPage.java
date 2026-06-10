package pages;

import wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class AddMoneyPage extends BasePage {

    private final String tableName = "Add money";

    @Override
    public AddMoneyPage openPage() {
        open(baseUrl + "#/update/users/plusMoney");
        return this;
    }

    @Override
    public AddMoneyPage isPageOpened() {
        Table table = new Table(tableName);
        table.checkTableVisible();
        return this;
    }

    public AddMoneyPage addMoneyToUser(int userId, double count) {
        Table table = new Table("Add money");
        table.setValueToInput("User ID", String.valueOf(userId));
        table.setValueToInput("Money", String.valueOf(count));
        table.clickPushToApi();
        return this;
    }

    public double getUserMoney() {
        Table table = new Table(tableName);
        return table.getResultDouble();
    }

    public String getStatusMessage () {
        Table table = new Table(tableName);
        return table.getMessagePushToApi();
    }

    public int getStatusCode () {
        Table table = new Table(tableName);
        return table.getStatus();
    }
}
