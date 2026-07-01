package tests.ui.house;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tests.ui.base.BaseTest;
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
        houseStep.createSimpleHouse(2, 10000.01, 10);
        String houseId = houseStep.checkCreateHouseAndGetId();
        houseStep.checkHouseData(String.valueOf(houseId), 2, 10000.01);
    }
}
