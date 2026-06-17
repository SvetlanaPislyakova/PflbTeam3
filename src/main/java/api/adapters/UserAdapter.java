package api.adapters;

import api.models.user.UserRq;
import api.models.user.UserRs;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
public class UserAdapter extends BaseAdapter {

    public UserRs createUser(UserRq userRq, String token) {
        log.info("POST - создание нового пользователя, 201ok");
        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(userRq))
                .log().all()
                .when()
                .post("/user")
                .then()
                .log().all()
                .spec(created201)
                .extract()
                .as(UserRs.class);
    }

    public void createUserWithNullFields(UserRq userRq, String token) {
        log.info("POST - создание нового пользователя с невалидными данными, 400");
        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(userRq))
                .log().all()
                .when()
                .post("/user")
                .then()
                .log().all()
                .spec(badRequest400);
    }

    public Integer createUserAndGetId(UserRq userRq, String token) {
        log.info("POST - создание нового пользователя и получение его id, 201ok");
        return given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(userRq))
                .when()
                .post("/user")
                .then()
                .spec(created201)
                .extract()
                .path("id");
    }

    public UserRs changeUser(Integer userId, UserRq userRq, String token) {
        log.info("PUT - Изменение пользователя, 201ok");
        return given()
                .spec(spec)
                .pathParam("userId", userId)
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(userRq))
                .log().all()
                .when()
                .put("/user/{userId}")
                .then()
                .log().all()
                .spec(accepted202)
                .extract()
                .as(UserRs.class);
    }

    public UserRs getUserById(Integer userId) {
        log.info("GET - получение пользователя по id, 200ok");
        return given()
                .spec(spec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}")
                .then()
                .log().all()
                .spec(success200)
                .extract()
                .as(UserRs.class);
    }

    public void getNotExistingUserById(Integer userId) {
        log.info("GET - получение несуществующего пользователя, 204ok");
        given()
                .spec(spec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}")
                .then()
                .log().all()
                .spec(noContent204);
    }

    public List<UserRs> getUsers() {
        log.info("GET - получение списка пользователей, 200ok");
        return given()
                .spec(spec)
                .log().all()
                .when()
                .get("/users")
                .then()
                .log().all()
                .spec(success200)
                .extract()
                .jsonPath()
                .getList("", UserRs.class);
    }

    public void getUserCars(Integer userId) {
        log.info("GET - получение автомобилей пользователя, 201ok");
        given()
                .spec(spec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}/cars")
                .then()
                .log().all()
                .spec(success200);
    }

    public void getUserInfo(Integer userId) {
        log.info("GET - получение информации о пользователе, 201ok");
        given()
                .spec(spec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}/info")
                .then()
                .log().all()
                .spec(success200);
    }

    public void deleteUser(Integer userId, String token) {
        log.info("DELETE - удаление пользователя, 201ok");
        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .delete("/user/{userId}")
                .then()
                .log().all()
                .spec(noContent204);
    }
}
