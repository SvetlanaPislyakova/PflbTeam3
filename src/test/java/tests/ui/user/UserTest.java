package tests.ui.user;

import api.adapters.UserAdapter;
import api.models.car.CarRq;
import api.models.car.CarRqFactory;
import api.models.car.CarRs;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import tests.ui.base.BaseTest;
import ui.dto.User;
import ui.dto.UserFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Description("Проверка создания нового пользователя")
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

    @DisplayName("Создание пользователя с невалидными данными")
    @ParameterizedTest(name = "Создание пользователя с невалидными данными - {0}")
    @MethodSource("invalidUsers")
    @Description("Проверка создания пользователя с невалидными данными")
    public void createInvalidUser(User user) {
        userSteps.createNewUser(user);
        userSteps.checkMessageContainsText("Invalid request data");
    }

    @ParameterizedTest(name = "Сортировка пользователей по полю {0}")
    @ValueSource(strings = {"First name", "Last name"})
    @Description("Проверка сортировки списка пользователей")
    public void checkSortingByTextField(String field) {
        userSteps.checkSortUsersByTextField(field);
    }

    @ParameterizedTest(name = "Сортировка пользователей по полю {0}")
    @ValueSource(strings = {"ID", "Age", "Money"})
    @Description("Проверка сортировки списка пользователей")
    public void checkSortingByNumericField(String field) {
        userSteps.checkSortUsersByNumericField(field);
    }

    @ParameterizedTest(name = "Сортировка пользователей по полю {0}")
    @ValueSource(strings = {"Sex"})
    @Description("Проверка сортировки списка пользователей")
    public void checkSortingByFixedTextField(String field) {
        userSteps.checkSortUsersByFixedTextField(field);
    }

    @Test
    @DisplayName("Добавление денег пользователю")
    @Description("Проверка добавления денег пользователю")
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
    @Description("Проверка выдачи кредита пользователю")
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
    @Description("Проверка получения списка автомобилей пользователя, у пользователя  нет автомобиля")
    public void readUserWithNoCar() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        userSteps.checkUserHaveNoCars(userId);
        userSteps.checkUserExistsInDb(userId);
        userAdapter.deleteUser(userId, token);
    }

    @ParameterizedTest(name = "Получение списка автомобилей пользователя - машин у пользователя: {0}")
    @DisplayName("Получение списка автомобилей пользователя")
    @ValueSource(ints = {1, 3, 5})
    @Description("Проверка получения списка автомобилей пользователя, у пользователя  несколько машин")
    public void readUserWithCars(int amount) {
        UserRq userRq = UserRqFactory
                .validUser()
                .toBuilder()
                .money(BigDecimal.valueOf(1000000))
                .build();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        List<Integer> carIds = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            CarRq carRq = CarRqFactory
                    .validCar()
                    .toBuilder()
                    .price(BigDecimal.valueOf(10000))
                    .build();
            CarRs car = carAdapter.createCar(carRq, token);
            userAdapter.buyCar(userId, car.getId(), token);
            carIds.add(car.getId());
        }
        userSteps.checkUserCars(userId, carIds);
        for (Integer carId : carIds) {
            userAdapter.sellCar(userId, carId, token);
            carAdapter.deleteCar(carId, token);
        }
        userAdapter.deleteUser(userId, token);
    }

    @Test
    @DisplayName("UI - Удаление пользователя через страницу All DELETE")
    public void deleteUserThroughUI() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        allDeletePage.openPage()
                .isPageOpened()
                .deleteUser(userId);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(allDeletePage.getUserStatusCode()).isEqualTo(204);
        softly.assertThat(dbSteps.isUserExistsInDB(userId)).isFalse();
        softly.assertAll();
    }
}