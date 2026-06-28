package ui.steps;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import ui.dto.Car;
import ui.pages.BuyOrSaleCarPage;
import ui.pages.CreateCarPage;

public class CarSteps {

    private final CreateCarPage createCarPage = new CreateCarPage();
    private final BuyOrSaleCarPage buyOrSaleCarPage = new BuyOrSaleCarPage();

    @Step("Создание нового автомобиля")
    public void createNewCar(Car car) {
        createCarPage.openPage()
                .isPageOpened()
                .createNewCar(car);
    }

    @Step("Покупка автомобиля пользователем")
    public void buyNewCar(int userID, int carID) {
        buyOrSaleCarPage.openPage()
                .isPageOpened()
                .setData(userID, carID,"BUY");
    }

    @Step("Продажа автомобиля пользователем")
    public void sellNewCar(int userID, int carID) {
        buyOrSaleCarPage.openPage()
                .isPageOpened()
                .setData(userID, carID, "SELL");
    }

    @Step("Получение статус кода")
    public int checkStatusCode() {
        return buyOrSaleCarPage.getStatusCode();
    }

    @Step("Проверка успешности создания автомобиля и получение его id")
    public int checkCreateCarAndGetId() {
        SoftAssertions softly = new SoftAssertions();
        int carId = createCarPage.getCarId();
        softly.assertThat(createCarPage.getStatusMessage()).contains("Successfully pushed");
        softly.assertThat(carId).isPositive();
        softly.assertThat(createCarPage.getStatusCode()).isEqualTo(201);
        softly.assertAll();
        return carId;
    }

    @Step("Проверка что автомобиль куплен пользователем")
    public boolean isCarBought(int userID, int carID) {
        return checkStatusCode() == 200;
    }

}
