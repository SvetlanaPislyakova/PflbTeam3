package ui.wrappers;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;

public class Button {

    private final String label;
    private final String PATTERN = "contains(., '%s')";

    public Button(String label) {
        this.label = label;
    }

    public void clickBtn() {
        StringBuilder textBtn = new StringBuilder();
        List<String> words = List.of(label.split(" "));
        for(int i = 0; i < words.size(); i++) {
            if (i != 0) textBtn.append(" and ");
            textBtn.append(String.format(PATTERN, words.get(i)));
        }
        System.out.println(textBtn);
        $x(String.format("//button[%s]", textBtn)).click();
    }
}
