package pages;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CreateUserPage extends BasePage {

    @Override
    public BasePage openPage() {
        return null;
    }

    @Override
    public CreateUserPage isPageOpened() {
        $$x("//table").shouldHave(size(1));
        $(withText("User ID")).shouldBe(visible);
        $(withText("ID will be generated")).shouldBe(visible);
        return this;
    }
}
