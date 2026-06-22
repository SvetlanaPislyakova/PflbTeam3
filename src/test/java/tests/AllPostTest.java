package tests;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.AllPostPage;

import java.math.BigDecimal;

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

    @Test
    @DisplayName("All POST - создание дома")
    public void createHouseFromAllPostPage() {
        allPostPage.createHouse(3, BigDecimal.valueOf(1_000_000));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(allPostPage.getCreateHouseStatusMessage()).contains("Successfully pushed");
            softly.assertThat(allPostPage.getCreateHouseStatusCode()).isEqualTo(201);
            softly.assertThat(allPostPage.getCreateHouseId()).isPositive();
        });
    }
}
