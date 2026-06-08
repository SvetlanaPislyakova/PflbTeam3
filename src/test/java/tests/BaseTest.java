package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import steps.LoginSteps;
import steps.UserSteps;
import utils.PropertyReader;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BaseTest {

    protected static final String email = System.getProperty("email", PropertyReader.getProperty("email"));
    protected static final String password = System.getProperty("password", PropertyReader.getProperty("password"));
    protected LoginSteps loginSteps;
    protected UserSteps userSteps;

    @BeforeEach
    public void setUp() {
        Configuration.timeout = 10000;
        Configuration.baseUrl = "http://82.142.167.37:4881/";
        Configuration.clickViaJs = true;
        Configuration.headless = false;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        Configuration.browserCapabilities = options;

        loginSteps = new LoginSteps();
        userSteps = new UserSteps();
    }

    @AfterEach
    public void tearDown() {
        WebDriver driver = getWebDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}