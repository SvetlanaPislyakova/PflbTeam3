package tests.ui.car;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

import static com.codeborne.selenide.Selenide.*;

public class CreateCarTest extends BaseTest {
    private final Faker faker = new Faker();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("Создание нового автомобиля")
    public void createCar() {
        open("#/create/cars");
        $("[id=car_engine_type_send]").setValue("Diesel");
        $("[id=car_mark_send]").setValue("Lada");
        $("[id=car_model_send]").setValue("Vesta");
        $("[id=car_price_send]").setValue("100500");
        $x("//button[contains(text(),'PUSH')]").click();
        sleep(5000);
    }
}