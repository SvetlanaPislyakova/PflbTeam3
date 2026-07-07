package tests.ui.house;

import api.adapters.HouseAdapter;
import api.models.HouseRq;
import api.models.HouseRs;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTest;
import io.qameta.allure.Description;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HouseDeleteTest extends BaseTest {

    private final HouseAdapter houseAdapter = new HouseAdapter();

    @BeforeEach
    public void login() {
        loginSteps.login(email, password)
                .acceptAlert("Successful authorization");
    }

    @Test
    @DisplayName("UI - Удаление дома через страницу All DELETE")
    @Description("Удаление дома через страницу All DELETE с проверкой в БД")
    public void deleteHouseThroughUI() {
        HouseRq houseRq = HouseRq.builder()
                .floorCount(5)
                .price(BigDecimal.valueOf(1_000_000))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq, token);
        Integer houseId = houseRs.getId();

        assertThat(dbSteps.isHouseExistsInDB(houseId)).isTrue();

        allDeletePage.openPage()
                .isPageOpened()
                .deleteHouse(houseId);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(allDeletePage.getHouseStatusCode()).isEqualTo(204);
        softly.assertThat(dbSteps.isHouseExistsInDB(houseId)).isFalse();
        softly.assertAll();
    }
}