package wrappers;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Input {

    private final String label;
    private final String tableName;

    public Input(String tableName, String label) {
        switch (tableName) {
            case "Create new user":
            case "First Name":
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
            case "Create new car":
                this.tableName = "Engine";
                break;
            case "Create new house":
                this.tableName = "Floors";
                break;
            default:
                throw new IllegalArgumentException("Неизвестная таблица");
        }
        this.label = label;
    }

    private int findColumnIndex() {
        List<String> headers =
                $$x(String.format("//table//th[2][contains(text(), '%s')]/ancestor::table//th", tableName)).texts();
        for (int i = 0; i < headers.size(); i++) {
            String normHeader = headers.get(i).replaceAll("\\u00A0", " ");
            if (normHeader.contains(label)) {
                return i;
            }
        }
        throw new AssertionError("Заголовок не найден");
    }

    public void write(String value) {
        int columnIndex = findColumnIndex() + 1;
        $x(String.format("//table//th[2][contains(text(), '%s')]/ancestor::" +
                "table/tbody//td[" + columnIndex + "]/input", tableName)).setValue(value);
    }

    public List<String> getValues() {
        int columnIndex = findColumnIndex() + 1;
        List<String> values = $$x(String.format("//table//th[2][contains(text(), '%s')]/ancestor::" +
                "table/tbody//td[" + columnIndex + "]", tableName)).texts();
        return values;
    }
}
