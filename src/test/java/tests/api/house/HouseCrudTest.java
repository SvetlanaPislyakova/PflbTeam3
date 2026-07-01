package tests.api.house;

import api.adapters.HouseAdapter;
import api.models.house.HouseRq;
import api.models.house.HouseRs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.api.base.BaseApiTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

    public class HouseCrudTest extends BaseApiTest {

        HouseAdapter houseAdapter = new HouseAdapter();

        @Test
        @DisplayName("Получение дома по ID")
        void getHouseTest() {

            HouseRq houseRq = HouseRq.builder()
                    .floorCount(3)
                    .price(BigDecimal.valueOf(100000))
                    .parkingPlaces(List.of())
                    .lodgers(List.of())
                    .build();

            HouseRs created = houseAdapter.createHouse(houseRq, accessToken);
            HouseRs received = houseAdapter.getHouse(created.getId(), accessToken);
            assertEquals(created.getId(), received.getId());
        }

        @Test
        @DisplayName("Получение списка домов")
        void getHousesTest() {

            List<HouseRs> houses = houseAdapter.getHouses(accessToken);
            assertFalse(houses.isEmpty());
        }

        @Test
        @DisplayName("Изменение дома")
        void updateHouseTest() {

            HouseRq houseRq = HouseRq.builder()
                    .floorCount(2)
                    .price(BigDecimal.valueOf(50000))
                    .parkingPlaces(List.of())
                    .lodgers(List.of())
                    .build();

            HouseRs created = houseAdapter.createHouse(houseRq, accessToken);
            HouseRq updateRq = HouseRq.builder()
                    .id(created.getId())
                    .floorCount(5)
                    .price(BigDecimal.valueOf(150000))
                    .parkingPlaces(List.of())
                    .lodgers(List.of())
                    .build();

            HouseRs updated = houseAdapter.updateHouse(created.getId(), updateRq, accessToken);
            assertEquals(5, updated.getFloorCount());
        }
    }
