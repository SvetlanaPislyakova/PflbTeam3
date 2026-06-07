package wrappers;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Input {

    private final String label;

    public Input(String label) {
        this.label = label;
    }

    private int findColumnIndex() {
        List<String> headers = $$x("//table/thead//th").texts();
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
        $x("//table/tbody//td[" + columnIndex + "]/input")
                .setValue(value);
    }
}
