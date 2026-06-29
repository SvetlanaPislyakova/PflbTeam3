package tests;

import api.adapters.CarAdapter;
import api.adapters.UserAdapter;
import api.models.CarRq;
import api.models.CarRs;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ui.dto.User;
import ui.dto.UserFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest extends BaseTest {

    private final Faker faker = new Faker();
    private final UserAdapter userAdapter = new UserAdapter();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void createUser() {
        User user = UserFactory.validUser();
        userSteps.createNewUser(user);
        Integer userId = userSteps.checkCreateUserAndGetId();
        dbSteps.checkUserInDB(user, userId);
        userAdapter.deleteUser(userId, token);
    }

    static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of(UserFactory.userWithNullFirstName()),
                Arguments.of(UserFactory.userWithNullLastName()),
                Arguments.of(UserFactory.userWithNullAge()),
                Arguments.of(UserFactory.userWithNullMoney()),
                Arguments.of(UserFactory.userWithNullSex())
        );
    }

    @ParameterizedTest(name = "Создание пользователя с невалидными данными - {0}")
    @MethodSource("invalidUsers")
    public void createInvalidUser(User user) {
        userSteps.createNewUser(user);
        userSteps.checkMessageContainsText("Invalid request data");
    }

    @ParameterizedTest(name = "Сортировка пользователей по полю {0}")
    @ValueSource(strings = {"First name", "Last name"})
    public void checkSortingByTextField(String field) {
        userSteps.checkSortUsersByTextField(field);
    }

    @ParameterizedTest(name = "Сортировка пользователей по полю {0}")
    @ValueSource(strings = {"ID", "Age", "Money"})
    public void checkSortingByNumericField(String field) {
        userSteps.checkSortUsersByNumericField(field);
    }

    @ParameterizedTest(name = "Сортировка пользователей по полю {0}")
    @ValueSource(strings = {"Sex"})
    public void checkSortingByFixedTextField(String field) {
        userSteps.checkSortUsersByFixedTextField(field);
    }

    @Test
    @DisplayName("Добавление денег пользователю")
    public void addMoney() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        BigDecimal money = BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000));
        BigDecimal result = userRq.getMoney().add(money);
        userSteps.checkAddingMoneyToUser(userId, money, result);
        userAdapter.deleteUser(userId, token);
    }

    @Test
    @Disabled("Временно отключен, чиним")
    @DisplayName("Выдача кредита пользователю")
    public void issueALoan() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        BigDecimal initialBalance = userRq.getMoney();

        BigDecimal loanAmount = BigDecimal.valueOf(faker.number().randomDouble(2, 100, 10000));

        userSteps.checkGetCredit(userId, loanAmount);

        BigDecimal expectedBalance = initialBalance.add(loanAmount);
        BigDecimal actualBalance = dbSteps.getUserBalance(userId);

        assertThat(actualBalance).isEqualByComparingTo(expectedBalance);

        userAdapter.deleteUser(userId, token);
    }

    @Test
    @DisplayName("Получение списка автомобилей пользователя (у пользователя нет машины)")
    public void readUserWithNoCar() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        userSteps.checkUserHaveNoCars(userId);
        userSteps.checkUserExistsInDb(userId);
        userAdapter.deleteUser(userId, token);
    }

    @Test
    @DisplayName("Получение списка автомобилей пользователя (у пользователя 1 машина)")
    public void readUserWithOneCar() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);

        CarAdapter carAdapter = new CarAdapter();
        CarRq carRq = CarRq.builder()
                .mark("Toyota")
                .model("Camry")
                .engineType("Diesel")
                .price(BigDecimal.valueOf(25000.00))
                .build();
        CarRs carRs = carAdapter.createCar(carRq, token);
        Integer carId = carRs.getId();

        userAdapter.buyCar(userId, carId, token);
        userSteps.checkUserCars(userId, List.of(carId));
        userAdapter.sellCar(userId, carId, token);
        carAdapter.deleteCar(carId, token);
        userAdapter.deleteUser(userId, token);
    }

    @Test
    @DisplayName("UI - Удаление пользователя через страницу All DELETE")
    public void deleteUserThroughUI() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        List<CarRs> cars = userAdapter.getUserCars(userId);
        if (!cars.isEmpty()) {
            // Если есть — удаляем их
            for (CarRs car : cars) {
                userAdapter.sellCar(userId, car.getId(), token);
                carAdapter.deleteCar(car.getId(), token);
            }
        }
        allDeletePage.openPage()
                .isPageOpened()
                .deleteUser(userId);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(allDeletePage.getUserStatusCode()).isEqualTo(204);
        softly.assertThat(dbSteps.isUserExistsInDB(userId)).isFalse();
        softly.assertAll();
    }
}