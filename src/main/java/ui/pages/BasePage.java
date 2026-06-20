package ui.pages;

import ui.wrappers.Button;
import ui.wrappers.Table;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class BasePage {

    public abstract BasePage openPage();

    public abstract BasePage isPageOpened();

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        return value.replace('\u00A0', ' ')
                .replace('\t', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }

    public List<String> sortNaturalOrder(List<String> objectList, boolean isNumeric) {
        List<String> sorted = new ArrayList<>(objectList);
        if (isNumeric) {
            sorted.sort(Comparator.comparingDouble(Double::parseDouble));
        } else {
            sorted.sort(Comparator.naturalOrder());
        }
        return sorted.stream()
                .map(this::normalizeText)
                .toList();
    }

    public List<String> sortReverseOrder(List<String> objectList, boolean isNumeric) {
        List<String> sorted = new ArrayList<>(objectList);
        if (isNumeric) {
            sorted.sort(Comparator.comparingDouble(Double::parseDouble).reversed());
        } else {
            sorted.sort(Comparator.reverseOrder());
        }
        return sorted.stream()
                .map(this::normalizeText)
                .toList();
    }

    public BasePage checkSortObjectNaturalOrder(Table table, List<String> startList, String field, boolean isNumeric) {
        List<String> sortedNaturalOrder = new ArrayList<>(startList);
        sortedNaturalOrder = sortNaturalOrder(sortedNaturalOrder, isNumeric);
        System.out.println(sortedNaturalOrder);
        new Button(field).clickBtn();
        List<String> sortedNatural = table.getListOfValues(field);
        System.out.println(sortedNatural);
        assertThat(sortedNatural).isEqualTo(sortedNaturalOrder);
        return this;
    }

    public BasePage checkSortObjectReverseOrder(Table table, List<String> startList, String field, boolean isNumeric) {
        List<String> sortedReverseOrder = new ArrayList<>(startList);
        sortedReverseOrder = sortReverseOrder(sortedReverseOrder, isNumeric);
        System.out.println(sortedReverseOrder);
        new Button("↑ " + field).clickBtn();
        List<String> sortedReverse = table.getListOfValues(field);
        System.out.println(sortedReverse);
        assertThat(sortedReverse).isEqualTo(sortedReverseOrder);
        return this;
    }

//    public BasePage checkSortObject(Table table, String field, boolean isNumeric) {
//        SoftAssertions softly = new SoftAssertions();
//        List<String> startList = table.getListOfValues(field);
//        List<String> sortedNaturalOrder = new ArrayList<>(startList);
//        List<String> sortedReverseOrder = new ArrayList<>(startList);
//
//        sortedNaturalOrder = sortNaturalOrder(sortedNaturalOrder, isNumeric);
//        new Button(field).clickBtn();
//        List<String> sortedNatural = table.getListOfValues(field);
//        softly.assertThat(sortedNatural).isEqualTo(sortedNaturalOrder);
//
//        sortedReverseOrder = sortReverseOrder(sortedReverseOrder, isNumeric);
//        new Button(field).clickBtn();
//        List<String> sortedReverse = table.getListOfValues(field);
//        softly.assertThat(sortedReverse).isEqualTo(sortedReverseOrder);
//        softly.assertAll();
//        return this;
//    }
}
