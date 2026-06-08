package tests;

import dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest extends BaseTest {

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    public void createUser() {
        User user = User.builder().build();
        userSteps.createUser(user);

    }

    @Test
    public void check() {
        userSteps.sortUsers("First Name", "ID");
    }
}
