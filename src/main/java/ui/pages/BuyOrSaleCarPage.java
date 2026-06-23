package ui.pages;

import ui.wrappers.Radio;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class BuyOrSaleCarPage extends BasePage {
    private static final String TABLE_NAME = "Buy or sell car";
    private static final String USER_ID_FIELD = "User ID";
    private static final String CAR_ID_FIELD = "Car Id";
    private static final String BUY_OPTION = "BUY";

    private final Table table = new Table(TABLE_NAME);
    private final Radio radio = new Radio(BUY_OPTION);

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

    public BuyOrSaleCarPage setData(Long userID, Long carID) {
        table.setValueToInput(USER_ID_FIELD, userID.toString());
        table.setValueToInput(CAR_ID_FIELD, carID.toString());
        radio.select();
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