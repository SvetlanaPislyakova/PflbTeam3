package ui.pages;

import ui.dto.Car;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CreateCarPage extends BasePage {

    private final String tableName = "Create new car";
    private final Table table = new Table(tableName);


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
        table.setValueToInput("Engine Type", String.valueOf(car.getEngineType()));
        table.setValueToInput("Mark", car.getMark());
        table.setValueToInput("Model", car.getModel());
        table.setValueToInput("Price", String.valueOf(car.getPrice()));
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
        return table.getResultInt();
    }
}