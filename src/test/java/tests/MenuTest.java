package tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ui.pages.*;
import ui.wrappers.DropDown;

public class MenuTest extends BaseTest {

    private final MenuPage menuPage = new MenuPage();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @ParameterizedTest(name = "Проверка открытия страницы {0}")
    @EnumSource(MenuPage.MenuOption.class)
    @Description("Проверка переходов по основным разделам сайта")
    public void checkOpeningPage(MenuPage.MenuOption menuOption) {
        new DropDown(menuOption.getDropdown()).selectOption(menuOption.getOption());
        menuOption.getPage().isPageOpened();
    }

    @Test
    @DisplayName("Проверка открытия страницы ALL_POST")
    @Description("Проверка открытия страницы ALL_POST")
    public void openAllPost() {
        menuPage.openAllPostPage()
                .isPageOpened();
    }

    @Test
    @DisplayName("Проверка открытия страницы ALL_DELETE")
    @Description("Проверка открытия страницы ALL_DELETE")
    public void openAllDelete() {
        menuPage.openAllDeletePage()
                .isPageOpened();
    }
}