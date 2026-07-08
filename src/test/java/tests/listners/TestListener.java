package tests.listners;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import utils.AllureUtils;

import java.util.Optional;

@Log4j2
public class TestListener implements TestWatcher {

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        log.warn("======================================== SKIPPED TEST {} ========================================",
                context.getDisplayName());
        reason.ifPresent(r -> log.warn("Reason: {}", r));
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info("======================================== FINISHED TEST {} ========================================",
                context.getDisplayName());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        log.warn("======================================== ABORTED TEST {} ========================================",
                context.getDisplayName(), cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.error("======================================== FAILED TEST {} ========================================",
                context.getDisplayName(), cause);
        try {
            Object testInstance = context.getRequiredTestInstance();
            WebDriver driver = (WebDriver) testInstance.getClass().getMethod("getDriver").invoke(testInstance);
            if (driver != null) {
                AllureUtils.takeScreenshot(driver);
            }
        } catch (Exception e) {
            log.error("Could not take screenshot", e);
        }
    }
}
