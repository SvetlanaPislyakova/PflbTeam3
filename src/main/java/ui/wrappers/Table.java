package ui.wrappers;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Log4j2
public class Table {

    private final String firstColumn;
    private final String secondColumn;
    private final String PATTERN = "//*[th[contains(text(), '%s')] and th[contains(text(), '%s')]]/ancestor::table";

    public Table(String tableName) {
        switch (tableName) {
            case "Read all users":
            case "Create new user":
                this.firstColumn = "Age";
                this.secondColumn = "Sex";
                break;
            case "Add money":
                this.firstColumn = "User ID";
                this.secondColumn = "Money";
                break;
            case "Buy or sell car":
                this.firstColumn = "User ID";
                this.secondColumn = "Car Id";
                break;
            case "Settle to house":
                this.firstColumn = "User ID";
                this.secondColumn = "House ID";
                break;
            case "Issue a loan":
                this.firstColumn = "User ID:";
                this.secondColumn = "Размер кредита:";
                break;
            case "Read all cars":
            case "Create new car":
                this.firstColumn = "Engine\u00A0Type";
                this.secondColumn = "Mark";
                break;
            case "Read all houses:":
            case "Parkings:":
                this.firstColumn = "isWarm:";
                this.secondColumn = "isCovered:";
                break;
            case "House info:":
                this.firstColumn = "Floor\u00A0Count:";
                this.secondColumn = "Price:";
                break;
            case "Create new house":
                this.firstColumn = "Floors";
                this.secondColumn = "Price";
                break;
            case "Lodgers:":
                this.firstColumn = "ID:";
                this.secondColumn = "First\u00A0name:";
                break;
            default:
                throw new IllegalArgumentException("Неизвестная таблица");
        }
    }

    public void checkTableVisible() {
        $x(String.format(PATTERN, firstColumn, secondColumn)).shouldBe(visible);
    }

    private int findColumnIndex(String label) {
        List<String> headers = $$x(String.format(PATTERN + "//th", firstColumn, secondColumn)).texts();
        for (int i = 0; i < headers.size(); i++) {
            String normHeader = headers.get(i).replaceAll("\\u00A0", " ");
            if (normHeader.contains(label)) {
                return i;
            }
        }
        throw new AssertionError("Заголовок не найден");
    }

    public void setValueToInput(String label, String value) {
        log.info("Заполнить поле '{}' значением '{}'", label, value);
        sleep(300);
        int columnIndex = findColumnIndex(label) + 1;
        SelenideElement input = $x(String.format(PATTERN + "//tbody//td[" + columnIndex + "]/input",
                firstColumn, secondColumn));
        input.shouldBe(visible).shouldBe(enabled).setValue(value);
    }

    public String getValueFromInput(String label) {
        int columnIndex = findColumnIndex(label) + 1;
        return $x(String.format(PATTERN + "//tbody//td[" + columnIndex + "]/input",
                firstColumn, secondColumn)).getValue();
    }

    public String getValueFromCell(String label) {
        int columnIndex = findColumnIndex(label) + 1;
        log.info("Получить значение из ячейки '{}'", label);
        return $x(String.format(PATTERN + "//tbody//td[" + columnIndex + "]",
                firstColumn, secondColumn)).getText();
    }

    public List<String> getListOfValues (String label) {
        log.info("Получить список значений из столбца '{}'", label);
        int columnIndex = findColumnIndex(label) + 1;
        ElementsCollection listOfValues = $$x(String.format(PATTERN + "//tbody//td[" + columnIndex + "]",
                firstColumn, secondColumn));
        listOfValues.shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1));
        return listOfValues.texts();
    }

    public void rowsShouldBeEmpty () {
        log.info("Проверить, что в таблице нет строк");
        $$x(String.format(PATTERN + "//tbody/tr", firstColumn, secondColumn))
                .shouldBe(CollectionCondition.empty);
    }

    public void clickPushToApiBtn() {
        log.info("Нажать на кнопку 'Push to api'");
        $x(String.format(PATTERN + "/parent::div/div/button[contains(@class, 'tableButton')]",
                firstColumn, secondColumn)).click();
        SelenideElement message = $x(String.format(PATTERN + "/parent::div//button[contains(@class, 'status')]",
                firstColumn, secondColumn));
        message.shouldNotHave(text("Status: not pushed"));
    }

    public void clickIssueLoanBtn() {
        log.info("Нажать на кнопку 'Запросить кредит'");
        $x(String.format(PATTERN + "/parent::div/div/button[contains(@class, 'tableButton')]",
                firstColumn, secondColumn)).click();
        SelenideElement message = $x(String.format(PATTERN + "/parent::div//button[contains(@class, 'status')]",
                firstColumn, secondColumn));
        message.shouldNotHave(text("Status: not pushed"), Duration.ofSeconds(120));
    }

    public String getMessagePushToApi() {
        SelenideElement message = $x(String.format(PATTERN + "/parent::div//button[contains(@class, 'status')]",
                firstColumn, secondColumn));
        message.shouldNotHave(text("Status: not pushed"));
        return message.getText();
    }

    public Integer getStatus() {
        log.info("Получить статус выполнения операции");
        String message = $x(String.format(PATTERN + "/parent::div//button[contains(@class, 'status')]",
                firstColumn, secondColumn)).shouldHave(matchText(".*\\d+.*")).getText();
        return Integer.parseInt(message.replaceAll("\\D+", ""));
    }

    public Integer getResultInt() {
        log.info("Получить целочисленный результат");
        String messageResult = $x(String.format(PATTERN + "/parent::div/div/button[3]",
                firstColumn, secondColumn)).shouldNotBe(empty).getText();
        String result = messageResult.replaceAll("\\D+", "");
        return Integer.parseInt(result);
    }

    public Double getResultDouble() {
        log.info("Получить результат с плавающей точкой");
        String messageResult = $x(String.format(PATTERN + "/parent::div/div/button[3]",
                firstColumn, secondColumn)).shouldNotBe(empty).getText();
        return Double.parseDouble(messageResult.replaceAll("[^\\d.]", ""));
    }

    public SelenideElement getTableElement() {
        log.info("Получить элемент таблицы");
        return $x(String.format(PATTERN, firstColumn, secondColumn));
    }
}
