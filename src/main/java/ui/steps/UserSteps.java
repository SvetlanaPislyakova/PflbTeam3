package ui.steps;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import ui.dto.User;
import ui.pages.AllUsersPage;
import ui.pages.CreateUserPage;
import ui.pages.IssueLoanPage;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserSteps {

    private final CreateUserPage createUserPage = new CreateUserPage();
    private final AllUsersPage allUsersPage = new AllUsersPage();
    private final IssueLoanPage issueLoanPage = new IssueLoanPage();
    private final DBSteps dbSteps = new DBSteps();

    @Step("Создание нового пользователя")
    public void createNewUser(User user) {
        createUserPage.openPage()
                .isPageOpened()
                .createNewUser(user);
    }

    @Step("Проверка успешности создания пользователя и получение его id")
    public Long checkCreateUserAndGetId() {
        SoftAssertions softly = new SoftAssertions();
        Long userId = createUserPage.getUserId();
        softly.assertThat(createUserPage.getStatusMessage()).contains("Successfully pushed");
        softly.assertThat(userId).isPositive();
        softly.assertThat(createUserPage.getStatusCode()).isEqualTo(201);
        softly.assertAll();
        return userId;
    }

    @Step("Проверка сортировки пользователей по полю {field}")
    public void checkSortUsers(String field, boolean isNumeric) {
        allUsersPage.openPage()
                .isPageOpened()
                .checkSortUsers(field, isNumeric);
    }

    private List<String> getListFromDb(String field) {
        if (field.equals("First name"))
            return dbSteps.getListFromDB("person", "first_name");
        else if(field.equals("Last name"))
            return dbSteps.getListFromDB("person", "second_name");
        return null;
    }

    @Step("Проверка сортировки пользователей по полю 'First name'")
    public void checkSortUsersInDb(String field, String btnName) {
        SoftAssertions softly = new SoftAssertions();
        List<String> temp = getListFromDb(field);
        List<String> sortedNaturalOrder = new ArrayList<>(temp);
        List<String> sortedReverseOrder = new ArrayList<>(temp);
        allUsersPage.openPage()
                .isPageOpened()
                .getListValues(field);

        allUsersPage.clickBtn(btnName);
        sortedNaturalOrder = allUsersPage.sortNaturalOrder(sortedNaturalOrder, false);
        softly.assertThat(allUsersPage.getListValues(field)).isEqualTo(sortedNaturalOrder);

        allUsersPage.clickBtn(btnName);
        sortedReverseOrder = allUsersPage.sortReverseOrder(sortedReverseOrder, false);
        softly.assertThat(allUsersPage.getListValues(field)).isEqualTo(sortedReverseOrder);
        softly.assertAll();
    }

    @Step("Получение кредита пользователем")
    public void checkGetCredit() {
        issueLoanPage.openPage()
                .isPageOpened()
                .requestALoan();
    }

    @Step("Проверка статуса получения кредита")
    public void getCreditStatus() {
        issueLoanPage.requestALoan();

    }
}
