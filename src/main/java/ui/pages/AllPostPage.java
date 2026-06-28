package ui.pages;

import com.codeborne.selenide.SelenideElement;
import ui.dto.Car;
import ui.dto.User;
import ui.wrappers.Radio;
import ui.wrappers.Table;

import java.math.BigDecimal;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
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
    public AllPostPage openPage() {
        open(baseUrl + "#/create/all");
        return this;
    }

    @Override
    public AllPostPage isPageOpened() {
        checkUserForm()
                .checkMoneyForm()
                .checkCarDealForm()
                .checkHouseDealForm()
                .checkCarForm()
                .checkHouseForm();
        return this;
    }

    public AllPostPage checkUserForm() {
        createUserTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkMoneyForm() {
        addMoneyTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkCarDealForm() {
        buyOrSellCarTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkHouseDealForm() {
        settleOrEvictUserTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkCarForm() {
        createCarTable.checkTableVisible();
        return this;
    }

    public AllPostPage checkHouseForm() {
        createHouseTable.checkTableVisible();
        return this;
    }

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

    public String getCreateUserStatusMessage() {
        return createUserTable.getMessagePushToApi();
    }

    public Integer getCreateUserStatusCode() {
        return createUserTable.getStatus();
    }

    public Integer getCreateUserId() {
        return createUserTable.getResultInt();
    }

    public AllPostPage addMoneyToUser(Integer userId, BigDecimal amount) {
        addMoneyTable.setValueToInput("User ID", String.valueOf(userId));
        addMoneyTable.setValueToInput("Money", String.valueOf(amount));
        addMoneyTable.clickPushToApiBtn();
        return this;
    }

    public String getAddMoneyStatusMessage() {
        return addMoneyTable.getMessagePushToApi();
    }

    public Integer getAddMoneyStatusCode() {
        return addMoneyTable.getStatus();
    }

    public Double getAddMoneyResult() {
        return addMoneyTable.getResultDouble();
    }

    public AllPostPage buyCarForUser(Integer userId, Integer carId) {
        buyOrSellCarTable.setValueToInput("User ID", String.valueOf(userId));
        buyOrSellCarTable.setValueToInput("Car Id", String.valueOf(carId));
        Radio.byValueIn(BUY_OR_SELL_CAR_FORM, "buyCar").select();
        buyOrSellCarTable.clickPushToApiBtn();
        return this;
    }

    public AllPostPage sellCarForUser(Integer userId, Integer carId) {
        buyOrSellCarTable.setValueToInput("User ID", String.valueOf(userId));
        buyOrSellCarTable.setValueToInput("Car Id", String.valueOf(carId));
        Radio.byValueIn(BUY_OR_SELL_CAR_FORM, "sellCar").select();
        buyOrSellCarTable.clickPushToApiBtn();
        return this;
    }

    public String getBuyOrSellCarStatusMessage() {
        return buyOrSellCarTable.getMessagePushToApi();
    }

    public Integer getBuyOrSellCarStatusCode() {
        return buyOrSellCarTable.getStatus();
    }

    public AllPostPage settleUserToHouse(Integer userId, Integer houseId) {
        settleOrEvictUserTable.setValueToInput("User ID", String.valueOf(userId));
        settleOrEvictUserTable.setValueToInput("House ID", String.valueOf(houseId));
        Radio.byValueIn(SETTLE_OR_EVICT_FORM, "settle").select();
        settleOrEvictUserTable.clickPushToApiBtn();
        return this;
    }

    public AllPostPage evictUserFromHouse(Integer userId, Integer houseId) {
        settleOrEvictUserTable.setValueToInput("User ID", String.valueOf(userId));
        settleOrEvictUserTable.setValueToInput("House ID", String.valueOf(houseId));
        Radio.byValueIn(SETTLE_OR_EVICT_FORM, "evict").select();
        settleOrEvictUserTable.clickPushToApiBtn();
        return this;
    }

    public String getSettleOrEvictStatusMessage() {
        return settleOrEvictUserTable.getMessagePushToApi();
    }

    public Integer getSettleOrEvictStatusCode() {
        return settleOrEvictUserTable.getStatus();
    }

    public AllPostPage createCar(Car car) {
        createCarTable.setValueToInput("Engine Type", car.getEngineType());
        createCarTable.setValueToInput("Mark", car.getMark());
        createCarTable.setValueToInput("Model", car.getModel());
        createCarTable.setValueToInput("Price", String.valueOf(car.getPrice()));
        createCarTable.clickPushToApiBtn();
        return this;
    }

    public String getCreateCarStatusMessage() {
        return createCarTable.getMessagePushToApi();
    }

    public Integer getCreateCarStatusCode() {
        return createCarTable.getStatus();
    }

    public Integer getCreateCarId() {
        return createCarTable.getResultInt();
    }

    public AllPostPage createHouse(Integer floors, BigDecimal price) {
        fillHouseForm(floors, price);
        createHouseTable.clickPushToApiBtn();
        return this;
    }

    private void fillHouseForm(Integer floors, BigDecimal price) {
        createHouseTable.setValueToInput("Floors", String.valueOf(floors));
        createHouseTable.setValueToInput("Price", String.valueOf(price));
        setHouseInput("#parking_first_send", "1");
        setHouseInput("#parking_second_send", "0");
        setHouseInput("#parking_third_send", "0");
        setHouseInput("#parking_fourth_send", "0");
    }

    private void setHouseInput(String selector, String fieldValue) {
        SelenideElement input = $(selector).shouldBe(visible).shouldBe(enabled);
        input.click();
        input.clear();
        input.sendKeys(fieldValue);
        input.shouldHave(value(fieldValue));
        sleep(200);
    }

    public String getCreateHouseStatusMessage() {
        return createHouseTable.getMessagePushToApi();
    }

    public Integer getCreateHouseStatusCode() {
        return createHouseTable.getStatus();
    }

    public Integer getCreateHouseId() {
        return createHouseTable.getResultInt();
    }

}
