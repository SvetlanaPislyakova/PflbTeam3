package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ReadUserWithCarsPage extends BasePage{


    private final SelenideElement ID_INPUT = $("#user_input");

    @Override
    public ReadUserWithCarsPage openPage() {
        open(baseUrl + "#/read/userInfo");
        return this;
    }

    @Override
    public ReadUserWithCarsPage isPageOpened() {
        ID_INPUT.shouldBe(visible);
        return this;
    }
}
