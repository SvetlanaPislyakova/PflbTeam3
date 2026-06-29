package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class AllDeletePage extends BasePage {

    private static final String DELETE_BUTTON = "//button[@value='%s']";
    private static final String DELETE_INPUT = DELETE_BUTTON + "/following-sibling::button//input";
    private static final String DELETE_STATUS = DELETE_BUTTON + "/following-sibling::button[contains(@class,'status')]";

    private static final int MAX_RETRIES = 3;
    private static final Duration TIMEOUT = Duration.ofSeconds(15);
    private static final Duration LONG_TIMEOUT = Duration.ofSeconds(30);

    @Override
    @Step("Открытие страницы ALL DELETE")
    public AllDeletePage openPage() {
        log.info("Открытие страницы ALL DELETE");
        open("/#/delete/all");

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                $x(String.format(DELETE_BUTTON, "user")).shouldBe(visible, TIMEOUT);
                log.info("Страница открыта успешно (попытка {})", attempt);
                return this;
            } catch (Exception e) {
                log.warn("Попытка {} не удалась: {}", attempt, e.getMessage());
                if (attempt == MAX_RETRIES) {
                    throw new RuntimeException("Не удалось открыть страницу после " + MAX_RETRIES + " попыток", e);
                }
            }
        }
        return this;
    }

    @Override
    @Step("Проверка открытия страницы ALL DELETE")
    public AllDeletePage isPageOpened() {
        log.info("Проверка открытия страницы ALL DELETE");
        $x(String.format(DELETE_BUTTON, "user")).shouldBe(visible, TIMEOUT);
        return this;
    }

    @Step("Удаление сущности {entity} с ID: {id}")
    public AllDeletePage deleteEntity(String entity, int id) {
        log.info("Удаление {} с ID: {}", entity, id);
        String idStr = String.valueOf(id);

        SelenideElement input = $x(String.format(DELETE_INPUT, entity));
        SelenideElement button = $x(String.format(DELETE_BUTTON, entity));
        SelenideElement status = $x(String.format(DELETE_STATUS, entity));

        input.shouldBe(Condition.interactable, TIMEOUT);
        input.setValue(idStr);
        input.shouldHave(Condition.value(idStr), TIMEOUT);
        log.info("ID {} введен для {}", id, entity);

        button.shouldBe(Condition.interactable, TIMEOUT).click();
        log.info("Кнопка DELETE {} нажата", entity);

        status.shouldHave(Condition.text("204"), LONG_TIMEOUT);
        log.info("{} с ID {} успешно удален", entity, id);

        return this;
    }

    @Step("Удаление пользователя с ID: {id}")
    public AllDeletePage deleteUser(int id) {
        return deleteEntity("user", id);
    }

    @Step("Удаление дома с ID: {id}")
    public AllDeletePage deleteHouse(int id) {
        return deleteEntity("house", id);
    }

    @Step("Удаление автомобиля с ID: {id}")
    public AllDeletePage deleteCar(int id) {
        return deleteEntity("car", id);
    }

    @Step("Получение статуса для {entity}")
    public String getStatus(String entity) {
        String status = $x(String.format(DELETE_STATUS, entity)).getText();
        log.info("Получен статус для {}: {}", entity, status);
        return status;
    }

    @Step("Получение кода статуса для {entity}")
    public int getStatusCode(String entity) {
        String statusText = getStatus(entity);
        if (statusText == null || statusText.isEmpty() || statusText.equals("Status: not pushed")) {
            return -1;
        }
        String code = statusText.replaceAll("\\D+", "");
        return code.isEmpty() ? -1 : Integer.parseInt(code);
    }

    public int getUserStatusCode() {
        return getStatusCode("user");
    }

    public int getCarStatusCode() {
        return getStatusCode("car");
    }

    public int getHouseStatusCode() {
        return getStatusCode("house");
    }

    @Step("Ожидание изменения статуса для {entity}")
    public AllDeletePage waitForStatusChange(String entity) {
        log.info("Ожидание изменения статуса для {}", entity);
        $x(String.format(DELETE_STATUS, entity))
                .shouldNotHave(Condition.text("Status: not pushed"), TIMEOUT);
        return this;
    }
}