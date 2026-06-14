package tests.api;

import api.adapters.UserAdapter;
import api.models.user.UserRq;
import api.models.user.UserRs;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class UserApiTest extends  BaseApiTest {

    UserAdapter userAdapter = new UserAdapter();

    @Test
    @DisplayName("API - Создание нового пользователя")
    public void createUser() {
        UserRq userRq = UserRq.builder().build();
        UserRs userRs = userAdapter.createUser(userRq, accessToken);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userRs.firstName).isEqualTo(userRq.firstName);
            softly.assertThat(userRs.secondName).isEqualTo(userRq.secondName);
            softly.assertThat(userRs.age).isEqualTo(userRq.age);
            softly.assertThat(userRs.sex).isEqualTo(userRq.sex);
            softly.assertThat(userRs.money).isEqualByComparingTo(userRq.money);
        });
        userAdapter.deleteUser(userRs.id, accessToken);
    }

    static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of(UserRq.builder().firstName(null).build()),
                Arguments.of(UserRq.builder().secondName(null).build()),
                Arguments.of(UserRq.builder().age(null).build()),
                Arguments.of(UserRq.builder().money(null).build())
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
                softly.assertThat(user.id).isNotNull();
                softly.assertThat(user.firstName).isNotNull();
                softly.assertThat(user.secondName).isNotNull();
                softly.assertThat(user.sex).isNotNull();
                softly.assertThat(user.age).isNotNull();
                softly.assertThat(user.money).isNotNull();
            }
        });
    }

    @Test
    @DisplayName("API - Удаление пользователя по id")
    public void deleteUser() {
        UserRq userRq = UserRq.builder().build();
        int userId = userAdapter.createUserAndGetId(userRq, accessToken);
        userAdapter.deleteUser(userId, accessToken);
    }

    @Test
    @DisplayName("API - Получение пользователя по id")
    public void getUserById() {
        UserRq userRq = UserRq.builder().build();
        int userId = userAdapter.createUserAndGetId(userRq, accessToken);
        UserRs userRs = userAdapter.getUserById(userId);
        SoftAssertions.assertSoftly((softly -> {
            softly.assertThat(userRs.firstName).isEqualTo(userRq.firstName);
            softly.assertThat(userRs.secondName).isEqualTo(userRq.secondName);
            softly.assertThat(userRs.age).isEqualTo(userRq.age);
            softly.assertThat(userRs.sex).isEqualTo(userRq.sex);
            softly.assertThat(userRs.money).isEqualByComparingTo(userRq.money);
        }));
        userAdapter.deleteUser(userId, accessToken);
    }

    @Test
    @DisplayName("API - Получение несуществующего пользователя по id")
    public void getNotExistUserById() {
        UserRq userRq = UserRq.builder().build();
        int userId = userAdapter.createUserAndGetId(userRq, accessToken);
        userAdapter.deleteUser(userId, accessToken);
        userAdapter.getNotExistingUserById(userId);
    }

    @Test
    @DisplayName("API - Изменение пользователя")
    public void changeUser() {
        UserRq userRq = UserRq.builder().build();
        int userId = userAdapter.createUserAndGetId(userRq, accessToken);
        UserRq newUserRq = UserRq.builder().id(userId).build();
        UserRs userRs = userAdapter.changeUser(userId, newUserRq, accessToken);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userRs.firstName).isEqualTo(newUserRq.firstName);
            softly.assertThat(userRs.secondName).isEqualTo(newUserRq.secondName);
            softly.assertThat(userRs.age).isEqualTo(newUserRq.age);
            softly.assertThat(userRs.sex).isEqualTo(newUserRq.sex);
            softly.assertThat(userRs.money).isEqualByComparingTo(newUserRq.money);
            softly.assertThat(userId).isEqualTo(newUserRq.id);
        });
        userAdapter.deleteUser(userId, accessToken);
    }

    @Test
    public void getUserCars() {
        userAdapter.getUserCars(13304);
    }

    @Test
    public void getUserInfo() {
        userAdapter.getUserInfo(13304);
    }
}
