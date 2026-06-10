package pages;

import wrappers.DropDown;

public class MainPage extends BasePage {

    @Override
    public BasePage openPage() {
        return null;
    }

    @Override
    public BasePage isPageOpened() { return null; }

    public void openPageWithDropDown(String label, String option) {
        new DropDown(label).selectOption(option);
    }

    public CreateUserPage openPageCreateUser() {
        new DropDown("Users").selectOption("Create new");
        return new CreateUserPage();
    }

    public AllUsersPage openPageAllUsers() {
        new DropDown("Users").selectOption("Read all");
        return new AllUsersPage();
    }

    public AddMoneyPage openPageAddMoney() {
        new DropDown("Users").selectOption("Add money");
        return new AddMoneyPage();
    }
}
