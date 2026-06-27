package tests.api;

import api.adapters.CarAdapter;
import api.models.CarRq;
import api.models.CarRs;
import io.qameta.allure.Description;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarApiTest extends BaseApiTest {

    private final CarAdapter carAdapter = new CarAdapter();

    @ParameterizedTest(name = "{0} {1}")
    @DisplayName("Создание и удаление автомобиля с различными параметрами")
    @Description("Параметризованный тест проверяет создание автомобиля с разными комбинациями марки, " +
            "модели, типа двигателя и цены, а также последующее успешное удаление созданного автомобиля")
    @CsvSource({
            "BMW,X3,Diesel,11000",
            "Audi,A4,Gasoline,9000",
            "Toyota,Corolla,PHEV,6000",
            "Hyundai,Elantra,CNG,7000"})
    public void createAndDeleteCarTest(
            String mark,
            String model,
            String engineType,
            BigDecimal price) {

        CarRq carRq = CarRq.builder()
                .mark(mark)
                .model(model)
                .engineType(engineType)
                .price(price)
                .build();

        CarRs createdCar = carAdapter.createCar(carRq, accessToken);


        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(createdCar.getMark()).isEqualTo(mark);
        softly.assertThat(createdCar.getModel()).isEqualTo(model);
        softly.assertThat(createdCar.getEngineType()).isEqualTo(engineType);
        softly.assertAll();

        carAdapter.deleteCar(createdCar.getId(), accessToken);
    }
}
