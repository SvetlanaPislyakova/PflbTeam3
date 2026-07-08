package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import ui.wrappers.Button;
import ui.wrappers.Table;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

@Log4j2
public class ReadHouseOneById extends BasePage {

    private final Table houseInfoTable = new Table("House info:");
    private final Table lodgersTable = new Table("Lodgers:");
    private final Table parkingsTable = new Table("Parkings:");
    private final SelenideElement HOUSE_INPUT = $("#house_input");

    @Override
    @Step("Открытие страницы 'House -> Read by ID'")
    public ReadHouseOneById openPage() {
        log.info("Открыть страницу 'House -> Read by ID'");
        open(baseUrl + "#/read/house");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы 'House -> Read by ID'")
    public ReadHouseOneById isPageOpened() {
        log.info("Проверить, что страница открыта");
        houseInfoTable.checkTableVisible();
        return this;
    }

    @Step("Ввести ID дома: {houseId}")
    public ReadHouseOneById enterHouseId(String houseId) {
        log.info("Ввести ID дома: {}", houseId);
        sleep(300);
        HOUSE_INPUT.shouldBe(visible).shouldBe(enabled).setValue(houseId);
        return this;
    }

    @Step("Нажать кнопку 'Read'")
    public ReadHouseOneById clickRead() {
        log.info("Нажать кнопку 'Read'");
        new Button("Read").clickBtn();
        houseInfoTable.checkTableVisible();
        return this;
    }

    public ReadHouseOneById findHouseById(String houseId) {
        enterHouseId(houseId);
        clickRead();
        return this;
    }

    // Методы для получения данных из таблицы дома
    @Step("Получить ID дома")
    public String getHouseId() {
        log.info("Получить ID дома");
        return houseInfoTable.getValueFromCell("ID");
    }

    @Step("Получить количество этажей")
    public String getFloorCount() {
        log.info("Получить количество этажей");
        return houseInfoTable.getValueFromCell("Floor Count:");
    }

    @Step("Получить цену дома")
    public String getPrice() {
        log.info("Получить цену дома");
        return houseInfoTable.getValueFromCell("Price:");
    }

    @Step("Получить количество парковочных мест")
    public String getParkingPlaces() {
        log.info("Получить количество парковочных мест");
        return houseInfoTable.getValueFromCell("Parking Places:");
    }

    @Step("Получить количество жильцов")
    public String getLodgersCount() {
        log.info("Получить количество жильцов");
        return houseInfoTable.getValueFromCell("Lodgers");
    }

    // Методы для проверки таблиц
    @Step("Проверить видимость таблицы дома")
    public ReadHouseOneById checkHouseTableVisible() {
        log.info("Проверить видимость таблицы дома");
        houseInfoTable.checkTableVisible();
        return this;
    }

    @Step("Проверить видимость таблицы жильцов")
    public ReadHouseOneById checkLodgersTableVisible() {
        log.info("Проверить видимость таблицы жильцов");
        lodgersTable.checkTableVisible();
        return this;
    }

    @Step("Проверить видимость таблицы парковок")
    public ReadHouseOneById checkParkingsTableVisible() {
        log.info("Проверить видимость таблицы парковок");
        parkingsTable.checkTableVisible();
        return this;
    }

    // Методы для получения данных из таблицы парковок
    @Step("Получить ID парковки")
    public String getParkingId() {
        log.info("Получить ID парковки");
        return parkingsTable.getValueFromCell("ID");
    }

    @Step("Получить значение isWarm")
    public String getIsWarm() {
        log.info("Получить значение isWarm");
        return parkingsTable.getValueFromCell("isWarm:");
    }

    @Step("Получить значение isCovered")
    public String getIsCovered() {
        log.info("Получить значение isCovered");
        return parkingsTable.getValueFromCell("isCovered:");
    }

    @Step("Получить количество мест на парковке")
    public String getPlacesCount() {
        log.info("Получить количество мест на парковке");
        return parkingsTable.getValueFromCell("placesCount:");
    }

    // Метод для получения статуса
    @Step("Получить статус")
    public String getStatus() {
        log.info("Получить статус");
        return houseInfoTable.getMessagePushToApi();
    }
}
