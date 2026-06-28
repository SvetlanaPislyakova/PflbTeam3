package tests.api;

import api.adapters.HouseAdapter;
import api.adapters.UserAdapter;
import api.models.HouseRq;
import api.models.HouseRs;
import api.models.user.UserRqFactory;
import api.models.user.UserRs;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class AllPostApiTest extends BaseApiTest {

    private final HouseAdapter houseAdapter = new HouseAdapter();
    private final UserAdapter userAdapter = new UserAdapter();

    @Test
    @DisplayName("API - Создание дома")
    public void createHouse() {
        HouseRq houseRq = HouseRq.builder()
                .floorCount(5)
                .price(BigDecimal.valueOf(1_000_000))
                .parkingPlaces(List.of())
                .lodgers(List.of())
                .build();

        HouseRs houseRs = houseAdapter.createHouse(houseRq, accessToken);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(houseRs).isNotNull();
            softly.assertThat(houseRs.getId()).isNotNull();
            softly.assertThat(houseRs.getFloorCount()).isEqualTo(houseRq.getFloorCount());
            softly.assertThat(houseRs.getPrice()).isEqualByComparingTo(houseRq.getPrice());
        });
    }

    @Test
    @DisplayName("API - Заселение и выселение пользователя из дома")
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
        HouseRs houseRs = houseAdapter.createHouse(houseRq, accessToken);

        HouseRs settledHouseRs = houseAdapter.settleUser(houseRs.getId(), userId, accessToken);
        HouseRs evictedHouseRs = houseAdapter.evictUser(houseRs.getId(), userId, accessToken);

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
}
