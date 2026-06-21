package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ui.pages.*;
import ui.wrappers.DropDown;

import java.util.Map;

public class MainPageTest extends BaseTest {

    private final AllUsersPage allUsersPage = new AllUsersPage();
    private final CreateUserPage createUserPage = new CreateUserPage();
    private final ReadUserWithCarsPage readUserWithCarsPage = new ReadUserWithCarsPage();
    private final AddMoneyPage addMoneyPage = new AddMoneyPage();
    private final IssueLoanPage issueLoanPage = new IssueLoanPage();
    private final MainPage mainPage = new MainPage();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    private final Map<String, BasePage> pages = Map.of(
            "AllUsersPage", allUsersPage,
            "CreateUserPage", createUserPage,
            "ReadUserWithCarsPage", readUserWithCarsPage,
            "AddMoneyPage", addMoneyPage,
            "IssueLoanPage", issueLoanPage
    );

    @ParameterizedTest(name = "Проверка открытия страницы {2}")
    @CsvSource({
            "Users, Read all, AllUsersPage",
            "Users, Create new, CreateUserPage",
            "Users, Read user with cars, ReadUserWithCarsPage",
            "Users, Add money, AddMoneyPage",
            "Users, Issue a loan, IssueLoanPage"
    })
    public void checkOpeningPage(String dropDawn, String option, String pageName) {
        new DropDown(dropDawn).selectOption(option);
        pages.get(pageName).isPageOpened();
    }

    @Test
    public void openAllPost() {
        mainPage.openAllPostPage();
    }
}
