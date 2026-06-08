package wrappers;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Table {

    private final String tableName;
    private final String PATTERN = "//table//th[2][contains(text(), '%s')]/ancestor::table";

    public Table(String tableName) {
        switch (tableName) {
            case "User":
                this.tableName = "First";
                break;
            case "Add money":
                this.tableName = "Money";
                break;
            case "Buy or sell car":
                this.tableName = "Car";
                break;
            case "Settle to house":
                this.tableName = "House";
                break;
            case "Issue a loan":
                this.tableName = "Размер";
                break;
            case "Car":
                this.tableName = "Engine";
                break;
            case "House":
                this.tableName = "Floors";
                break;
            default:
                throw new IllegalArgumentException("Неизвестная таблица");
        }
    }

    private int findColumnIndex(String label) {
        List<String> headers = $$x(String.format(PATTERN + "//th", tableName)).texts();
        for (int i = 0; i < headers.size(); i++) {
            String normHeader = headers.get(i).replaceAll("\\u00A0", " ");
            if (normHeader.contains(label)) {
                return i;
            }
        }
        throw new AssertionError("Заголовок не найден");
    }

    public void setValueToInput(String label, String value) {
        int columnIndex = findColumnIndex(label) + 1;
        $x(String.format(PATTERN + "/tbody//td[" + columnIndex + "]/input", tableName)).setValue(value);
    }

    public List<String> getListOfValues (String label) {
        int columnIndex = findColumnIndex(label) + 1;
        return $$x(String.format(PATTERN + "/tbody//td[" + columnIndex + "]", tableName)).texts();
    }

    public void clickPushToApi() {
        $x(String.format(PATTERN + "/following::div[1]/button[contains(text(), 'PUSH')]", tableName)).click();
    }

    public String getMessagePushToApi() {
        return $x(String.format(PATTERN + "/following::div[1]/button[contains(text(), 'Status')]", tableName)).getText();
    }

    public int getResultInt() {
        String message = $x(String.format(PATTERN + "/following::div[1]/button[3]",
                tableName)).getText();
        return Integer.parseInt(message.replaceAll("\\D+", ""));
    }

    public double getResult() {
        String message = $x(String.format(PATTERN + "/following::div[1]/button[3]",
                tableName)).getText();
        return Double.parseDouble(message.replaceAll("[^\\d.]", ""));
    }
}
