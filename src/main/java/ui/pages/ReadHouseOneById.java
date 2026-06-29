package ui.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import ui.wrappers.Button;
import ui.wrappers.Table;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class ReadHouseOneById extends BasePage {

    private final Table houseInfoTable = new Table("House info:");
    private final Table lodgersTable = new Table("Lodgers:");
    private final Table parkingsTable = new Table("Parkings:");
    private final SelenideElement HOUSE_INPUT = $("#house_input");

    @Override
    public ReadHouseOneById openPage() {
        log.info("Открыть страницу 'House -> Read by ID'");
        open(baseUrl + "#/read/house");
        return this;
    }

    @Override
    public ReadHouseOneById isPageOpened() {
        log.info("Проверить, что страница открыта");
        houseInfoTable.checkTableVisible();
        return this;
    }

    public ReadHouseOneById enterHouseId(String houseId) {
        log.info("Ввести ID дома: {}", houseId);
        sleep(300);
        HOUSE_INPUT.shouldBe(visible).shouldBe(enabled).setValue(houseId);
        return this;
    }

    public ReadHouseOneById clickRead() {
        log.info("Нажать кнопку 'Read'");
        new Button("Read").clickBtn();
        sleep(12000);
        return this;
    }

    public ReadHouseOneById findHouseById(String houseId) {
        enterHouseId(houseId);
        clickRead();
        return this;
    }

    // Методы для получения данных из таблицы дома
    public String getHouseId() {
        log.info("Получить ID дома");
        return houseInfoTable.getValueFromCell("ID");
    }

    public String getFloorCount() {
        log.info("Получить количество этажей");
        return houseInfoTable.getValueFromCell("Floor Count:");  // <-- ИСПРАВЛЕНО
    }

    public String getPrice() {
        log.info("Получить цену дома");
        return houseInfoTable.getValueFromCell("Price:");
    }

    public String getParkingPlaces() {
        log.info("Получить количество парковочных мест");
        return houseInfoTable.getValueFromCell("Parking Places:");
    }

    public String getLodgersCount() {
        log.info("Получить количество жильцов");
        return houseInfoTable.getValueFromCell("Lodgers");
    }

    // Методы для проверки таблиц
    public ReadHouseOneById checkHouseTableVisible() {
        log.info("Проверить видимость таблицы дома");
        houseInfoTable.checkTableVisible();
        return this;
    }

    public ReadHouseOneById checkLodgersTableVisible() {
        log.info("Проверить видимость таблицы жильцов");
        lodgersTable.checkTableVisible();
        return this;
    }

    public ReadHouseOneById checkParkingsTableVisible() {
        log.info("Проверить видимость таблицы парковок");
        parkingsTable.checkTableVisible();
        return this;
    }

    // Методы для получения данных из таблицы парковок
    public String getParkingId() {
        log.info("Получить ID парковки");
        return parkingsTable.getValueFromCell("ID");
    }

    public String getIsWarm() {
        log.info("Получить значение isWarm");
        return parkingsTable.getValueFromCell("isWarm:");
    }

    public String getIsCovered() {
        log.info("Получить значение isCovered");
        return parkingsTable.getValueFromCell("isCovered:");
    }

    public String getPlacesCount() {
        log.info("Получить количество мест на парковке");
        return parkingsTable.getValueFromCell("placesCount:");
    }

    // Метод для получения статуса
    public String getStatus() {
        log.info("Получить статус");
        return houseInfoTable.getMessagePushToApi();
    }
}
