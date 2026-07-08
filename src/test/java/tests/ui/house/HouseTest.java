package tests.ui.house;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.ui.base.BaseTest;
import ui.steps.CreateNewHouseSteps;

public class HouseTest extends BaseTest {

    private final CreateNewHouseSteps houseStep = new CreateNewHouseSteps();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Создание нового дома с базовыми параметрами")
    @Description("Создание нового дома с базовыми параметрами")
    @Owner("Трафилькин Валентин")
    public void createSimpleHouse() {
        houseStep.createSimpleHouse(2, 10000.01, 10);
        String houseId = houseStep.checkCreateHouseAndGetId();
        houseStep.checkHouseData(String.valueOf(houseId), 2, 10000.01);
    }
}
