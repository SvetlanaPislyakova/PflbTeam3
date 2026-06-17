package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.AllPostPage;

public class AllPostTest extends BaseTest {

    private final AllPostPage allPostPage = new AllPostPage();

    @BeforeEach
    public void openAllPost() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
        allPostPage.openPage()
                .isPageOpened();
    }

    @Test
    @DisplayName("All POST - отображение всех POST-форм")
    public void checkAllPost() {
        allPostPage.isPageOpened();
    }
}
