package ui.pages;

import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class AllCarsPage extends BasePage {

    private final String tableName = "Read all cars";
    Table table = new Table(tableName);

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
}