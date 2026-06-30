package ui.pages;

import lombok.extern.log4j.Log4j2;
import ui.steps.DBSteps;
import ui.wrappers.Table;

import java.util.List;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

@Log4j2
public class AllUsersPage extends BasePage {

    private final String tableName = "Read all users";
    Table table = new Table(tableName);
    private final DBSteps dbSteps = new DBSteps();


    @Override
    public AllUsersPage openPage() {
        log.info("Открыть страницу '{}'", tableName);
        open(baseUrl + "#/read/users");
        return this;
    }

    @Override
    public AllUsersPage isPageOpened() {
        log.info("Проверить, что страница '{}' открыта", tableName);
        table.checkTableVisible();
        return this;
    }

    private List<String> getListFromDb(String field) {
        if (field.equals("First name"))
            return dbSteps.getListFromDB("person", "first_name");
        else if(field.equals("Last name"))
            return dbSteps.getListFromDB("person", "second_name");
        return null;
    }

    public AllUsersPage checkSortUsers(String field, boolean isNumeric) {
        List<String> startList = table.getListOfValues(field);
        checkSortObjectNaturalOrder(table, startList, field, isNumeric);
        checkSortObjectReverseOrder(table, startList, field, isNumeric);
        return this;
    }

    public AllUsersPage checkSortUsersByText(String field, boolean isNumeric) {
        List<String> startList = getListFromDb(field);
        sleep(5000);
        checkSortObjectNaturalOrder(table, startList, field, isNumeric);
        checkSortObjectReverseOrder(table, startList, field, isNumeric);
        return this;
    }
}