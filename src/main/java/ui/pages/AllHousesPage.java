package ui.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ui.wrappers.Table;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

@Log4j2
public class AllHousesPage extends BasePage {
    private final String tableName = "Read all houses:";
    @Getter
    private final Table table = new Table(tableName);

    @Override
    @Step("Открытие страницы 'Read all houses'")
    public AllHousesPage openPage() {
        log.info("Открыть страницу '{}'", tableName);
        open(baseUrl + "#/read/houses");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы 'Read all houses'")
    public AllHousesPage isPageOpened() {
        log.info("Проверить, что страница '{}' открыта", tableName);
        table.checkTableVisible();
        return this;
    }

    @Step("Нажатие на кнопку Reload")
    public AllHousesPage clickReload() {
        log.info("Нажать на кнопку Reload");
        new ui.wrappers.Button("Reload").clickBtn();
        return this;
    }
}
