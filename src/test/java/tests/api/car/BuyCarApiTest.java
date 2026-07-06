package tests.api.car;

import api.adapters.CarAdapter;
import api.adapters.UserAdapter;
import api.models.car.CarRq;
import api.models.car.CarRqFactory;
import api.models.car.CarRs;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

public class BuyCarApiTest {

    private final UserAdapter userAdapter = new UserAdapter();
    private final CarAdapter carAdapter = new CarAdapter();
    SoftAssertions softly = new SoftAssertions();

    @Test
    @Owner("Akhunov Gayaz")
    @DisplayName("API - Покупка автомобиля")
    @Description("Тест проверяет создание пользователя, создание авто, покупку авто и результат покупки")
    void buyCarTest() {
        UserRq userRq = UserRqFactory
                .validUser()
                .toBuilder()
                .money(BigDecimal.valueOf(100000))
                .build();

        Integer userId = userAdapter.createUserAndGetId(userRq);

        CarRq carRq = CarRqFactory
                .validCar()
                .toBuilder()
                .price(BigDecimal.valueOf(15000))
                .build();

        CarRs createdCar = carAdapter.createCar(carRq);
        int carId = createdCar.getId();

        userAdapter.buyCar(userId, carId);
        List<CarRs> userCars = userAdapter.getUserCars(userId);

            softly.assertThat(userCars).isNotEmpty();

            boolean carFound = userCars.stream()
                    .anyMatch(car -> car.getId() == carId);
            softly.assertThat(carFound).isTrue();

            CarRs boughtCar = userCars.stream()
                    .filter(car -> car.getId() == carId)
                    .findFirst()
                    .orElse(null);

            softly.assertThat(boughtCar).isNotNull();
            if (boughtCar != null) {
                softly.assertThat(boughtCar.getMark())
                        .isEqualTo(carRq.getMark());
                softly.assertThat(boughtCar.getModel())
                        .isEqualTo(carRq.getModel());
                softly.assertThat(boughtCar.getEngineType())
                        .isEqualTo(carRq.getEngineType());
                softly.assertThat(boughtCar.getPrice())
                        .isEqualByComparingTo(carRq.getPrice());
                softly.assertAll();
            }
        }

    @Test
    @Owner("Akhunov Gayaz")
    @DisplayName("API - Покупка авто проверяет уменьшение баланса")
    @Description("Тест проверяет, что после покупки авто деньги пользователя уменьшиваются на стоимость авто")
    void buyCarCheckBalanceTest() {

        BigDecimal initialBalance = BigDecimal.valueOf(50000);
        UserRq userRq = UserRqFactory
                .validUser()
                .toBuilder()
                .money(initialBalance)
                .build();

        Integer userId = userAdapter.createUserAndGetId(userRq);

        BigDecimal carPrice = BigDecimal.valueOf(12500.50);
        CarRq carRq = CarRqFactory
                .validCar()
                .toBuilder()
                .price(carPrice)
                .build();

        CarRs createdCar = carAdapter.createCar(carRq);
        Integer carId = createdCar.getId();

        userAdapter.buyCar(userId, carId);

        BigDecimal expectedBalance = initialBalance.subtract(carPrice);

        var userInfo = userAdapter.getUserInfo(userId);

        softly.assertThat(userInfo.getMoney())
                .isEqualByComparingTo(expectedBalance);
        softly.assertAll();
    }
}