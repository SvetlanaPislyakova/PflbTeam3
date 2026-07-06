package tests.ui.car;

import io.qameta.allure.Owner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tests.ui.base.BaseTest;

public class CarSortTest extends BaseTest {
    @ParameterizedTest(name = "Сортировка автомобилей по полю {0}")
    @Owner("Akhunov Gayaz")
    @ValueSource(strings = {"Mark", "Model"})
    public void checkSortingByTextField(String field) {
        carSteps.checkSortCarsByTextField(field);
    }

    @ParameterizedTest(name = "Сортировка автомобилей по полю {0}")
    @Owner("Akhunov Gayaz")
    @ValueSource(strings = {"ID", "Price"})
    public void checkSortingByNumericField(String field) {
        carSteps.checkSortCarsByNumericField(field);
    }

    @ParameterizedTest(name = "Сортировка автомобилей по полю {0}")
    @Owner("Akhunov Gayaz")
    @ValueSource(strings = {"Engine Type"})
    public void checkSortingByFixedTextField(String field) {
        carSteps.checkSortCarsByFixedTextField(field);
    }
}
