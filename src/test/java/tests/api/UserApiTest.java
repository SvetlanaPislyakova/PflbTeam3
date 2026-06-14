package tests.api;

import api.adapters.UserAdapter;
import api.models.UserRq;
import api.models.UserRs;
import org.junit.jupiter.api.Test;

public class UserApiTest extends  BaseApiTest {

    @Test
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
