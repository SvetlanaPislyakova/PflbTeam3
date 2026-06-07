package pages;

import wrappers.DropDown;

import static com.codeborne.selenide.Selenide.sleep;

public class MainPage extends BasePage {

    @Override
    public BasePage openPage() {
        return null;
    }

    @Override
    public MainPage isPageOpened() {

        return this;
    }

    public void openPageCreateUser() {
        new DropDown("Users").selectOption("Create new");
    }
}
