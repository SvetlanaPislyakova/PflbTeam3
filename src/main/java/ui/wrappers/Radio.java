package ui.wrappers;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class Radio {
    private final SelenideElement radio;
    private final String action;


    public Radio(String action) {
        this.action = action;
        this.radio = $x(String.format("//*[contains(text(),'%s')]//input", action));
    }


    public void click() {
        radio.click();
    }

    public void select() {
        if (!radio.isSelected()) {
            click();
        }
    }

    public boolean isSelected() {
        return radio.isSelected();
    }
}