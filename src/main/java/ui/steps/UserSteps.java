package ui.steps;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import ui.dto.User;
import ui.pages.AllUsersPage;
import ui.pages.CreateUserPage;
import ui.pages.IssueLoanPage;
import ui.pages.ReadUserWithCarsPage;

import java.math.BigDecimal;

public class UserSteps {

    private final CreateUserPage createUserPage = new CreateUserPage();
    private final AllUsersPage allUsersPage = new AllUsersPage();
    private final IssueLoanPage issueLoanPage = new IssueLoanPage();
    private final ReadUserWithCarsPage readUserPage = new ReadUserWithCarsPage();
    private final DBSteps dbSteps = new DBSteps();

    @Step("Создание нового пользователя")
    public void createNewUser(User user) {
        createUserPage.openPage()
                .isPageOpened()
                .createNewUser(user);
    }

    @Step("Проверка успешности создания пользователя и получение его id")
    public Integer checkCreateUserAndGetId() {
        SoftAssertions softly = new SoftAssertions();
        Integer userId = createUserPage.getUserId();
        softly.assertThat(createUserPage.getStatusMessage()).contains("Successfully pushed");
        softly.assertThat(userId).isPositive();
        softly.assertThat(createUserPage.getStatusCode()).isEqualTo(201);
        softly.assertAll();
        return userId;
    }

//    private void checkSortUsersByTextFieldInDb(String field) {
//        SoftAssertions softly = new SoftAssertions();
//        List<String> temp = getListFromDb(field);
//        List<String> sortedNaturalOrder = new ArrayList<>(temp);
//        List<String> sortedReverseOrder = new ArrayList<>(temp);
//        allUsersPage.openPage()
//                .isPageOpened()
//                .getListValues(field);
//        allUsersPage.clickBtn(field);
//        sortedNaturalOrder = allUsersPage.sortNaturalOrder(sortedNaturalOrder, false);
//        softly.assertThat(allUsersPage.getListValues(field)).isEqualTo(sortedNaturalOrder);
//        allUsersPage.clickBtn("↑ " + field);
//        sortedReverseOrder = allUsersPage.sortReverseOrder(sortedReverseOrder, false);
//        softly.assertThat(allUsersPage.getListValues(field)).isEqualTo(sortedReverseOrder);
//        softly.assertAll();
//    }

    @Step("Проверка сортировки пользователей по полю {field}")
    public void checkSortUsersByNumericField(String field) {
        allUsersPage.openPage()
                .isPageOpened()
                .checkSortUsers(field, true);
    }

    @Step("Проверка сортировки пользователей по полю {field}")
    public void checkSortUsersByFixedTextField(String field) {
        allUsersPage.openPage()
                .isPageOpened()
                .checkSortUsers(field, false);
    }

    @Step("Проверка сортировки пользователей по полю {field}")
    public void checkSortUsersByTextField(String field) {
        allUsersPage.openPage()
                .isPageOpened()
                .checkSortUsersByText(field, false);
    }

    @Step("Получение кредита пользователем")
    public void checkGetCredit(Integer userId, BigDecimal money) {
        issueLoanPage.openPage()
                .isPageOpened()
                .requestALoan(userId, money);
    }

    @Step("")
    public void readUserWithCar() {
        readUserPage.openPage()
                .isPageOpened()
                .findCarsByUserId(13304);
    }
}
