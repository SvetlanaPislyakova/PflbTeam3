package ui.pages;

import com.codeborne.selenide.SelenideElement;
import ui.dto.Car;
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

    public AllPostPage createCar(Car car) {
        setInput("#car_mark_send", car.getMark());
        setInput("#car_model_send", car.getModel());
        setInput("#car_price_send", String.valueOf(car.getPrice()));
        setInput("#car_engine_type_send", car.getEngineType());
        // Форма All POST сбрасывает Mark после изменения Engine Type; повторно выставляем его перед submit.
        setInput("#car_mark_send", car.getMark());
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
        for (int i = 0; i < 3; i++) {
            fillHouseForm(floors, price);
            createHouseTable.clickPushToApiBtn();
            if (!getCreateHouseStatusMessage().contains("Invalid input data")) {
                return this;
            }
            openPage().isPageOpened();
        }
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

    private void setInput(String selector, String fieldValue) {
        SelenideElement input = $(selector).shouldBe(visible).shouldBe(enabled);
        input.click();
        input.setValue(fieldValue);
        input.shouldHave(value(fieldValue));
        sleep(200);
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
