package tests.api;

import api.adapters.CarAdapter;
import api.models.CarRq;
import api.models.CarRs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarApiTest extends BaseApiTest {

    private final CarAdapter carAdapter = new CarAdapter();

    @ParameterizedTest(name = "{0} {1}")
    @CsvSource({
            "BMW,X3,Diesel,11000",
            "BMW,X4,Diesel,11500",
            "Audi,A4,Gasoline,9000",
            "Audi,A6,Gasoline,15000",
            "Mercedes,E200,Diesel,17000",
            "Mercedes,C180,Gasoline,13000",
            "Mercedes,S400,Diesel,32000",
            "Toyota,Camry,Gasoline,8000",
            "Toyota,Corolla,PHEV,6000",
            "Kia,Rio,PHEV,5000",
            "Kia,Sportage,Diesel,12000",
            "Kia,Ceed,PHEV,6500",
            "Kia,Niro,PHEV,10000",
            "Volkswagen,Golf,PHEV,7500",
            "Hyundai,Elantra,CNG,7000",
            "Hyundai,SantaFe,Diesel,14000",
            "Nissan,Leaf,CNG,9000",
            "Lexus,RX350,Diesel,19000" })
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

        assertEquals(mark, createdCar.getMark());
        assertEquals(model, createdCar.getModel());
        assertEquals(engineType, createdCar.getEngineType());

        carAdapter.deleteCar(createdCar.getId(), accessToken);
    }
}
