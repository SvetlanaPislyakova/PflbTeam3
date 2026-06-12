package ui.wrappers;

import static com.codeborne.selenide.Selenide.$x;

public class DropDown {

    private final String label;
    private final String PATTERN = "//a[contains(text(), '%s')]";

    public DropDown(String label) {
        this.label = label;
    }

    public void selectOption(String option) {
        $x(String.format(PATTERN, label)).click();
        $x(String.format(PATTERN + "/parent::div" + PATTERN, label, option)).click();
    }
}
