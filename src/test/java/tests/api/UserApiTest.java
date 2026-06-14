package tests.api;

import api.adapters.UserAdapter;
import api.models.user.UserRq;
import api.models.user.UserRs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserApiTest extends  BaseApiTest {

    @Test
    @DisplayName("API - создание нового пользователя")
    public void createUser() {
        UserRq userRq = UserRq.builder().build();
        UserRs response = UserAdapter.createUser(userRq, accessToken);
        System.out.println(accessToken);
    }

    @Test
    public void getUsers() {
        UserAdapter.getUsers();
    }

    @Test
    public void getUserById() {
        UserAdapter.getUserById(13304);
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
