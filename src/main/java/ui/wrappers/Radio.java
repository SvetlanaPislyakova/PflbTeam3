package ui.wrappers;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class Radio {
    private final SelenideElement radio;
    private final String name;


    public Radio(String name) {
        this.name = name;
        this.radio = $x(String.format("//*[contains(text(),'%s')]//input", name));
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