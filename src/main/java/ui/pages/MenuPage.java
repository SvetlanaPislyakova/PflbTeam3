package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MenuPage extends BasePage {

    private final SelenideElement ALL_POST_BTN = $("[href='#/create/all']");

    @Override
    public BasePage openPage() {
        return null;
    }

    @Override
    public BasePage isPageOpened() { return null; }

    public MenuPage openAllPostPage() {
        ALL_POST_BTN.click();
        return this;
    }
}
