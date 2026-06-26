package ui.steps;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import ui.dto.User;
import ui.pages.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserSteps {

    private final CreateUserPage createUserPage = new CreateUserPage();
    private final AllUsersPage allUsersPage = new AllUsersPage();
    private final IssueLoanPage issueLoanPage = new IssueLoanPage();
    private final ReadUserWithCarsPage readUserPage = new ReadUserWithCarsPage();
    private final AddMoneyPage addMoneyPage = new AddMoneyPage();
    private final DBSteps dbSteps = new DBSteps();

    @Step("Создание нового пользователя")
    public void createNewUser(User user) {
        createUserPage.openPage()
                .isPageOpened()
                .createNewUser(user);
    }

    @Step("Проверить, что сообщение содержит текст - {text}")
    public void checkMessageContainsText(String text) {
        assertThat(createUserPage.getStatusMessage()).contains(text);
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

    @Step("Проверить, что у пользователя есть машины с id = {carsId}")
    public void checkUserCars(Integer userId, List<Integer> carsId) {
        readUserPage.openPage()
                .isPageOpened()
                .findCarsByUserId(userId)
                .checkUserInfo(userId)
                .checkCarsInfo(carsId);
    }

    @Step("Проверить, что у пользователя нет машин")
    public void checkUserHaveNoCars(Integer userId) {
        readUserPage.openPage()
                .isPageOpened()
                .findCarsByUserId(userId)
                .checkUserInfo(userId)
                .checkEmptyCarsInfo();
    }

    public void checkUserExistsInDb(Integer userId) {
        assertThat(dbSteps.isUserExistsInDB(userId)).isTrue();
    }

    @Step("Проверить добавление денег пользователю")
    public void checkAddingMoneyToUser(Integer userId, BigDecimal money, BigDecimal result) {
        SoftAssertions softly = new SoftAssertions();
        addMoneyPage.openPage()
                .isPageOpened()
                .addMoneyToUser(userId, money);
        softly.assertThat(addMoneyPage.getStatusMessage()).contains("Successfully pushed");
        softly.assertThat(addMoneyPage.getStatusCode()).isEqualTo(200);
        softly.assertThat(addMoneyPage.getUserMoney()).isEqualByComparingTo(result);
        softly.assertAll();
    }
}
