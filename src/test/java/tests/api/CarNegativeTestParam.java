package tests.api;

import api.adapters.CarAdapter;
import api.models.CarRq;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

@DisplayName("Негативные тесты создания автомобиля")
public class CarNegativeTestParam extends BaseApiTest  {

    private final CarAdapter carAdapter = new CarAdapter();

    @DisplayName("Создание автомобиля с невалидными данными")
    @Description("Параметризованный тест проверяет обработку ошибок при создании автомобиля с некорректными данными: " +
            "пустые значения, недопустимые типы двигателя, отрицательная цена, числовые значения в строковых полях")
    @ParameterizedTest
    @CsvSource({
            ",X5,Diesel,10000",
            "BMW,,Diesel,10000",
            "BMW,X5,,10000",
            "BMW,X5,Petrol,10000",
            "BMW,X5,Diesel,-100",
            "'',X5,Diesel,10000",
            "123,X5,Diesel,10000",
            "BMW,123,Diesel,10000"
    })
    void createInvalidCarTest(
            String mark,
            String model,
            String engineType,
            BigDecimal price) {

        CarRq car = CarRq.builder()
                .mark(mark)
                .model(model)
                .engineType(engineType)
                .price(price)
                .build();

        carAdapter.createCarBadRequest(car, accessToken);
    }
}
