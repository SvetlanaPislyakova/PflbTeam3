package tests.ui.car;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tests.BaseTest;

public class CarSortTest extends BaseTest {

    @ParameterizedTest(name = "Сортировка автомобилей по полю {0}")
    @ValueSource(strings = {"Mark", "Model"})
    public void checkSortingByTextField(String field) {
        carSteps.checkSortCarsByTextField(field);
    }

    @ParameterizedTest(name = "Сортировка автомобилей по полю {0}")
    @ValueSource(strings = {"ID", "Price"})
    public void checkSortingByNumericField(String field) {
        carSteps.checkSortCarsByNumericField(field);
    }

    @ParameterizedTest(name = "Сортировка автомобилей по полю {0}")
    @ValueSource(strings = {"Engine Type"})
    public void checkSortingByFixedTextField(String field) {
        carSteps.checkSortCarsByFixedTextField(field);
    }
}
