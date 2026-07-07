package api.adapters;

import api.models.HouseRq;
import api.models.HouseRs;
import io.restassured.response.ValidatableResponse;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
public class HouseAdapter extends BaseAdapter {

    private ValidatableResponse createHouseRequest(HouseRq houseRq, String token) {
        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(houseRq))
                .log().all()
                .when()
                .post("/house")
                .then()
                .log().all();
    }

    public HouseRs createHouse(HouseRq houseRq, String token) {
        log.info("POST - создание дома, 201");
        return createHouseRequest(houseRq, token)
                .spec(created201)
                .extract()
                .as(HouseRs.class);
    }

    private ValidatableResponse settleUserRequest(Integer houseId, Integer userId, String token) {
        return given()
                .spec(spec)
                .pathParam("houseId", houseId)
                .pathParam("userId", userId)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .post("/house/{houseId}/settle/{userId}")
                .then()
                .log().all();
    }

    public HouseRs settleUser(Integer houseId, Integer userId, String token) {
        log.info("POST - заселение пользователя в дом, 200");
        return settleUserRequest(houseId, userId, token)
                .spec(success200)
                .extract()
                .as(HouseRs.class);
    }

    private ValidatableResponse evictUserRequest(Integer houseId, Integer userId, String token) {
        return given()
                .spec(spec)
                .pathParam("houseId", houseId)
                .pathParam("userId", userId)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .post("/house/{houseId}/evict/{userId}")
                .then()
                .log().all();
    }

    private ValidatableResponse deleteHouseRequest(Integer houseId, String token) {
        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("houseId", houseId)
                .log().all()
                .when()
                .delete("/house/{houseId}")
                .then()
                .log().all();
    }

    public void deleteHouse(Integer houseId, String token) {
        log.info("DELETE - удаление дома, 204");
        deleteHouseRequest(houseId, token)
                .spec(noContent204);
    }

    public HouseRs evictUser(Integer houseId, Integer userId, String token) {
        log.info("POST - выселение пользователя из дома, 200");
        return evictUserRequest(houseId, userId, token)
                .spec(success200)
                .extract()
                .as(HouseRs.class);
    }
    private ValidatableResponse updateHouseRequest(Integer houseId, HouseRq houseRq, String token) {
        return given()
                .spec(spec)
                .pathParam("houseId", houseId)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(houseRq))
                .log().all()
                .when()
                .put("/house/{houseId}")
                .then()
                .log().all();
    }

    public HouseRs updateHouse(Integer houseId, HouseRq houseRq, String token) {
        log.info("PUT - изменение дома, 202");
        return updateHouseRequest(houseId, houseRq, token)
                .spec(accepted202)
                .extract()
                .as(HouseRs.class);
    }
    private ValidatableResponse getHouseRequest(Integer houseId) {
        return given()
                .spec(spec)
                .pathParam("houseId", houseId)
                .log().all()
                .when()
                .get("/house/{houseId}")
                .then()
                .log().all();
    }

    public HouseRs getHouse(Integer houseId, String accessToken) {
        log.info("GET - получение дома по id, 200");
        return getHouseRequest(houseId)
                .spec(success200)
                .extract()
                .as(HouseRs.class);
    }

    public List<HouseRs> getHouses(String accessToken) {
        log.info("GET - получение списка домов, 200");

        return given()
                .spec(spec)
                .log().all()
                .when()
                .get("/houses")
                .then()
                .log().all()
                .spec(success200)
                .extract()
                .jsonPath()
                .getList("", HouseRs.class);
    }
}
