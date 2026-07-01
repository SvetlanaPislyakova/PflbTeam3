package tests.ui.house;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.ui.base.BaseTest;
import ui.pages.AllHousesPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AllHousesTest extends BaseTest {

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @Description("Проверка открытия страницы Read all houses и кнопки Reload")
    public void testReadAllHousesPage() {

        AllHousesPage allHousesPage = new AllHousesPage().openPage();
        allHousesPage.isPageOpened()
                .clickReload();

        assertTrue(
                allHousesPage.getTable().getTableElement().isDisplayed(),
                "Таблица должна быть видна после нажатия Reload");
    }
}
