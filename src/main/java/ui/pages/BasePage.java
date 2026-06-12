package ui.pages;

import org.assertj.core.api.SoftAssertions;
import ui.wrappers.Table;

import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.Selenide.$x;

public abstract class BasePage {

    public abstract BasePage openPage();

    public abstract BasePage isPageOpened();

    public BasePage checkSortObject(String tableName, String field, boolean isNumeric) {
        Table table = new Table(tableName);
        SoftAssertions softly = new SoftAssertions();
        List<String> startList = table.getListOfValues(field);
        System.out.println(startList);
        System.out.println(startList.size());
        if (isNumeric) {
            startList.sort(Comparator.comparingDouble(Double::parseDouble));
        } else {
            startList.sort(Comparator.naturalOrder());
        }
        System.out.println("start " + startList);
        $x(String.format("//button[contains(., '%s')]", field)).click(); // Гаяз
        List<String> sorted = table.getListOfValues(field);
        System.out.println(sorted);
        softly.assertThat(sorted).isEqualTo(startList);
        $x(String.format("//button[contains(., '%s')]", field)).click(); // Гаяз
        if (isNumeric) {
            startList.sort(Comparator.comparingDouble(Double::parseDouble).reversed());
        } else {
            startList.sort(Comparator.reverseOrder());
        }
        List<String> sorted2 = table.getListOfValues(field);
        System.out.println("start " + startList);
        System.out.println(sorted2);
        softly.assertThat(sorted2).isEqualTo(startList);
        softly.assertAll();
        return this;
    }
}
