package pages;

import wrappers.DropDown;

public class MainPage extends BasePage {

    @Override
    public BasePage openPage() {
        return null;
    }

    @Override
    public MainPage isPageOpened() {

        return this;
    }

    public CreateUserPage openPageCreateUser() {
        new DropDown("Users").selectOption("Create new");
        return new CreateUserPage();
    }
}
