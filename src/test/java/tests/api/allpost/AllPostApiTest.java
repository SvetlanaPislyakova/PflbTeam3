package tests.api.allpost;

import api.adapters.HouseAdapter;
import api.adapters.UserAdapter;
import api.models.house.HouseRq;
import api.models.house.HouseRqFactory;
import api.models.house.HouseRs;
import api.models.user.UserRqFactory;
import api.models.user.UserRs;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

public class AllPostApiTest {

    private final HouseAdapter houseAdapter = new HouseAdapter();
    private final UserAdapter userAdapter = new UserAdapter();

    @Test
    @DisplayName("API - Создание дома")
    @Description("Отправляем валидные данные дома и ожидаем успешное создание с id, количеством этажей и ценой из запроса")
    @Owner("OlgaBaidalina")
    public void createHouse() {
        HouseRq houseRq = HouseRqFactory.validHouse();

        HouseRs houseRs = houseAdapter.createHouse(houseRq);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(houseRs).isNotNull();
            softly.assertThat(houseRs.getId()).isNotNull();
            softly.assertThat(houseRs.getFloorCount()).isEqualTo(houseRq.getFloorCount());
            softly.assertThat(houseRs.getPrice()).isEqualByComparingTo(houseRq.getPrice());
        });
    }

    @Test
    @DisplayName("API - Ошибка при создании дома с невалидными данными")
    @Description("Отправляем floorCount строкой вместо integer и ожидаем ошибку 400")
    @Owner("OlgaBaidalina")
    public void createHouseWithInvalidData() {
        String houseRq = "{\"floorCount\":\"wrong\",\"price\":100,\"parkingPlaces\":[],\"lodgers\":[]}";

        houseAdapter.createHouseBadRequest(houseRq);
    }

    @Test
    @DisplayName("API - Заселение и выселение пользователя из дома")
    @Description("Создаем пользователя и дом, заселяем пользователя в дом, затем выселяем и проверяем состав жильцов в ответе")
    @Owner("OlgaBaidalina")
    public void settleAndEvictUserInHouse() {
        Integer userId = userAdapter.createUserAndGetId(UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000_000))
                .build());
        HouseRq houseRq = HouseRq.builder()
                .floorCount(3)
                .price(BigDecimal.valueOf(100))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();
        HouseRs houseRs = houseAdapter.createHouse(houseRq);

        HouseRs settledHouseRs = houseAdapter.settleUser(houseRs.getId(), userId);
        HouseRs evictedHouseRs = houseAdapter.evictUser(houseRs.getId(), userId);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(settledHouseRs).isNotNull();
            softly.assertThat(settledHouseRs.getId()).isEqualTo(houseRs.getId());
            softly.assertThat(settledHouseRs.getLodgers())
                    .isNotNull()
                    .extracting(UserRs::getId)
                    .contains(userId);

            softly.assertThat(evictedHouseRs).isNotNull();
            softly.assertThat(evictedHouseRs.getId()).isEqualTo(houseRs.getId());
            softly.assertThat(evictedHouseRs.getLodgers())
                    .isNotNull()
                    .extracting(UserRs::getId)
                    .doesNotContain(userId);
        });
    }

    @Test
    @DisplayName("API - Ошибка при заселении несуществующего пользователя")
    @Description("Создаем дом, пытаемся заселить несуществующего пользователя и ожидаем ошибку 404 без добавления жильца")
    @Owner("OlgaBaidalina")
    public void settleNonExistingUser() {
        Integer missingUserId = 999_999_999;
        HouseRs houseRs = houseAdapter.createHouse(HouseRqFactory.validHouse());

        houseAdapter.settleNonExistingUser(houseRs.getId(), missingUserId);
        HouseRs houseAfterSettle = houseAdapter.getHouse(houseRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(houseAfterSettle.getLodgers())
                    .isNotNull()
                    .extracting(UserRs::getId)
                    .doesNotContain(missingUserId);
        });
    }

    @Test
    @DisplayName("API - Ошибка при заселении в несуществующий дом")
    @Description("Создаем пользователя, пытаемся заселить его в несуществующий дом и ожидаем ошибку 404")
    @Owner("OlgaBaidalina")
    public void settleToNonExistingHouse() {
        Integer userId = userAdapter.createUserAndGetId(UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000_000))
                .build());
        Integer missingHouseId = 999_999_999;

        houseAdapter.settleUserToNonExistingHouse(missingHouseId, userId);
    }

    @Test
    @DisplayName("API - Ошибка при выселении несуществующего пользователя")
    @Description("Создаем дом с жильцом, пытаемся выселить несуществующего пользователя и ожидаем ошибку 404 без изменения списка жильцов")
    @Owner("OlgaBaidalina")
    public void evictNonExistingUser() {
        Integer userId = userAdapter.createUserAndGetId(UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000_000))
                .build());
        Integer missingUserId = 999_999_999;
        HouseRs houseRs = houseAdapter.createHouse(HouseRqFactory.validHouse());
        houseAdapter.settleUser(houseRs.getId(), userId);

        houseAdapter.evictNonExistingUser(houseRs.getId(), missingUserId);
        HouseRs houseAfterEvict = houseAdapter.getHouse(houseRs.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(houseAfterEvict.getLodgers())
                    .isNotNull()
                    .extracting(UserRs::getId)
                    .contains(userId)
                    .doesNotContain(missingUserId);
        });
    }

    @Test
    @DisplayName("API - Ошибка при выселении из несуществующего дома")
    @Description("Создаем пользователя, пытаемся выселить его из несуществующего дома и ожидаем ошибку 404")
    @Owner("OlgaBaidalina")
    public void evictFromNonExistingHouse() {
        Integer userId = userAdapter.createUserAndGetId(UserRqFactory.validUser().toBuilder()
                .money(BigDecimal.valueOf(1_000_000))
                .build());
        Integer missingHouseId = 999_999_999;

        houseAdapter.evictUserFromNonExistingHouse(missingHouseId, userId);
    }
}
