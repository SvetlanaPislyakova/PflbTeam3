package api.adapters;

import api.models.HouseRq;
import api.models.HouseRs;
import io.restassured.response.ValidatableResponse;
import lombok.extern.log4j.Log4j2;

import static io.restassured.RestAssured.given;

@Log4j2
public class HouseAdapter extends BaseAdapter {

    private ValidatableResponse createHouseRequest(HouseRq houseRq) {
        return given()
                .spec(getAuthSpec())
                .body(gson.toJson(houseRq))
                .log().all()
                .when()
                .post("/house")
                .then()
                .log().all();
    }

    public HouseRs createHouse(HouseRq houseRq) {
        log.info("POST - создание дома, 201");
        return createHouseRequest(houseRq)
                .spec(created201)
                .extract()
                .as(HouseRs.class);
    }

    private ValidatableResponse settleUserRequest(Integer houseId, Integer userId) {
        return given()
                .spec(getAuthSpec())
                .pathParam("houseId", houseId)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .post("/house/{houseId}/settle/{userId}")
                .then()
                .log().all();
    }

    public HouseRs settleUser(Integer houseId, Integer userId) {
        log.info("POST - заселение пользователя в дом, 200");
        return settleUserRequest(houseId, userId)
                .spec(success200)
                .extract()
                .as(HouseRs.class);
    }

    private ValidatableResponse evictUserRequest(Integer houseId, Integer userId) {
        return given()
                .spec(getAuthSpec())
                .pathParam("houseId", houseId)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .post("/house/{houseId}/evict/{userId}")
                .then()
                .log().all();
    }

    public HouseRs evictUser(Integer houseId, Integer userId) {
        log.info("POST - выселение пользователя из дома, 200");
        return evictUserRequest(houseId, userId)
                .spec(success200)
                .extract()
                .as(HouseRs.class);
    }
}
