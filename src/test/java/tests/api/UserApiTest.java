package tests.api;

import api.adapters.UserAdapter;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
import api.models.user.UserRs;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ui.steps.DBSteps;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserApiTest extends  BaseApiTest {

    private final UserAdapter userAdapter = new UserAdapter();
    private final DBSteps dbSteps = new DBSteps();
    private final Faker faker = new Faker();

    private void assertUserEquals(UserRs userRs, UserRq userRq) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userRs.getFirstName()).isEqualTo(userRq.getFirstName());
            softly.assertThat(userRs.getSecondName()).isEqualTo(userRq.getSecondName());
            softly.assertThat(userRs.getAge()).isEqualTo(userRq.getAge());
            softly.assertThat(userRs.getSex()).isEqualTo(userRq.getSex());
            softly.assertThat(userRs.getMoney()).isEqualByComparingTo(userRq.getMoney());
        });
    }

    @Test
    @DisplayName("API - Создание нового пользователя")
    public void createUser() {
        UserRq userRq = UserRqFactory.validUser();
        UserRs userRs = userAdapter.createUser(userRq, accessToken);
        assertUserEquals(userRs, userRq);
        userAdapter.deleteUser(userRs.getId(), accessToken);
    }

    static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of(UserRqFactory.userWithNullFirstName()),
                Arguments.of(UserRqFactory.userWithNullSecondName()),
                Arguments.of(UserRqFactory.userWithNullAge()),
                Arguments.of(UserRqFactory.userWithNullMoney())
        );
    }

    @ParameterizedTest(name = "Создание пользователя с невалидными данными - {0}")
    @MethodSource("invalidUsers")
    void createUserWithNullFields(UserRq userRq) {
        userAdapter.createUserWithNullFields(userRq, accessToken);
    }

    @Test
    @DisplayName("API - Получение списка пользователей")
    public void getUsers() {
        List<UserRs> users = userAdapter.getUsers();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(users).isNotNull();
            softly.assertThat(users).isNotEmpty();
            for(UserRs user : users) {
                softly.assertThat(user.getId()).isNotNull();
                softly.assertThat(user.getFirstName()).isNotNull();
                softly.assertThat(user.getSecondName()).isNotNull();
                softly.assertThat(user.getSex()).isNotNull();
                softly.assertThat(user.getAge()).isNotNull();
                softly.assertThat(user.getMoney()).isNotNull();
            }
        });
    }

    @Test
    @DisplayName("API - Удаление пользователя по id")
    public void deleteUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        userAdapter.deleteUser(userId, accessToken);
        assertThat(dbSteps.isUserNotExistsInDB(userId)).isTrue();
    }

    @Test
    @DisplayName("API - Попытка удаления несуществующего пользователя")
    public void deleteNotExistingUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        userAdapter.deleteUser(userId, accessToken);
        assertThat(dbSteps.isUserNotExistsInDB(userId)).isTrue();
        userAdapter.deleteNotExistingUser(userId, accessToken);
    }

    @Test
    @DisplayName("API - Получение пользователя по id")
    public void getUserById() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        UserRs userRs = userAdapter.getUserById(userId);
        assertUserEquals(userRs, userRq);
        userAdapter.deleteUser(userId, accessToken);
    }

    @Test
    @DisplayName("API - Попытка получения несуществующего пользователя по id")
    public void getNotExistingUserById() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        userAdapter.deleteUser(userId, accessToken);
        userAdapter.getNotExistingUserById(userId);
    }

    @Test
    @DisplayName("API - Изменение пользователя")
    public void changeUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        UserRq newUserRq = UserRqFactory.validUserWithId(userId);
        UserRs userRs = userAdapter.changeUser(userId, newUserRq, accessToken);
        assertUserEquals(userRs, newUserRq);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userId).isEqualTo(newUserRq.getId());
        });
        userAdapter.deleteUser(userId, accessToken);
    }

    @Test
    @DisplayName("API - Попытка изменения несуществующего пользователя")
    public void changeNotExistingUserById() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        UserRq newUserRq = UserRqFactory.validUserWithId(userId);
        userAdapter.deleteUser(userId, accessToken);
        userAdapter.changeNotExistingUser(userId, newUserRq, accessToken);
    }

    @Test
    @DisplayName("API - Начисление денег пользователю")
    public void addMoneyToUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        BigDecimal money = BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000));
        UserRs userRs = userAdapter.addMoneyToUser(userId, money, accessToken);
        assertThat(userRq.getMoney().add(money)).isEqualByComparingTo(userRs.getMoney());
        userAdapter.deleteUser(userId, accessToken);
    }

    @Test
    @DisplayName("API - Попытка начисления денег несуществующему пользователю")
    public void addMoneyToNotExistingUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        userAdapter.deleteUser(userId, accessToken);
        BigDecimal money = BigDecimal.valueOf(faker.number().randomDouble(2, 0, 1000000));
        userAdapter.addMoneyToNotExistingUser(userId, money, accessToken);
    }

    @Test
    @DisplayName("API - Попытка начисления денег пользователю, отрицательная сумма")
    public void addInvalidMoneyToUser() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        userAdapter.addInvalidMoneyToUser(userId, BigDecimal.valueOf(-125.0236), accessToken);
        userAdapter.deleteUser(userId, accessToken);
    }

    @Test
    public void getUserCars() {
        userAdapter.getUserCars(13304);
    }

    @Test
    public void getNotExistingUserInfo() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        userAdapter.deleteUser(userId, accessToken);
        userAdapter.getNotExistingUserInfo(userId);
    }
}
