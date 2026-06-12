package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage {

    private final SelenideElement TITLE = $(byText("Authorization"));
    private final SelenideElement EMAIL_INPUT = $("[name='email']");
    private final SelenideElement PASSWORD_INPUT = $("[name='password']");
    private final SelenideElement GO_BTN = $x("//button[text()=' GO']");

    @Override
    public LoginPage openPage() {
        open(baseUrl);
        return this;
    }

    @Override
    public LoginPage isPageOpened() {
        TITLE.shouldBe(visible);
        return this;
    }

    public LoginPage login(String email, String password) {
        EMAIL_INPUT.setValue(email);
        PASSWORD_INPUT.setValue(password);
        GO_BTN.click();
        return this;
    }

    public String getAlertMsg() {
        return switchTo().alert().getText();
    }

    public LoginPage acceptAlert() {
        switchTo().alert().accept();
        return this;
    }

    public LoginPage rejectAlert() {
        switchTo().alert().dismiss();
        return this;
    }
}
