package api.adapters;

import api.models.CarRq;
import api.models.CarRs;
import lombok.extern.log4j.Log4j2;

import static io.restassured.RestAssured.given;

@Log4j2
public class CarAdapter extends BaseAdapter {

    public CarRs createCar(CarRq carRq, String token) {
        log.info("POST - создание автомобиля, 201");
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
        log.info("DELETE - удаление автомобиля, 204");
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
        log.info("GET - получение автомобиля по id, 200");
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
        log.info("POST - создание автомобиля с невалидными данными, 400");
        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(carRq)
                .post("/car")
                .then()
                .spec(badRequest400);
    }
}