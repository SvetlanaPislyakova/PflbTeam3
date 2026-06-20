package ui.wrappers;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class Radio {
    private final SelenideElement radio;
    String name;


    public Radio(String name) {
        this.name = name;
        this.radio = $x(String.format("//*[contains(text(),'%s')]", name));
    }


    public void click(String name) {
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