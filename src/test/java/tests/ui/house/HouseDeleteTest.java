package tests.ui.house;

import api.adapters.HouseAdapter;
import api.models.HouseRq;
import api.models.HouseRs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.BaseTest;

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
    public void deleteHouseThroughUI() {
        // 1. Создаем дом через API
        HouseRq houseRq = HouseRq.builder()
                .floorCount(5)
                .price(BigDecimal.valueOf(1_000_000))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq, token);
        Integer houseId = houseRs.getId();

        // 2. Удаляем дом через UI
        allDeletePage.openPage()
                .isPageOpened()
                .deleteHouse(houseId);

        // 3. Проверяем статус
        assertThat(allDeletePage.getHouseStatusCode()).isEqualTo(204);

        // 4. Проверяем в БД
        assertThat(dbSteps.isHouseExistsInDB(houseId)).isFalse();
    }
}