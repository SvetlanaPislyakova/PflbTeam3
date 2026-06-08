package steps;

import dto.User;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import pages.AllUsersPage;
import pages.CreateUserPage;
import wrappers.Input;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserSteps {

    CreateUserPage createUserPage = new CreateUserPage();
    AllUsersPage allUsersPage = new AllUsersPage();

    @Step("Create new user")
    public void createUser(User user) {
        createUserPage.openPage()
                .isPageOpened()
                .fillTableCreateUser(user)
                .pushToApiBtnClick();
        sleep(5000);
    }

    public void checkMessage() {

    }

    public void getUserIds() {
        allUsersPage.openPage()
                .isPageOpened();
        sleep(5000);
        System.out.println(new Input("First Name", "ID").getValues());
    }

    public void sortUsers (String tableName, String field) {
        allUsersPage.openPage()
                .isPageOpened();
        sleep(2000);
        List<String> first = new Input(tableName, field).getValues();
        System.out.println(first);
        first.sort(Comparator.comparingInt(Integer::parseInt));
        System.out.println(first);
        $x(String.format("//button[contains(text(), '%s')]", field)).click();
        List<String> sorted = new Input(tableName, field).getValues();
        System.out.println(sorted);
        assertThat(first).isEqualTo(sorted);
        $x(String.format("//button[contains(., '%s')]", field)).click();
        sleep(2000);
        List<String> sorted2 = new Input(tableName, field).getValues();
        first.sort(Collections.reverseOrder(Comparator.comparingInt(Integer::parseInt)));
        System.out.println(sorted2);
        System.out.println(first);
        assertThat(first).isEqualTo(sorted2);
    }


}
