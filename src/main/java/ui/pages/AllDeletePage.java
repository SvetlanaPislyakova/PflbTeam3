package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class AllDeletePage extends BasePage {

    private final SelenideElement userInput = $x("//button[contains(text(), 'DELETE') and contains(text(), 'USER')]/following-sibling::button//input");
    private final SelenideElement userPushBtn = $x("//button[contains(text(), 'DELETE') and contains(text(), 'USER')]");
    private final SelenideElement userStatus = $x("//button[contains(text(), 'DELETE') and contains(text(), 'USER')]/following-sibling::button[contains(@class, 'status')]");

    private final SelenideElement houseInput = $x("//button[contains(text(), 'DELETE') and contains(text(), 'HOUSE')]/following-sibling::button//input");
    private final SelenideElement housePushBtn = $x("//button[contains(text(), 'DELETE') and contains(text(), 'HOUSE')]");
    private final SelenideElement houseStatus = $x("//button[contains(text(), 'DELETE') and contains(text(), 'HOUSE')]/following-sibling::button[contains(@class, 'status')]");

    private final SelenideElement carInput = $x("//button[contains(text(), 'DELETE') and contains(text(), 'CAR')]/following-sibling::button//input");
    private final SelenideElement carPushBtn = $x("//button[contains(text(), 'DELETE') and contains(text(), 'CAR')]");
    private final SelenideElement carStatus = $x("//button[contains(text(), 'DELETE') and contains(text(), 'CAR')]/following-sibling::button[contains(@class, 'status')]");

    @Override
    @Step("Открытие страницы ALL DELETE")
    public AllDeletePage openPage() {
        log.info("Открытие страницы ALL DELETE");
        open("/#/delete/all");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы ALL DELETE")
    public AllDeletePage isPageOpened() {
        log.info("Проверка открытия страницы ALL DELETE");
        userInput.shouldBe(visible);
        return this;
    }

    @Step("Удаление пользователя с ID: {0}")
    public AllDeletePage deleteUser(Integer userId) {
        log.info("Удаление пользователя с ID: {}", userId);
        userInput.setValue(String.valueOf(userId));
        userPushBtn.click();
        userStatus.shouldNotHave(Condition.text("Status: not pushed"), Duration.ofSeconds(60));

        String statusText = userStatus.getText();
        if (statusText.contains("Bad request") || statusText.contains("400")) {
            log.error("Удаление пользователя {} не удалось: {}", userId, statusText);
            throw new AssertionError("Удаление пользователя " + userId + " не удалось: " + statusText);
        }

        log.info("Пользователь {} успешно удален", userId);
        return this;
    }

    @Step("Удаление дома с ID: {0}")
    public AllDeletePage deleteHouse(Integer houseId) {
        log.info("Удаление дома с ID: {}", houseId);
        houseInput.setValue(String.valueOf(houseId));
        housePushBtn.click();
        houseStatus.shouldNotHave(Condition.text("Status: not pushed"), Duration.ofSeconds(60));

        String statusText = houseStatus.getText();
        if (statusText.contains("Bad request") || statusText.contains("400")) {
            log.error("Удаление дома {} не удалось: {}", houseId, statusText);
            throw new AssertionError("Удаление дома " + houseId + " не удалось: " + statusText);
        }

        log.info("Дом {} успешно удален", houseId);
        return this;
    }

    @Step("Удаление автомобиля с ID: {0}")
    public AllDeletePage deleteCar(Integer carId) {
        log.info("Удаление автомобиля с ID: {}", carId);
        carInput.setValue(String.valueOf(carId));
        carPushBtn.click();
        carStatus.shouldNotHave(Condition.text("Status: not pushed"), Duration.ofSeconds(60));

        String statusText = carStatus.getText();
        if (statusText.contains("Bad request") || statusText.contains("400")) {
            log.error("Удаление автомобиля {} не удалось: {}", carId, statusText);
            throw new AssertionError("Удаление автомобиля " + carId + " не удалось: " + statusText);
        }

        log.info("Автомобиль {} успешно удален", carId);
        return this;
    }

    @Step("Получение статуса пользователя")
    public int getUserStatusCode() {
        String statusText = userStatus.getText();
        if (statusText == null || statusText.isEmpty() || statusText.equals("Status: not pushed")) {
            return -1;
        }
        return Integer.parseInt(statusText.replaceAll("\\D+", ""));
    }

    @Step("Получение статуса дома")
    public int getHouseStatusCode() {
        String statusText = houseStatus.getText();
        if (statusText == null || statusText.isEmpty() || statusText.equals("Status: not pushed")) {
            return -1;
        }
        return Integer.parseInt(statusText.replaceAll("\\D+", ""));
    }

    @Step("Получение статуса автомобиля")
    public int getCarStatusCode() {
        String statusText = carStatus.getText();
        if (statusText == null || statusText.isEmpty() || statusText.equals("Status: not pushed")) {
            return -1;
        }
        return Integer.parseInt(statusText.replaceAll("\\D+", ""));
    }
}