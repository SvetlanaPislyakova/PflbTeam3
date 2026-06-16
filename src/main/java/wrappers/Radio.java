package wrappers;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class Radio {
    private final SelenideElement radio;


    public Radio(String name) {
        this.radio = $x(String.format("//*[text()='%s']", name));
    }


    public void click() {
        radio.click();
    }

    public void select() {
        if (!radio.isSelected()) {
            radio.click();
        }
    }

    public boolean isSelected() {
        return radio.isSelected();
    }
}