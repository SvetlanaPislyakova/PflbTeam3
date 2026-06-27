package ui.pages;

import lombok.extern.log4j.Log4j2;
import ui.wrappers.Table;

import java.math.BigDecimal;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class IssueLoanPage extends BasePage {

    private final String tableName = "Issue a loan";
    Table table = new Table(tableName);

    @Override
    public IssueLoanPage openPage() {
        log.info("Открыть страницу '{}'", tableName);
        open(baseUrl + "/#/update/Issue_A_Loan");
        return this;
    }

    @Override
    public IssueLoanPage isPageOpened() {
        log.info("Проверить, что страница '{}' открыта", tableName);
        table.checkTableVisible();
        return this;
    }

    public IssueLoanPage requestALoan(Integer userId, BigDecimal money) {
        table.setValueToInput("User ID", String.valueOf(userId));
        table.setValueToInput("Размер кредита", String.valueOf(money));
        table.clickIssueLoanBtn();
        return this;
    }
}
