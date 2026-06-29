package ui.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class CreateNewHousePage extends BasePage {

    private final SelenideElement FLOORS_INPUT = $("#floor_send");
    private final SelenideElement PRICE_INPUT = $("#price_send");

    private final SelenideElement PARKING_WARM_COVERED = $("#parking_first_send");
    private final SelenideElement PARKING_WARM_NOT_COVERED = $("#parking_second_send");
    private final SelenideElement PARKING_COLD_COVERED = $("#parking_third_send");
    private final SelenideElement PARKING_COLD_NOT_COVERED = $("#parking_fourth_send");

    private final SelenideElement PUSH_BUTTON = $x("//button[contains(@class, 'tableButton')]");
    private final SelenideElement STATUS_BUTTON = $x("//button[contains(@class, 'status')]");
    private final SelenideElement NEW_HOUSE_ID = $x("//button[contains(@class, 'newId')]");

    @Override
    public CreateNewHousePage openPage() {
        log.info("Открыть страницу 'Houses -> Create new'");
        open(baseUrl + "#/create/house");
        return this;
    }

    @Override
    public CreateNewHousePage isPageOpened() {
        log.info("Проверить, что страница открыта");
        FLOORS_INPUT.shouldBe(visible);
        PRICE_INPUT.shouldBe(visible);
        PUSH_BUTTON.shouldBe(visible);
        return this;
    }

    // Методы для заполнения основной информации
    public CreateNewHousePage setFloors(int floors) {
        log.info("Установить количество этажей: {}", floors);
        FLOORS_INPUT.shouldBe(visible).shouldBe(enabled).setValue(String.valueOf(floors));
        return this;
    }

    public CreateNewHousePage setPrice(double price) {
        log.info("Установить цену дома: {}", price);
        PRICE_INPUT.shouldBe(visible).shouldBe(enabled).setValue(String.valueOf(price));
        return this;
    }

    // Методы для заполнения парковочных мест
    public CreateNewHousePage setWarmCoveredParking(int count) {
        log.info("Установить количество теплых крытых парковок: {}", count);
        PARKING_WARM_COVERED.shouldBe(visible).shouldBe(enabled).setValue(String.valueOf(count));
        return this;
    }

    public CreateNewHousePage setWarmNotCoveredParking(int count) {
        log.info("Установить количество теплых открытых парковок: {}", count);
        PARKING_WARM_NOT_COVERED.shouldBe(visible).shouldBe(enabled).setValue(String.valueOf(count));
        return this;
    }

    public CreateNewHousePage setColdCoveredParking(int count) {
        log.info("Установить количество холодных крытых парковок: {}", count);
        PARKING_COLD_COVERED.shouldBe(visible).shouldBe(enabled).setValue(String.valueOf(count));
        return this;
    }

    public CreateNewHousePage setColdNotCoveredParking(int count) {
        log.info("Установить количество холодных открытых парковок: {}", count);
        PARKING_COLD_NOT_COVERED.shouldBe(visible).shouldBe(enabled).setValue(String.valueOf(count));
        return this;
    }

    // Методы для отправки и получения результатов
    public CreateNewHousePage clickPushToApi() {
        log.info("Нажать кнопку 'PUSH TO API'");
        PUSH_BUTTON.shouldBe(visible).shouldBe(enabled).click();
        return this;
    }

    public String getStatus() {
        log.info("Получить статус операции");
        STATUS_BUTTON.shouldHave(
                com.codeborne.selenide.Condition.text("Successfully pushed"),
                java.time.Duration.ofSeconds(30)
        );
        return STATUS_BUTTON.getText();
    }

    public String getHouseId() {
        log.info("Get the ID of the created property");
        // Ждём, что кнопка содержит текст
        NEW_HOUSE_ID.shouldNotBe(com.codeborne.selenide.Condition.empty, Duration.ofSeconds(30));
        String text = NEW_HOUSE_ID.text();
        log.info("Button text: {}", text);
        // Извлекаем только цифры
        return text.replaceAll("\\D", "");
    }

    // Комплексные методы
    public CreateNewHousePage createHouse(int floors, double price,
                                          int warmCovered, int warmNotCovered,
                                          int coldCovered, int coldNotCovered) {
        log.info("Создать дом: этажей={}, цена={}, warm+covered={}, warm+not={}, cold+covered={}, cold+not={}",
                floors, price, warmCovered, warmNotCovered, coldCovered, coldNotCovered);
        setFloors(floors);
        setPrice(price);
        setWarmCoveredParking(warmCovered);
        setWarmNotCoveredParking(warmNotCovered);
        setColdCoveredParking(coldCovered);
        setColdNotCoveredParking(coldNotCovered);
        return this;
    }

    public CreateNewHousePage createSimpleHouse(int floors, double price, int totalParkingPlaces) {
        log.info("Создать простой дом: этажей={}, цена={}, всего парковок={}", floors, price,
                totalParkingPlaces);
        setFloors(floors);
        setPrice(price);
        setWarmCoveredParking(totalParkingPlaces);
        setWarmNotCoveredParking(0);
        setColdCoveredParking(0);
        setColdNotCoveredParking(0);
        return this;
    }
}