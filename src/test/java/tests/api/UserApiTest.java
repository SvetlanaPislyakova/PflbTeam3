package tests.api;

import api.adapters.UserAdapter;
import api.models.user.UserRq;
import api.models.user.UserRs;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserApiTest extends  BaseApiTest {

    @Test
    @DisplayName("API - Создание нового пользователя")
    public void createUser() {
        UserRq userRq = UserRq.builder().build();
        UserRs userRs = UserAdapter.createUser(userRq, accessToken);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(userRs.firstName).isEqualTo(userRq.firstName);
            softly.assertThat(userRs.secondName).isEqualTo(userRq.secondName);
            softly.assertThat(userRs.age).isEqualTo(userRq.age);
            softly.assertThat(userRs.sex).isEqualTo(userRq.sex);
            softly.assertThat(userRs.money).isEqualTo(userRq.money);
        });
        UserAdapter.deleteUser(userRs.id, accessToken);
    }

    @Test
    @DisplayName("API - Получение списка пользователей")
    public void getUsers() {
        List<UserRs> users = UserAdapter.getUsers();
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
        int userId = UserAdapter.createUserAndGetId(userRq, accessToken);
        UserAdapter.deleteUser(userId, accessToken);
    }

    @Test
    @DisplayName("API - Получение пользователя по id")
    public void getUserById() {
        UserRq userRq = UserRq.builder().build();
        int userId = UserAdapter.createUserAndGetId(userRq, accessToken);
        UserRs userRs = UserAdapter.getUserById(userId);
        SoftAssertions.assertSoftly((softly -> {
            softly.assertThat(userRs.firstName).isEqualTo(userRq.firstName);
            softly.assertThat(userRs.secondName).isEqualTo(userRq.secondName);
            softly.assertThat(userRs.age).isEqualTo(userRq.age);
            softly.assertThat(userRs.sex).isEqualTo(userRq.sex);
            softly.assertThat(userRs.money).isEqualTo(userRq.money);
        }));
        UserAdapter.deleteUser(userId, accessToken);
    }

    @Test
    public void getUserCars() {
        UserAdapter.getUserCars(13304);
    }

    @Test
    public void getUserInfo() {
        UserAdapter.getUserInfo(13304);
    }
}
