package ui.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class MenuPage extends BasePage {

    private final SelenideElement ALL_POST_BTN = $("[href='#/create/all']");
    private final SelenideElement ALL_DELETE_BTN = $("[href='#/delete/all']");

    @Getter
    public enum MenuOption {
        USERS_READ_ALL("Users", "Read all", new AllUsersPage()),
        USERS_CREATE_NEW("Users", "Create new", new CreateUserPage()),
        USERS_READ_WITH_CARS("Users", "Read user with cars", new ReadUserWithCarsPage()),
        USERS_ADD_MONEY("Users", "Add money", new AddMoneyPage()),
        USERS_BUY_OR_SELL_CAR("Users", "Buy or sell car", new BuyOrSaleCarPage()),
//        USERS_SETTLE_TO_HOUSE("Users", "Settle to house", new SettleToHousePage()),
        USERS_ISSUE_LOAN("Users", "Issue a loan", new IssueLoanPage()),

        CARS_READ_ALL("Cars", "Read all", new AllCarsPage()),
        CARS_CREATE_NEW("Cars", "Create new", new CreateCarPage()),
        CARS_BUY_OR_SELL_CAR("Cars", "Buy or sell car", new BuyOrSaleCarPage());

//        HOUSES_READ_ALL("Houses", "Read all", new AllHousesPage()),
//        HOUSES_READ_ONE_BY_ID("Houses", "Read one by ID", new ReadOneHousePage()),
//        HOUSES_CREATE_NEW("Houses", "Create new", new CreateHousePage()),
//        HOUSES_SETTLE_OR_EVICT_USER("Houses", "Settle or evict user", new SettleOrEvictPage());

        private final String dropdown;
        private final String option;
        private final BasePage page;

        MenuOption(String dropdown, String option, BasePage page) {
            this.dropdown = dropdown;
            this.option = option;
            this.page = page;
        }
    }

    @Override
    public BasePage openPage() {
        return null;
    }

    @Override
    public BasePage isPageOpened() { return null; }

    public AllPostPage openAllPostPage() {
        ALL_POST_BTN.click();
        switchTo().window(1);
        return new AllPostPage();
    }

    public AllDeletePage openAllDeletePage() {
        ALL_DELETE_BTN.click();
        switchTo().window(1);
        return new AllDeletePage();
    }
}