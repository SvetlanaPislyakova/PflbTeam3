package ui.wrappers;

import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.$x;

@Log4j2
public class DropDown {

    private final String label;
    private final String PATTERN = "//a[contains(text(), '%s')]";

    public DropDown(String label) {
        this.label = label;
    }

    public void selectOption(String option) {
        log.info("Выбрать из выпадающего списка '{}' значение '{}'", label, option);
        $x(String.format(PATTERN, label)).click();
        $x(String.format(PATTERN + "/parent::div" + PATTERN, label, option)).click();
    }
}
