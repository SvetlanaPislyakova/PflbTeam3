package pages;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class AllPostPage extends BasePage {

    @Override
    public AllPostPage openPage() {
        open(baseUrl + "/#/create/all");
        return this;
    }

    @Override
    public AllPostPage isPageOpened() {
        return this;
    }
}
