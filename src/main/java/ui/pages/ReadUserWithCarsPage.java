package ui.pages;

import com.codeborne.selenide.SelenideElement;
import ui.wrappers.Button;

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

    public ReadUserWithCarsPage findCarsByUserId(Integer userId) {
        ID_INPUT.setValue(String.valueOf(userId));
        new Button("Read").clickBtn();
        return this;
    }
}
