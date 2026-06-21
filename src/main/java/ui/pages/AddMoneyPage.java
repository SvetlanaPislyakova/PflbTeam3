package ui.pages;

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
    public AddMoneyPage openPage() {
        log.info("Открыть страницу '{}'", tableName);
        open(baseUrl + "#/update/users/plusMoney");
        return this;
    }

    @Override
    public AddMoneyPage isPageOpened() {
        log.info("Проверить, что страница '{}' открыта", tableName);
        table.checkTableVisible();
        return this;
    }

    public AddMoneyPage addMoneyToUser(Integer userId, BigDecimal count) {
        table.setValueToInput("User ID", String.valueOf(userId));
        table.setValueToInput("Money", String.valueOf(count));
        table.clickPushToApiBtn();
        return this;
    }

    public BigDecimal getUserMoney() {
        return BigDecimal.valueOf(table.getResultDouble());
    }

    public String getStatusMessage () {
        return table.getMessagePushToApi();
    }

    public int getStatusCode () {
        return table.getStatus();
    }
}
