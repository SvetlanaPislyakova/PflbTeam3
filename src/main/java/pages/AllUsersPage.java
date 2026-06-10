package pages;

import wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class AllUsersPage extends BasePage {

    private final String tableName = "Read all users";

    @Override
    public AllUsersPage openPage() {
        open(baseUrl + "#/read/users");
        return this;
    }

    @Override
    public AllUsersPage isPageOpened() {
        Table table = new Table(tableName);
        table.checkTableVisible();
        return this;
    }

    public AllUsersPage checkSortUsers(String field, boolean isNumeric) {
        checkSortObject(tableName, field, isNumeric);
        return this;
    }
}
