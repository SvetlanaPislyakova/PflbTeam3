package tests;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.steps.CreateNewHouseSteps;

public class HouseTest extends BaseTest {

    private final Faker faker = new Faker();
    private final CreateNewHouseSteps houseStep = new CreateNewHouseSteps();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @Description("Создание нового дома с базовыми параметрами")
    public void createSimpleHouse() {
        int floors = 5;
        double price = 100000.00;
        int parkingPlaces = 10;

        houseStep.createSimpleHouse(floors, price, parkingPlaces);
        String houseId = houseStep.checkCreateHouseAndGetId();
        houseStep.checkHouseData(String.valueOf(houseId), floors, price);
    }
}
