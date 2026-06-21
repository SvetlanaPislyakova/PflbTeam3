package api.adapters;

import api.models.HouseRq;
import api.models.HouseRs;
import io.restassured.response.ValidatableResponse;
import lombok.extern.log4j.Log4j2;

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
}
