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
        SoftAssertions softly = new SoftAssertions();
        User user = User.builder().build();
        createUserPage.openPage()
                .isPageOpened()
                .createNewUser(user);
        softly.assertThat(createUserPage.getStatusMessage()).contains("Successfully pushed");
        softly.assertThat(createUserPage.getUserId()).isPositive();
        softly.assertThat(createUserPage.getStatusCode()).isEqualTo(201);
        softly.assertAll();
    }

    static Stream<Arguments> sortingData() {
        return Stream.of(
                Arguments.of("ID", true),
                Arguments.of("First", false),
                Arguments.of("Last", false),
                Arguments.of("Age", true),
                Arguments.of("Sex", false),
                Arguments.of("Money", true)
        );
    }

    @ParameterizedTest(name = "Сортировка пользователей по полю {0}")
    @MethodSource("sortingData")
    public void checkSortingByField(String field, boolean isNumeric) {
        allUsersPage.openPage()
                .isPageOpened()
                .checkSortUsers(field, isNumeric);
    }

    @Test
    @DisplayName("Добавление денег пользователю")
    public void addMoney() {
        SoftAssertions softly = new SoftAssertions();
        User user = User.builder().build();
        double money = faker.number().randomDouble(2, 0, 1000000);
        createUserPage.openPage()
                .isPageOpened()
                .createNewUser(user);
        int userId = createUserPage.getUserId();
        addMoneyPage.openPage()
                .isPageOpened()
                .addMoneyToUser(userId, money);
        double result = Double.parseDouble(user.getMoney()) + money;
        result = Math.round(result * 100.0) / 100.0;
        softly.assertThat(addMoneyPage.getStatusMessage()).contains("Successfully pushed");
        softly.assertThat(addMoneyPage.getStatusCode()).isEqualTo(200);
        softly.assertThat(addMoneyPage.getUserMoney()).isEqualTo(result);
        softly.assertAll();
    }
}
