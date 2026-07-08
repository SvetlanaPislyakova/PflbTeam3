package ui.pages;

import io.qameta.allure.Step;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class SettleOrEvictPage extends BasePage {

    private final Table settleOrEvictUserTable = new Table("Settle to house");

    @Override
    @Step("Открытие страницы Settle or evict")
    public SettleOrEvictPage openPage() {
        open(baseUrl + "#/update/houseAndUser");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы Settle or evict")
    public SettleOrEvictPage isPageOpened() {
        settleOrEvictUserTable.checkTableVisible();
        return this;
    }
}
