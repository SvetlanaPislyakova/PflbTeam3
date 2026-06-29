package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.Configuration.baseUrl;

@Log4j2
public class AllDeletePage extends BasePage {

    private final String DELETE_BUTTON = "//button[@value = '%s']";
    private final String DELETE_INPUT = "//button[@value = '%s']/ancestor::div[@role='group']//input";
    private final String DELETE_STATUS = "//button[@value = '%s']/ancestor::div[@role='group']//button[@class='status btn btn-secondary']";

    @Override
    @Step("Открытие страницы All DELETE")
    public AllDeletePage openPage() {
        log.info("Opening All DELETE page");
        open(baseUrl + "#/delete/all");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы All DELETE")
    public AllDeletePage isPageOpened() {
        log.info("Checking All DELETE page is loaded");
        $x(String.format(DELETE_BUTTON, "user")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        return this;
    }

    @Step("Удаление пользователя с ID: {userId}")
    public AllDeletePage deleteUser(Integer userId) {
        log.info("Deleting user with ID: {}", userId);
        SelenideElement input = $x(String.format(DELETE_INPUT, "user"));
        Actions actions = new Actions(getWebDriver());

        actions.moveToElement(input).click().perform();
        actions.sendKeys(String.valueOf(userId)).perform();
        actions.sendKeys(Keys.TAB).perform();
        log.info("User ID entered: {}", userId);

        SelenideElement button = $x(String.format(DELETE_BUTTON, "user"));
        button.shouldBe(Condition.enabled, Duration.ofSeconds(10));
        button.click();
        log.info("DELETE USER button clicked");

        $x(String.format(DELETE_STATUS, "user")).shouldHave(Condition.text("204"), Duration.ofSeconds(30));
        log.info("User {} deleted successfully, status 204 received", userId);
        return this;
    }

    @Step("Удаление автомобиля с ID: {carId}")
    public AllDeletePage deleteCar(Integer carId) {
        log.info("Deleting car with ID: {}", carId);
        SelenideElement input = $x(String.format(DELETE_INPUT, "car"));
        Actions actions = new Actions(getWebDriver());

        actions.moveToElement(input).click().perform();
        actions.sendKeys(String.valueOf(carId)).perform();
        actions.sendKeys(Keys.TAB).perform();
        log.info("Car ID entered: {}", carId);

        SelenideElement button = $x(String.format(DELETE_BUTTON, "car"));
        button.shouldBe(Condition.enabled, Duration.ofSeconds(10));
        button.click();
        log.info("DELETE CAR button clicked");

        $x(String.format(DELETE_STATUS, "car")).shouldHave(Condition.text("204"), Duration.ofSeconds(30));
        log.info("Car {} deleted successfully, status 204 received", carId);
        return this;
    }

    @Step("Удаление дома с ID: {houseId}")
    public AllDeletePage deleteHouse(Integer houseId) {
        log.info("Deleting house with ID: {}", houseId);
        SelenideElement input = $x(String.format(DELETE_INPUT, "house"));
        Actions actions = new Actions(getWebDriver());

        actions.moveToElement(input).click().perform();
        actions.sendKeys(String.valueOf(houseId)).perform();
        actions.sendKeys(Keys.TAB).perform();
        log.info("House ID entered: {}", houseId);

        SelenideElement button = $x(String.format(DELETE_BUTTON, "house"));
        button.shouldBe(Condition.enabled, Duration.ofSeconds(10));
        button.click();
        log.info("DELETE HOUSE button clicked");

        $x(String.format(DELETE_STATUS, "house")).shouldHave(Condition.text("204"), Duration.ofSeconds(30));
        log.info("House {} deleted successfully, status 204 received", houseId);
        return this;
    }

    @Step("Получение статуса удаления пользователя")
    public int getUserStatusCode() {
        String statusText = $x(String.format(DELETE_STATUS, "user")).getText();
        return parseStatusCode(statusText);
    }

    @Step("Получение статуса удаления автомобиля")
    public int getCarStatusCode() {
        String statusText = $x(String.format(DELETE_STATUS, "car")).getText();
        return parseStatusCode(statusText);
    }

    @Step("Получение статуса удаления дома")
    public int getHouseStatusCode() {
        String statusText = $x(String.format(DELETE_STATUS, "house")).getText();
        return parseStatusCode(statusText);
    }

    private int parseStatusCode(String statusText) {
        if (statusText == null || statusText.isEmpty() || statusText.equals("Status: not pushed")) {
            return -1;
        }
        return Integer.parseInt(statusText.replaceAll("\\D+", ""));
    }
}