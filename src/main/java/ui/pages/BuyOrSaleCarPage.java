package ui.pages;

import io.qameta.allure.Step;
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

    @Override
    @Step("Открытие страницы покупки авто")
    public BuyOrSaleCarPage openPage() {
        open(baseUrl + "#/update/users/buyCar");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы покупки/продажи авто")
    public BuyOrSaleCarPage isPageOpened() {
        table.checkTableVisible();
        return this;
    }

    @Step("Заполнение полей на странице")
    public BuyOrSaleCarPage setData(int userID, int carID, String name) {
        table.setValueToInput("User ID", String.valueOf(userID));
        table.setValueToInput("Car Id", String.valueOf(carID));
        new Radio(name).click();
        table.clickPushToApiBtn();
        return this;
    }

    @Step("Получение сообщения о статусе операции")
    public String getStatusMessage() {
        return table.getMessagePushToApi();
    }

    @Step("Получение кода статуса")
    public int getStatusCode() {
        return table.getStatus();
    }
}
