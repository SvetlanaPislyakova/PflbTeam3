package ui.pages;

import ui.dto.Car;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class CreateCarPage extends BasePage {
    private static final String TABLE_NAME = "Create new car";
    private static final String ENGINE_TYPE_FIELD = "Engine Type";
    private static final String MARK_FIELD = "Mark";
    private static final String MODEL_FIELD = "Model";
    private static final String PRICE_FIELD = "Price";

    private final Table table = new Table(TABLE_NAME);

    @Override
    public CreateCarPage openPage() {
        open(baseUrl + "#/create/cars");
        return this;
    }

    @Override
    public CreateCarPage isPageOpened() {
        table.checkTableVisible();
        return this;
    }

    public CreateCarPage createNewCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car object cannot be null");
        }
        table.setValueToInput(ENGINE_TYPE_FIELD, car.getEngineType());
        table.setValueToInput(MARK_FIELD, car.getMark());
        table.setValueToInput(MODEL_FIELD, car.getModel());
        table.setValueToInput(PRICE_FIELD, car.getPrice().toPlainString());
        table.clickPushToApiBtn();
        return this;
    }

    public String getStatusMessage() {
        return table.getMessagePushToApi();
    }

    public int getStatusCode() {
        return table.getStatus();
    }

    public Long getCarId() {
        return table.getResultInt().longValue();
    }
}
