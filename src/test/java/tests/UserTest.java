package tests;

import api.adapters.UserAdapter;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Disabled;
import ui.dto.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.TokenProvider;

import java.math.BigDecimal;
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
        User user = User.builder().build();
        userSteps.createNewUser(user);
        Integer userId = userSteps.checkCreateUserAndGetId();
        dbSteps.checkUserInDB(user, userId);
        userAdapter.deleteUser(userId, TokenProvider.getAccessToken());
    }

    static Stream<Arguments> sortingData() {
        return Stream.of(
                Arguments.of("ID", true),
                Arguments.of("Age", true),
                Arguments.of("Sex", false),
                Arguments.of("Money", true)
        );
    }

    @ParameterizedTest(name = "Сортировка пользователей по полю {0}")
    @MethodSource("sortingData")
    public void checkSortingByField(String field, boolean isNumeric) {
        userSteps.checkSortUsers(field, isNumeric);
    }


    static Stream<Arguments> sortingData2() {
        return Stream.of(
                Arguments.of("First name", "First Name"),
                Arguments.of("Last name", "Last Name")
        );
    }

    @ParameterizedTest(name = "Сортировка пользователей по полю {0}")
    @MethodSource("sortingData2")
    public void checkSortingByFieldInDb(String field, String btnName) {
        userSteps.checkSortUsersInDb(field, btnName);
    }


    //TODO Сортировка????


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
        softly.assertThat(addMoneyPage.getUserMoney()).isEqualTo(result);
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
    @DisplayName("Получение списка автомобилей пользователя")
    public void readUserWithCar() {
        //добавить пользователя
        //добавить пользователю машину
        userSteps.readUserWithCar();
    }
}
