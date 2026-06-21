package tests;

import api.adapters.UserAdapter;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ui.dto.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import ui.dto.UserFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

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
        SoftAssertions softly = new SoftAssertions();
        BigDecimal money = BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000));
        addMoneyPage.openPage()
                .isPageOpened()
                .addMoneyToUser(userId, money);
        BigDecimal result = userRq.getMoney().add(money);
        softly.assertThat(addMoneyPage.getStatusMessage()).contains("Successfully pushed");
        softly.assertThat(addMoneyPage.getStatusCode()).isEqualTo(200);
        softly.assertThat(addMoneyPage.getUserMoney()).isEqualByComparingTo(result);
        softly.assertAll();
        userAdapter.deleteUser(userId, token);
    }

    @Test
    @Disabled("Не работает")
    @DisplayName("Запросить кредит")
    public void issueALoan() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        BigDecimal money = BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000));
        userSteps.checkGetCredit(userId, money);
        userAdapter.deleteUser(userId, token);
    }

    @Test
    @DisplayName("Получение списка автомобилей пользователя (у пользователя нет машины)")
    public void readUserWithNoCar() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        userSteps.checkUserHaveNoCars(userId);
        userAdapter.deleteUser(userId, token);
    }

    @Test
    @DisplayName("Получение списка автомобилей пользователя (у пользователя 1 машина)")
    public void readUserWithOneCar() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, token);
        userSteps.checkUserCars(13304, List.of(54));
        userAdapter.deleteUser(userId, token);
    }
}
