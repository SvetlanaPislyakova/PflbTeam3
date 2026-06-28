package api.adapters;

import api.models.car.CarRq;
import api.models.car.CarRs;

import static io.restassured.RestAssured.given;

public class CarAdapter extends BaseAdapter {

    public CarRs createCar(CarRq carRq, String token) {
        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(carRq)
                .log().all()
                .post("/car")
                .then()
                .log().all()
                .spec(created201)
                .extract()
                .as(CarRs.class);
    }

    public void deleteCar(int id, String token) {
        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("id", id)
                .log().all()
                .delete("/car/{id}")
                .then()
                .log().all()
                .spec(noContent204);
    }

    public CarRs getCar(int id, String token) {
        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("id", id)
                .log().all()
                .get("/car/{id}")
                .then()
                .log().all()
                .spec(success200)
                .extract()
                .as(CarRs.class);
    }
    public void createCarBadRequest(CarRq carRq, String token) {
        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(carRq)
                .post("/car")
                .then()
                .spec(badRequest400);
    }
}