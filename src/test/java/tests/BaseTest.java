package tests;

import api.adapters.CarAdapter;
import api.adapters.UserAdapter;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ui.pages.AddMoneyPage;
import ui.pages.AllCarsPage;
import ui.pages.AllUsersPage;
import ui.pages.BuyOrSaleCarPage;
import ui.pages.CreateCarPage;
import ui.pages.CreateUserPage;
import ui.pages.MenuPage;
import ui.steps.CarSteps;
import ui.steps.DBSteps;
import ui.steps.LoginSteps;
import ui.steps.UserSteps;
import utils.PropertyReader;
import utils.TestListener;
import utils.TokenProvider;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Log4j2
@ExtendWith(TestListener.class)
public class BaseTest {

    protected static final String email = System.getProperty("email", PropertyReader.getProperty("email"));
    protected static final String password = System.getProperty("password", PropertyReader.getProperty("password"));
    protected String token;

    protected LoginSteps loginSteps;
    protected UserSteps userSteps;
    protected DBSteps dbSteps;
    protected CarSteps carSteps;

    protected CreateUserPage createUserPage;
    protected AllUsersPage allUsersPage;
    protected AddMoneyPage addMoneyPage;
    protected MenuPage menuPage;
    protected CreateCarPage createCarPage;
    protected AllCarsPage allCarsPage;
    protected BuyOrSaleCarPage buyOrSaleCarPage;

    protected List<Long> createdUserIds = new ArrayList<>();
    protected List<Long> createdCarIds = new ArrayList<>();
    protected final UserAdapter userAdapter = new UserAdapter();
    protected final CarAdapter carAdapter = new CarAdapter();



    @BeforeAll
    public static void setupAllure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        log.info("======================================== STARTING TEST {} " +
                        "========================================",
                testInfo.getDisplayName());
        token = TokenProvider.getAccessToken();

        Configuration.timeout = 10000;
        Configuration.baseUrl = "http://82.142.167.37:4881/";
        Configuration.clickViaJs = true;
        ChromeOptions options = new ChromeOptions();
        if (System.getProperty("headless", "true").equals("true")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        Configuration.browserCapabilities = options;

        loginSteps = new LoginSteps();
        userSteps = new UserSteps();
        dbSteps = new DBSteps();
        carSteps = new CarSteps();

        createUserPage = new CreateUserPage();
        allUsersPage = new AllUsersPage();
        addMoneyPage = new AddMoneyPage();
        menuPage = new MenuPage();
        createCarPage = new CreateCarPage();
        allCarsPage = new AllCarsPage();
        buyOrSaleCarPage = new BuyOrSaleCarPage();
    }

    @AfterEach
    public void tearDown() {
        WebDriver driver = getWebDriver();
        if (driver != null) {
            driver.quit();
        }
    }
//    public void cleanupTestData() { // для кайфа не хватает CarAdapter
//        if (!createdUserIds.isEmpty()) {  и сохранять в каждом тесте айди созданных сущностей createdUserIds.add(userID);
//            createdUserIds.forEach(userId ->
//                    userAdapter.deleteUser(userId, token)
//            );
//        }
//        if (!createdCarIds.isEmpty()) {
//            createdCarIds.forEach(carId ->
//                    carAdapter.deleteCar(carId, token)
//            );
//        }
//    }
}
