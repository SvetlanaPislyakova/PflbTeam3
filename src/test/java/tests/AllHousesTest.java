package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.AllHousesPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AllHousesTest extends BaseTest {

    @Test
    @DisplayName("Проверка открытия страницы Read all houses и кнопки Reload")
    public void testReadAllHousesPage() {

        AllHousesPage allHousesPage = new AllHousesPage().openPage();
        allHousesPage.isPageOpened()
                .clickReload();

        assertTrue(
                allHousesPage.getTable().getTableElement().isDisplayed(),
                "Таблица должна быть видна после нажатия Reload"
        );
    }
}