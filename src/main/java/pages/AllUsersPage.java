package pages;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class AllUsersPage extends BasePage {

    @Override
    public AllUsersPage openPage() {
        open(baseUrl + "#/read/users");
        return this;
    }

    @Override
    public AllUsersPage isPageOpened() {
        return this;
    }
}
