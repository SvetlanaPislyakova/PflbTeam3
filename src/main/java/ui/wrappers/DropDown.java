package ui.wrappers;

import static com.codeborne.selenide.Selenide.$x;

public class DropDown {

    private final String label;

    public DropDown(String label) {
        this.label = label;
    }

    public void selectOption(String option) {
        $x(String.format("//a[contains(text(), '%s')]", label)).click();
        $x(String.format("//a[contains(text(), '%s')]/parent::div//a[contains(text(), '%s')]", label, option)).click();
    }
}
