package ui.pages;

import ui.wrappers.Table;

import java.math.BigDecimal;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class IssueLoanPage extends BasePage {

    private final String tableName = "Issue a loan";
    Table table = new Table(tableName);

    @Override
    public IssueLoanPage openPage() {
        open(baseUrl + "/#/update/Issue_A_Loan");
        return this;
    }

    @Override
    public IssueLoanPage isPageOpened() {
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
