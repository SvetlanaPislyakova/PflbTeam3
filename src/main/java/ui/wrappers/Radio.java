package ui.wrappers;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class Radio {
    private final SelenideElement radio;

    public Radio(String name) {
        this($x(String.format("//*[contains(text(),'%s')]//input", name)));
    }

    private Radio(SelenideElement radio) {
        this.radio = radio;
    }

    public static Radio byValueIn(String containerXpath, String value) {
        return new Radio($x(String.format("%s//input[@value='%s']", containerXpath, value)));
    }

    public static Radio byNameAndValue(String name, String value) {
        return new Radio($(String.format("input[name='%s'][value='%s']", name, value)));
    }

    public void click() {
        radio.shouldBe(visible).shouldBe(enabled).click();
    }

    public void select() {
        if (!radio.shouldBe(visible).isSelected()) {
            radio.shouldBe(enabled).click();
        }
        radio.shouldBe(selected);
    }

    public boolean isSelected() {
        return radio.isSelected();
    }
}
