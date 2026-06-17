package ui.pages;

import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class AllPostPage extends BasePage {

    private final Table createUserTable = new Table("Create new user");
    private final Table addMoneyTable = new Table("Add money");
    private final Table buyOrSellCarTable = new Table("Buy or sell car");
    private final Table settleOrEvictUserTable = new Table("Settle to house");
    private final Table createCarTable = new Table("Create new car");
    private final Table createHouseTable = new Table("Create new house");

    @Override
    public AllPostPage openPage() {
        open(baseUrl + "#/create/all");
        return this;
    }

    @Override
    public AllPostPage isPageOpened() {
        checkUserForm()
                .checkMoneyForm()
                .checkCarDealForm()
                .checkHouseDealForm()
                .checkCarForm()
                .checkHouseForm();
        return this;
    }

    public AllPostPage checkUserForm() {
        createUserTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkMoneyForm() {
        addMoneyTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkCarDealForm() {
        buyOrSellCarTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkHouseDealForm() {
        settleOrEvictUserTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkCarForm() {
        createCarTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkHouseForm() {
        createHouseTable.checkTableVisible();
        return this;
    }

}
