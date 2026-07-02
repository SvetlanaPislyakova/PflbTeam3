package api.adapters;

import api.models.car.CarRq;
import api.models.car.CarRs;
import io.restassured.response.ValidatableResponse;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
public class CarAdapter extends BaseAdapter {

    private ValidatableResponse createCarRequest(CarRq carRq) {
        return given()
                .spec(getAuthSpec())
                .body(gson.toJson(carRq))
                .log().all()
                .when()
                .post("/car")
                .then()
                .log().all();
    }

    public CarRs createCar(CarRq carRq) {
        log.info("POST - создание автомобиля, 201");
        return given()
                .spec(getAuthSpec())
                .body(carRq)
                .log().all()
                .post("/car")
                .then()
                .log().all()
                .spec(created201)
                .extract()
                .as(CarRs.class);
    }

    public void deleteCar(int id) {
        log.info("DELETE - удаление автомобиля, 204");
        given()
                .spec(getAuthSpec())
                .pathParam("id", id)
                .log().all()
                .delete("/car/{id}")
                .then()
                .log().all()
                .spec(noContent204);
    }

    public CarRs getCar(int id) {
        log.info("GET - получение автомобиля по id, 200");
        return given()
                .spec(baseSpec)
                .pathParam("id", id)
                .log().all()
                .get("/car/{id}")
                .then()
                .log().all()
                .spec(success200)
                .extract()
                .as(CarRs.class);
    }
    public void createCarBadRequest(CarRq carRq) {
        log.info("POST - создание автомобиля с невалидными данными, 400");
        given()
                .spec(getAuthSpec())
                .body(carRq)
                .post("/car")
                .then()
                .spec(badRequest400);
    }

    public Integer createCarAndGetId(CarRq carRq) {
        log.info("POST - создание нового автомобиля и получение его id, 201");
        return createCarRequest(carRq)
                .spec(created201)
                .extract()
                .path("id");
    }

    public CarRs updateCar(int id, CarRq carRq) {
        log.info("PUT - изменение автомобиля, 202");
        return given()
                .spec(getAuthSpec())
                .pathParam("id", id)
                .body(gson.toJson(carRq))
                .log().all()
                .when()
                .put("/car/{id}")
                .then()
                .log().all()
                .spec(accepted202)
                .extract()
                .as(CarRs.class);
    }

    public List<CarRs> getCars() {
        log.info("GET - получение списка автомобилей, 200");
        return given()
                .spec(baseSpec)
                .log().all()
                .get("/cars")
                .then()
                .log().all()
                .spec(success200)
                .extract()
                .jsonPath()
                .getList("", CarRs.class);
    }
}
