package ui.pages;

import ui.steps.DBSteps;
import ui.wrappers.Table;

import java.util.List;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class AllCarsPage extends BasePage {

    private final String tableName = "Read all cars";
    Table table = new Table(tableName);
    private final DBSteps dbSteps = new DBSteps();

    @Override
    public AllCarsPage openPage() {
        open(baseUrl + "#/read/cars");
        return this;
    }

    @Override
    public AllCarsPage isPageOpened() {
        table.checkTableVisible();
        return this;
    }

    private List<String> getListFromDb(String field) {
        if (field.equals("Mark"))
            return dbSteps.getListFromDB("car", "mark");
        else if(field.equals("Model"))
            return dbSteps.getListFromDB("car", "model");
        return null;
    }

    public AllCarsPage checkSortCars(String field, boolean isNumeric) {
        List<String> startList = table.getListOfValues(field);
        checkSortObjectNaturalOrder(table, startList, field, isNumeric);
        checkSortObjectReverseOrder(table, startList, field, isNumeric);
        return this;
    }

    public AllCarsPage checkSortCarsByText(String field, boolean isNumeric) {
        List<String> startList = getListFromDb(field);
        sleep(5000);
        checkSortObjectNaturalOrder(table, startList, field, isNumeric);
        checkSortObjectReverseOrder(table, startList, field, isNumeric);
        return this;
    }
}