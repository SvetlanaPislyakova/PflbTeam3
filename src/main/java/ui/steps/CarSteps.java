package ui.steps;

import io.qameta.allure.Step;
import ui.dto.Car;
import ui.pages.BuyOrSaleCarPage;
import ui.pages.CreateCarPage;

public class CarSteps {

    private final CreateCarPage createCarPage = new CreateCarPage();
    private final BuyOrSaleCarPage buyOrSaleCarPage = new BuyOrSaleCarPage();

    @Step("Создание нового пользователя")
    public void createNewCar(Car car) {
        createCarPage.openPage()
                .isPageOpened()
                .createNewCar(car);
    }

    @Step("Покупка нового пользователя")
    public void buyNewCar() {
        buyOrSaleCarPage.openPage()
                .isPageOpened()
                .setData();
    }
}
