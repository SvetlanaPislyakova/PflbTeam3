package ui.steps;

import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import ui.pages.CreateNewHousePage;
import ui.pages.ReadHouseOneById;

public class CreateNewHouseSteps {

    private final CreateNewHousePage createNewHousePage = new CreateNewHousePage();
    private final ReadHouseOneById readHouseOneById = new ReadHouseOneById();

    @Step("Создание нового дома с параметрами: этажей={floors}, цена={price}, парковки: warm+covered={warmCovered}," +
            " warm+not={warmNotCovered}, cold+covered={coldCovered}, cold+not={coldNotCovered}")
    public void createNewHouse(int floors, double price,
                               int warmCovered, int warmNotCovered,
                               int coldCovered, int coldNotCovered) {
        createNewHousePage.openPage()
                .isPageOpened()
                .createHouse(floors, price, warmCovered, warmNotCovered, coldCovered, coldNotCovered)
                .clickPushToApi();
    }

    @Step("Создание простого дома: этажей={floors}, цена={price}, парковок={parkingPlaces}")
    public void createSimpleHouse(int floors, double price, int parkingPlaces) {
        createNewHousePage.openPage()
                .isPageOpened()
                .createSimpleHouse(floors, price, parkingPlaces)
                .clickPushToApi();
    }

    @Step("Проверка успешности создания дома и получение его ID")
    public String checkCreateHouseAndGetId() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(createNewHousePage.getStatus()).contains("Successfully pushed");
        String houseId = createNewHousePage.getHouseId();
        softly.assertThat(houseId).isNotNull();
        softly.assertAll();
        return houseId;
    }


    @Step("Получение статуса операции создания дома")
    public String getCreateHouseStatus() {
        return createNewHousePage.getStatus();
    }

    @Step("Получение ID созданного дома")
    public String getCreatedHouseId() {  // <-- Возвращаем Integer
        return createNewHousePage.getHouseId();
    }

    @Step("Чтение дома по ID={houseId} и проверка данных")
    public void checkHouseData(String houseId, int expectedFloors, double expectedPrice) {
        readHouseOneById.openPage()
                .isPageOpened()
                .findHouseById(houseId);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(readHouseOneById.getHouseId()).isEqualTo(houseId);
        softly.assertThat(readHouseOneById.getFloorCount()).isEqualTo(String.valueOf(expectedFloors));
        softly.assertThat(readHouseOneById.getPrice()).isEqualTo(String.valueOf(expectedPrice));
        softly.assertAll();
    }

    @Step("Проверка видимости таблиц на странице чтения дома по ID={houseId}")
    public void checkHouseTablesVisible(String houseId) {
        readHouseOneById.openPage()
                .isPageOpened()
                .findHouseById(houseId)
                .checkHouseTableVisible()
                .checkLodgersTableVisible()
                .checkParkingsTableVisible();
    }
}
