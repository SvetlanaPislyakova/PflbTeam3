package tests.ui.car;

import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTest;
import ui.dto.Car;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class SellCarTest extends BaseTest {

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Продажа автомобиля пользователем")
    public void sellCarSuccess() {
        UserRq seller = UserRqFactory.validUser().toBuilder().money(BigDecimal.valueOf(10000000)).build();
        Integer sellerID = userAdapter.createUserAndGetId(seller,token);

        Car car = Car.builder().build();
        carSteps.createNewCar(car);
        int carID = carSteps.checkCreateCarAndGetId();
        createdCarIds.add(carID);

        carSteps.buyNewCar(sellerID, carID);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(carSteps.isCarBought(sellerID, carID)).isTrue();

        carSteps.sellNewCar(sellerID, carID);
        softly.assertThat(carSteps.checkStatusCode()).isEqualTo(200);
    }
}