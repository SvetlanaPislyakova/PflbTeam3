package ui.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import ui.wrappers.Button;
import ui.wrappers.Table;

import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Log4j2
public class ReadUserWithCarsPage extends BasePage {

    private final Table carInfoTable = new Table("Read all cars");
    private final Table userInfoTable = new Table("Read all users");
    private final SelenideElement ID_INPUT = $("#user_input");

    @Override
    public ReadUserWithCarsPage openPage() {
        log.info("Открыть страницу 'ReadUserWithCarsPage'");
        open(baseUrl + "#/read/userInfo");
        return this;
    }

    @Override
    public ReadUserWithCarsPage isPageOpened() {
        log.info("Проверить, что страница 'ReadUserWithCarsPage' открыта");
        carInfoTable.checkTableVisible();
        userInfoTable.checkTableVisible();
        return this;
    }

    public ReadUserWithCarsPage findCarsByUserId(Integer userId) {
        log.info("Заполнить инпут значением userId = {}", userId);
        sleep(300);
        ID_INPUT.shouldBe(visible).shouldBe(enabled).setValue(String.valueOf(userId));
        new Button("Read").clickBtn();
        return this;
    }

    public ReadUserWithCarsPage checkUserInfo(Integer userId) {
        Integer userIdActual = Integer.valueOf(userInfoTable.getValueFromCell("ID"));
        assertThat(userIdActual).isEqualTo(userId);
        return this;
    }

    public ReadUserWithCarsPage checkCarsInfo(List<Integer> carsId) {
        List<Integer> actualIds = carInfoTable.getListOfValues("ID")
                .stream()
                .map(Integer::valueOf)
                .sorted()
                .toList();
        assertThat(actualIds).isEqualTo(carsId.stream().sorted().toList());
        return this;
    }

    public ReadUserWithCarsPage checkEmptyCarsInfo() {
        carInfoTable.rowsShouldBeEmpty();
        return this;
    }
}
