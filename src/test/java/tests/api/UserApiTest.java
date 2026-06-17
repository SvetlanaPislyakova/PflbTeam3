package tests.api;

import api.adapters.UserAdapter;
import api.models.user.UserRq;
import api.models.user.UserRqFactory;
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
        UserRq userRq = UserRqFactory.validUser();
        UserRs userRs = userAdapter.createUser(userRq, accessToken);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userRs.getFirstName()).isEqualTo(userRq.getFirstName());
            softly.assertThat(userRs.getSecondName()).isEqualTo(userRq.getSecondName());
            softly.assertThat(userRs.getAge()).isEqualTo(userRq.getAge());
            softly.assertThat(userRs.getSex()).isEqualTo(userRq.getSex());
            softly.assertThat(userRs.getMoney()).isEqualByComparingTo(userRq.getMoney());
        });
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
        // TODO проверить что удалился
    }

    @Test
    @DisplayName("API - Получение пользователя по id")
    public void getUserById() {
        UserRq userRq = UserRqFactory.validUser();
        Integer userId = userAdapter.createUserAndGetId(userRq, accessToken);
        UserRs userRs = userAdapter.getUserById(userId);
        SoftAssertions.assertSoftly((softly -> {
            softly.assertThat(userRs.getFirstName()).isEqualTo(userRq.getFirstName());
            softly.assertThat(userRs.getSecondName()).isEqualTo(userRq.getSecondName());
            softly.assertThat(userRs.getAge()).isEqualTo(userRq.getAge());
            softly.assertThat(userRs.getSex()).isEqualTo(userRq.getSex());
            softly.assertThat(userRs.getMoney()).isEqualByComparingTo(userRq.getMoney());
        }));
        userAdapter.deleteUser(userId, accessToken);
    }

    @Test
    @DisplayName("API - Получение несуществующего пользователя по id")
    public void getNotExistUserById() {
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
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userRs.getFirstName()).isEqualTo(newUserRq.getFirstName());
            softly.assertThat(userRs.getSecondName()).isEqualTo(newUserRq.getSecondName());
            softly.assertThat(userRs.getAge()).isEqualTo(newUserRq.getAge());
            softly.assertThat(userRs.getSex()).isEqualTo(newUserRq.getSex());
            softly.assertThat(userRs.getMoney()).isEqualByComparingTo(newUserRq.getMoney());
            softly.assertThat(userId).isEqualTo(newUserRq.getId());
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
