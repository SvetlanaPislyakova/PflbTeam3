package ui.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

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
    public AllDeletePage openPage() {
        open(baseUrl + "#/delete/all");
        return this;
    }

    @Override
    public AllDeletePage isPageOpened() {
        userInput.shouldBe(visible);
        return this;
    }

    public AllDeletePage deleteUser(Integer userId) {
        userInput.setValue(String.valueOf(userId));
        carPushBtn.shouldBe(visible).shouldBe(enabled);
        userPushBtn.click();
        userStatus.shouldNotHave(text("Status: not pushed"), Duration.ofSeconds(60));
        return this;
    }

    public int getUserStatusCode() {
        String statusText = userStatus.getText();
        if (statusText == null || statusText.isEmpty() || statusText.equals("Status: not pushed")) {
            return -1;
        }
        return Integer.parseInt(statusText.replaceAll("\\D+", ""));
    }

    public AllDeletePage deleteHouse(Integer houseId) {
        houseInput.setValue(String.valueOf(houseId));
        carPushBtn.shouldBe(visible).shouldBe(enabled);
        housePushBtn.click();
        houseStatus.shouldNotHave(text("Status: not pushed"), Duration.ofSeconds(60));
        return this;
    }

    public int getHouseStatusCode() {
        String statusText = houseStatus.getText();
        if (statusText == null || statusText.isEmpty() || statusText.equals("Status: not pushed")) {
            return -1;
        }
        return Integer.parseInt(statusText.replaceAll("\\D+", ""));
    }

    public AllDeletePage deleteCar(Integer carId) {
        carInput.setValue(String.valueOf(carId));
        carPushBtn.shouldBe(visible).shouldBe(enabled);
        carPushBtn.click();
        carStatus.shouldNotHave(text("Status: not pushed"), Duration.ofSeconds(60));
        return this;
    }

    public int getCarStatusCode() {
        String statusText = carStatus.getText();
        if (statusText == null || statusText.isEmpty() || statusText.equals("Status: not pushed")) {
            return -1;
        }
        return Integer.parseInt(statusText.replaceAll("\\D+", ""));
    }
}