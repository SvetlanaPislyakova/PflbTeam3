package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;

@Log4j2
public class LoginPage extends BasePage {

    private final SelenideElement TITLE = $(byText("Authorization"));
    private final SelenideElement EMAIL_INPUT = $("[name='email']");
    private final SelenideElement ERROR_EMAIL_MSG = $x("//*[@name='email']/preceding-sibling::div[1]");
    private final SelenideElement PASSWORD_INPUT = $("[name='password']");
    private final SelenideElement ERROR_PASSWORD_MSG = $x("//*[@name='password']/preceding-sibling::div[1]");
    private final SelenideElement GO_BTN = $x("//button[text()=' GO']");

    @Override
    @Step("Открытие страницы авторизации")
    public LoginPage openPage() {
        log.info("Открыть страницу авторизации");
        open(baseUrl);
        return this;
    }

    @Override
    @Step("Проверка открытия страницы авторизации")
    public LoginPage isPageOpened() {
        log.info("Страница авторизации открыта");
        TITLE.shouldBe(visible);
        return this;
    }

    @Step("Заполнение поля email значением '{email}'")
    public LoginPage fillEmailInput(String email) {
        log.info("Заполнить поле email значением '{}'", email);
        EMAIL_INPUT.setValue(email).sendKeys(Keys.TAB);
        return this;
    }

    @Step("Заполнение поля password значением '{password}'")
    public LoginPage fillPasswordInput(String password) {
        log.info("Заполнить поле password значением '{}'", password);
        PASSWORD_INPUT.setValue(password).sendKeys(Keys.TAB);
        return this;
    }

    @Step("Нажатие на кнопку 'GO'")
    public LoginPage goBtnClick() {
        log.info("Нажать на кнопку 'GO'");
        GO_BTN.click();
        return this;
    }

    @Step("Получение текста alert сообщения")
    public String getAlertMsg() {
        return switchTo().alert().getText();
    }

    @Step("Принятие alert сообщения")
    public LoginPage acceptAlert() {
        switchTo().alert().accept();
        return this;
    }

    @Step("Отклонение alert сообщения")
    public LoginPage rejectAlert() {
        switchTo().alert().dismiss();
        return this;
    }

    @Step("Получение сообщения об ошибке email")
    public String getErrorEmailMessage() {
        return ERROR_EMAIL_MSG.shouldBe(visible).getText();
    }

    @Step("Получение сообщения об ошибке password")
    public String getErrorPasswordMessage() {
        return ERROR_PASSWORD_MSG.shouldBe(visible).getText();
    }
}
