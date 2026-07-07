package ui.pages;

import io.qameta.allure.Step;
import ui.steps.DBSteps;
import ui.wrappers.Table;

import java.util.List;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class AllCarsPage extends BasePage {
    private final String tableName = "Read all cars";
    private final DBSteps dbSteps = new DBSteps();
    Table table = new Table(tableName);

    @Override
    @Step("Открытие страницы read/cars")
    public AllCarsPage openPage() {
        open(baseUrl + "#/read/cars");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы read/cars")
    public AllCarsPage isPageOpened() {
        table.checkTableVisible();
        return this;
    }

    @Step("Получение списка из базы данных")
    private List<String> getListFromDb(String field) {
        if (field.equals("Mark"))
            return dbSteps.getListFromDB("car", "mark");
        else if (field.equals("Model"))
            return dbSteps.getListFromDB("car", "model");
        return null;
    }

    @Step("Проверка сортировки автомобилей по полю '{field}'")
    public AllCarsPage checkSortCars(String field, boolean isNumeric) {
        List<String> startList = table.getListOfValues(field);
        checkSortObjectNaturalOrder(table, startList, field, isNumeric);
        checkSortObjectReverseOrder(table, startList, field, isNumeric);
        return this;
    }

    @Step("Проверка сортировки автомобилей по полю '{field}' из базы данных")
    public AllCarsPage checkSortCarsByText(String field, boolean isNumeric) {
        List<String> startList = getListFromDb(field);
        sleep(5000);
        checkSortObjectNaturalOrder(table, startList, field, isNumeric);
        checkSortObjectReverseOrder(table, startList, field, isNumeric);
        return this;
    }
}