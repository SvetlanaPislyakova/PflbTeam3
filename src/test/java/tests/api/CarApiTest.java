package tests.api;

import api.adapters.CarAdapter;
import api.models.CarRq;
import api.models.CarRs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarApiTest extends BaseApiTest {

    private final CarAdapter carAdapter = new CarAdapter();

    @ParameterizedTest(name = "{0} {1}")
    @CsvSource({
            "BMW,X3,Diesel,11000",
            "BMW,X4,Diesel,11500",
            "BMW,X5,Diesel,10000",
            "BMW,X6,Diesel,12000",
            "BMW,X7,Diesel,25000",
            "BMW,5-Series,Petrol,14000",
            "BMW,3-Series,Petrol,9000",
            "Audi,A4,Petrol,9000",
            "Audi,A6,Petrol,15000",
            "Audi,A8,Diesel,22000",
            "Audi,Q5,Diesel,13000",
            "Audi,Q7,Diesel,18000",
            "Audi,A3,Petrol,7500",
            "Mercedes,E200,Diesel,17000",
            "Mercedes,C180,Petrol,13000",
            "Mercedes,S400,Diesel,32000",
            "Mercedes,GLE,Petrol,21000",
            "Mercedes,GLC,Diesel,19000",
            "Mercedes,A200,Petrol,11000",
            "Toyota,Camry,Petrol,8000",
            "Toyota,Corolla,Petrol,6000",
            "Toyota,RAV4,Diesel,14000",
            "Toyota,LandCruiser,Diesel,35000",
            "Toyota,Highlander,Petrol,16000",
            "Toyota,Prius,Petrol,7000",
            "Kia,Rio,Petrol,5000",
            "Kia,Sportage,Diesel,12000",
            "Kia,Ceed,Petrol,6500",
            "Kia,Stinger,Petrol,18000",
            "Kia,Sorento,Diesel,15000",
            "Kia,Niro,Petrol,10000",
            "Volkswagen,Golf,Petrol,7500",
            "Volkswagen,Passat,Diesel,11000",
            "Volkswagen,Tiguan,Diesel,13000",
            "Volkswagen,Touareg,Diesel,20000",
            "Volkswagen,Polo,Petrol,4500",
            "Hyundai,Elantra,Petrol,7000",
            "Hyundai,SantaFe,Diesel,14000",
            "Hyundai,Tucson,Petrol,11000",
            "Hyundai,i30,Petrol,6000",
            "Hyundai,Accent,Petrol,4000",
            "Nissan,Qashqai,Diesel,12000",
            "Nissan,XTrail,Diesel,13000",
            "Nissan,Patrol,Diesel,28000",
            "Nissan,Leaf,Petrol,9000",
            "Ford,Focus,Petrol,6500",
            "Ford,Mustang,Petrol,22000",
            "Ford,Kuga,Diesel,12500",
            "Ford,Mondeo,Diesel,10500",
            "Lexus,RX350,Diesel,19000" })
    public void createAndDeleteCarTest(
            String mark,
            String model,
            String engineType,
            Double price) {

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
