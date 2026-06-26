package ui.wrappers;

import lombok.extern.log4j.Log4j2;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;

@Log4j2
public class Button {

    private final String label;

    private final String PATTERN =
            "contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')";
//    private final String PATTERN = "contains(., '%s')";

    public Button(String label) {
        this.label = label.toLowerCase();
    }

    public void clickBtn() {
        log.info("Кликнуть кнопку {}", label);
        StringBuilder textBtn = new StringBuilder();
        List<String> words = List.of(label.split(" "));
        for(int i = 0; i < words.size(); i++) {
            if (i != 0) textBtn.append(" and ");
            textBtn.append(String.format(PATTERN, words.get(i)));
        }
        $x(String.format("//button[%s]", textBtn)).click();
    }
}
