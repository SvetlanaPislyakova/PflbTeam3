package tests;

import com.github.javafaker.Faker;
import ui.dto.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class UserTest extends BaseTest {

    private final Faker faker = new Faker();

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
        Long userId = userSteps.checkCreateUserAndGetId();
        dbSteps.checkUserInDB(user, userId);
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

    @Test
    @DisplayName("Добавление денег пользователю")
    public void addMoney() {
        SoftAssertions softly = new SoftAssertions();
        User user = User.builder().build();
        BigDecimal money = BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000));
        createUserPage.openPage()
                .isPageOpened()
                .createNewUser(user);
        Long userId = createUserPage.getUserId();
        addMoneyPage.openPage()
                .isPageOpened()
                .addMoneyToUser(userId, money);
        BigDecimal result = user.getMoney().add(money);
        softly.assertThat(addMoneyPage.getStatusMessage()).contains("Successfully pushed");
        softly.assertThat(addMoneyPage.getStatusCode()).isEqualTo(200);
        softly.assertThat(addMoneyPage.getUserMoney()).isEqualTo(result);
        softly.assertAll();
    }

//    @Test
//    @DisplayName("Создание нового пользователя")
//    public void create() {
//        User user = User.builder().build();
//        userSteps.checkUserInDB(user, 194L);
//        userSteps.checkSortUsers("First name", false);
//        new Button("First Name").clickBtn();
//    }

//    @Test
//    @DisplayName("Создание нового пользователя")
//    public void request() {
//        userSteps.checkGetCredit();
//
//    }
}
