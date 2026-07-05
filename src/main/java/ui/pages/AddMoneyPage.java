package ui.pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.wrappers.Table;

import java.math.BigDecimal;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class AddMoneyPage extends BasePage {

    private final String tableName = "Add money";
    Table table = new Table(tableName);

    @Override
    @Step("Открытие страницы 'Add money'")
    public AddMoneyPage openPage() {
        log.info("Открыть страницу '{}'", tableName);
        open(baseUrl + "#/update/users/plusMoney");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы 'Add money'")
    public AddMoneyPage isPageOpened() {
        log.info("Проверить, что страница '{}' открыта", tableName);
        table.checkTableVisible();
        return this;
    }

    @Step("Добавление денег пользователю: userId={userId}, count={count}")
    public AddMoneyPage addMoneyToUser(Integer userId, BigDecimal count) {
        table.setValueToInput("User ID", String.valueOf(userId));
        table.setValueToInput("Money", String.valueOf(count));
        table.clickPushToApiBtn();
        return this;
    }

    @Step("Получение текущих денег пользователя")
    public BigDecimal getUserMoney() {
        return BigDecimal.valueOf(table.getResultDouble());
    }

    @Step("Получение сообщения о статусе операции")
    public String getStatusMessage () {
        return table.getMessagePushToApi();
    }

    @Step("Получение кода статуса")
    public int getStatusCode () {
        return table.getStatus();
    }
}
