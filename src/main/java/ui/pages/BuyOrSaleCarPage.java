package ui.pages;

import ui.wrappers.Radio;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class BuyOrSaleCarPage extends BasePage {

    private final String tableName = "Buy or sell car";
    Table table = new Table(tableName);
    Radio radio = new Radio(tableName);

    @Override
    public BuyOrSaleCarPage openPage() {
        open(baseUrl + "#/update/users/buyCar");
        return this;
    }

    @Override
    public BuyOrSaleCarPage isPageOpened() {
        table.checkTableVisible();
        return this;
    }

    public BuyOrSaleCarPage requestALoan() {
        table.setValueToInput("User ID", "13004");
        table.setValueToInput("Car Id", "8883");
        table.clickPushToApiBtn();
        return this;
    }

    public String getStatusMessage() {
        return table.getMessagePushToApi();
    }

    public int getStatusCode() {
        return table.getStatus();
    }

}