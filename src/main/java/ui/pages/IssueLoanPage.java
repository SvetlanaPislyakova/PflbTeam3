package ui.pages;

import ui.wrappers.Table;

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

    public IssueLoanPage requestALoan() {
        table.setValueToInput("User ID", "13004");
        table.setValueToInput("Размер кредита", "2000.025");
        table.clickIssueLoanBtn();
        return this;
    }
}
