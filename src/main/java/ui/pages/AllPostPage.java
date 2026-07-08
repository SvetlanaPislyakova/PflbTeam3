package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.dto.Car;
import ui.dto.House;
import ui.dto.User;
import ui.wrappers.Radio;
import ui.wrappers.Table;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class AllPostPage extends BasePage {

    private static final String BUY_OR_SELL_CAR_FORM =
            "//*[th[contains(text(), 'User ID')] and th[contains(text(), 'Car Id')]]/ancestor::table";
    private static final String SETTLE_OR_EVICT_FORM =
            "//*[th[contains(text(), 'User ID')] and th[contains(text(), 'House ID')]]/ancestor::table";
    private final Table createUserTable = new Table("Create new user");
    private final Table addMoneyTable = new Table("Add money");
    private final Table buyOrSellCarTable = new Table("Buy or sell car");
    private final Table settleOrEvictUserTable = new Table("Settle to house");
    private final Table createCarTable = new Table("Create new car");
    private final Table createHouseTable = new Table("Create new house");

    @Override
    @Step("Открытие страницы ALL POST")
    public AllPostPage openPage() {
        open(baseUrl + "#/create/all");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы ALL POST")
    public AllPostPage isPageOpened() {
        checkUserForm()
                .checkMoneyForm()
                .checkCarDealForm()
                .checkHouseDealForm()
                .checkCarForm()
                .checkHouseForm();
        return this;
    }

    @Step("Проверка видимости формы создания пользователя")
    public AllPostPage checkUserForm() {
        createUserTable.checkTableVisible();
        return this;
    }

    @Step("Проверка видимости формы добавления денег")
    public AllPostPage checkMoneyForm() {
        addMoneyTable.checkTableVisible();
        return this;
    }

    @Step("Проверка видимости формы покупки/продажи авто")
    public AllPostPage checkCarDealForm() {
        buyOrSellCarTable.checkTableVisible();
        return this;
    }
    @Step("Проверка видимости формы покупки/продажи дома")
    public AllPostPage checkHouseDealForm() {
        settleOrEvictUserTable.checkTableVisible();
        return this;
    }

    @Step("Проверка видимости формы создания автомобиля")
    public AllPostPage checkCarForm() {
        createCarTable.checkTableVisible();
        return this;
    }

    @Step("Проверка видимости формы создания дома")
    public AllPostPage checkHouseForm() {
        createHouseTable.checkTableVisible();
        return this;
    }

    @Step("Создание пользователя")
    public AllPostPage createUser(User user) {
        createUserTable.setValueToInput("First Name", user.getFirstName());
        createUserTable.setValueToInput("Last Name", user.getLastName());
        createUserTable.setValueToInput("Age", String.valueOf(user.getAge()));
        if (user.getSex() != null) {
            Radio.byNameAndValue("sex_send", user.getSex()).select();
        }
        createUserTable.setValueToInput("Money", String.valueOf(user.getMoney()));
        createUserTable.clickPushToApiBtn();
        return this;
    }

    @Step("Получение сообщения о статусе операции")
    public String getCreateUserStatusMessage() {
        return createUserTable.getMessagePushToApi();
    }

    @Step("Получение кода статуса")
    public Integer getCreateUserStatusCode() {
        return createUserTable.getStatus();
    }

    @Step("Получение ID созданного пользователя")
    public Integer getCreateUserId() {
        return createUserTable.getResultInt();
    }

    @Step("Добавление денег пользователю")
    public AllPostPage addMoneyToUser(Integer userId, BigDecimal amount) {
        addMoneyTable.setValueToInput("User ID", String.valueOf(userId));
        addMoneyTable.setValueToInput("Money", String.valueOf(amount));
        addMoneyTable.clickPushToApiBtn();
        return this;
    }

    @Step("Получение сообщения о статусе операции")
    public String getAddMoneyStatusMessage() {
        return addMoneyTable.getMessagePushToApi();
    }

    @Step("Получение кода статуса")
    public Integer getAddMoneyStatusCode() {
        return addMoneyTable.getStatus();
    }

    @Step("Получение результата операции")
    public Double getAddMoneyResult() {
        return addMoneyTable.getResultDouble();
    }

    @Step("Покупка дома пользователем")
    public AllPostPage buyCarForUser(Integer userId, Integer carId) {
        buyOrSellCarTable.setValueToInput("User ID", String.valueOf(userId));
        buyOrSellCarTable.setValueToInput("Car Id", String.valueOf(carId));
        Radio.byValueIn(BUY_OR_SELL_CAR_FORM, "buyCar").select();
        buyOrSellCarTable.clickPushToApiBtn();
        return this;
    }

    @Step("Получение ID созданного пользователя")
    public AllPostPage sellCarForUser(Integer userId, Integer carId) {
        buyOrSellCarTable.setValueToInput("User ID", String.valueOf(userId));
        buyOrSellCarTable.setValueToInput("Car Id", String.valueOf(carId));
        Radio.byValueIn(BUY_OR_SELL_CAR_FORM, "sellCar").select();
        buyOrSellCarTable.clickPushToApiBtn();
        return this;
    }

    @Step("Получение сообщения о статусе операции")
    public String getBuyOrSellCarStatusMessage() {
        return buyOrSellCarTable.getMessagePushToApi();
    }

    @Step("Получение кода статуса")
    public Integer getBuyOrSellCarStatusCode() {
        return buyOrSellCarTable.getStatus();
    }

    @Step("Выселить пользователя из дома")
    public AllPostPage settleUserToHouse(Integer userId, Integer houseId) {
        settleOrEvictUserTable.setValueToInput("User ID", String.valueOf(userId));
        settleOrEvictUserTable.setValueToInput("House ID", String.valueOf(houseId));
        Radio.byValueIn(SETTLE_OR_EVICT_FORM, "settle").select();
        settleOrEvictUserTable.clickPushToApiBtn();
        return this;
    }

    @Step("Заселить пользователя из дома")
    public AllPostPage evictUserFromHouse(Integer userId, Integer houseId) {
        settleOrEvictUserTable.setValueToInput("User ID", String.valueOf(userId));
        settleOrEvictUserTable.setValueToInput("House ID", String.valueOf(houseId));
        Radio.byValueIn(SETTLE_OR_EVICT_FORM, "evict").select();
        settleOrEvictUserTable.clickPushToApiBtn();
        return this;
    }

    @Step("Получение сообщения о статусе операции")
    public String getSettleOrEvictStatusMessage() {
        return settleOrEvictUserTable.getMessagePushToApi();
    }

    @Step("Получение кода статуса")
    public Integer getSettleOrEvictStatusCode() {
        return settleOrEvictUserTable.getStatus();
    }

    @Step("Создание нового автомобиля")
    public AllPostPage createCar(Car car) {
        createCarTable.setValueToInput("Engine Type", car.getEngineType());
        createCarTable.setValueToInput("Mark", car.getMark());
        createCarTable.setValueToInput("Model", car.getModel());
        createCarTable.setValueToInput("Price", String.valueOf(car.getPrice()));
        createCarTable.clickPushToApiBtn();
        return this;
    }

    @Step("Получение сообщения о статусе операции")
    public String getCreateCarStatusMessage() {
        return createCarTable.getMessagePushToApi();
    }

    @Step("Получение кода статуса")
    public Integer getCreateCarStatusCode() {
        return createCarTable.getStatus();
    }

    @Step("Получение ID созданного автомобиля")
    public Integer getCreateCarId() {
        return createCarTable.getResultInt();
    }

    @Step("Созданеи нового дома")
    public AllPostPage createHouse(House house) {
        fillHouseForm(house);
        createHouseTable.clickPushToApiBtn();
        return this;
    }

    @Step("Заполнение формы создания дома")
    private void fillHouseForm(House house) {
        createHouseTable.setValueToInput("Floors", String.valueOf(house.getFloors()));
        createHouseTable.setValueToInput("Price", String.valueOf(house.getPrice()));
        setHouseInput("#parking_first_send", String.valueOf(house.getWarmCoveredParking()));
        setHouseInput("#parking_second_send", String.valueOf(house.getWarmNotCoveredParking()));
        setHouseInput("#parking_third_send", String.valueOf(house.getColdCoveredParking()));
        setHouseInput("#parking_fourth_send", String.valueOf(house.getColdNotCoveredParking()));
    }

    @Step("Заполнение полей для создания дома")
    private void setHouseInput(String selector, String fieldValue) {
        SelenideElement input = $(selector).shouldBe(visible).shouldBe(enabled);
        input.click();
        input.clear();
        input.sendKeys(fieldValue);
        input.shouldHave(value(fieldValue));
        sleep(200);
    }

    @Step("Получение сообщения о статусе операции")
    public String getCreateHouseStatusMessage() {
        return createHouseTable.getMessagePushToApi();
    }

    @Step("Получение кода статуса")
    public Integer getCreateHouseStatusCode() {
        return createHouseTable.getStatus();
    }

    @Step("Получение ID созданного дома")
    public Integer getCreateHouseId() {
        return createHouseTable.getResultInt();
    }
}
