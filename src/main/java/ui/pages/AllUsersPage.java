package ui.pages;

import ui.wrappers.Table;

import java.util.List;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class AllUsersPage extends BasePage {

    private final String tableName = "Read all users";
    Table table = new Table(tableName);

    @Override
    public AllUsersPage openPage() {
        open(baseUrl + "#/read/users");
        return this;
    }

    @Override
    public AllUsersPage isPageOpened() {
        table.checkTableVisible();
        return this;
    }

    public AllUsersPage checkSortUsers(String field, boolean isNumeric) {
        checkSortObject(table, field, isNumeric);
        return this;
    }

    public List<String> getListValues(String field) {
        return table.getListOfValues(field);
    }
}