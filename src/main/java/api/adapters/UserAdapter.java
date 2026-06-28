package api.adapters;

import api.models.user.UserInfoRs;
import api.models.user.UserRq;
import api.models.user.UserRs;
import io.restassured.response.ValidatableResponse;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;

@Log4j2
public class UserAdapter extends BaseAdapter {

    private ValidatableResponse createUserRequest(UserRq userRq) {
        return given()
                .spec(getAuthSpec())
                .body(gson.toJson(userRq))
                .log().all()
                .when()
                .post("/user")
                .then()
                .log().all();
    }

    public UserRs createUser(UserRq userRq) {
        log.info("POST - создание нового пользователя, 201");
        return createUserRequest(userRq)
                .spec(created201)
                .extract()
                .as(UserRs.class);
    }

    public void createUserWithNullFields(UserRq userRq) {
        log.info("POST - создание нового пользователя с невалидными данными, 400");
        createUserRequest(userRq)
                .spec(badRequest400);
    }

    public Integer createUserAndGetId(UserRq userRq) {
        log.info("POST - создание нового пользователя и получение его id, 201");
        return createUserRequest(userRq)
                .spec(created201)
                .extract()
                .path("id");
    }

    private ValidatableResponse changeUserRequest(Integer userId, UserRq userRq) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .body(gson.toJson(userRq))
                .log().all()
                .when()
                .put("/user/{userId}")
                .then()
                .log().all();
    }

    public UserRs changeUser(Integer userId, UserRq userRq) {
        log.info("PUT - Изменение пользователя, 202");
        return changeUserRequest(userId, userRq)
                .spec(accepted202)
                .extract()
                .as(UserRs.class);
    }

    public void changeNotExistingUser(Integer userId, UserRq userRq) {
        log.info("PUT - Попытка изменения несуществующего пользователя, 404");
        changeUserRequest(userId, userRq)
                .spec(notFound404);
    }

    private ValidatableResponse getUserRequest(Integer userId) {
        return given()
                .spec(baseSpec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}")
                .then()
                .log().all();
    }

    public UserRs getUserById(Integer userId) {
        log.info("GET - получение пользователя по id, 200");
        return getUserRequest(userId)
                .spec(success200)
                .extract()
                .as(UserRs.class);
    }

    public void getNotExistingUserById(Integer userId) {
        log.info("GET - получение несуществующего пользователя, 204");
        getUserRequest(userId)
                .spec(noContent204);
    }

    public List<UserRs> getUsers() {
        log.info("GET - получение списка пользователей, 200");
        return given()
                .spec(baseSpec)
                .log().all()
                .when()
                .get("/users")
                .then()
                .spec(success200)
                .extract()
                .jsonPath()
                .getList("", UserRs.class);
    }

    public void getUserCars(Integer userId) {
        log.info("GET - получение автомобилей пользователя, 200");
        given()
                .spec(baseSpec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}/cars")
                .then()
                .log().all()
                .spec(success200);
    }

    private ValidatableResponse getUserInfoRequest(Integer userId) {
        return given()
                .spec(baseSpec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}/info")
                .then()
                .log().all();
    }

    public UserInfoRs getUserInfo(Integer userId) {
        log.info("GET - получение информации о пользователе, 200");
        return getUserInfoRequest(userId)
                .spec(success200)
                .extract()
                .as(UserInfoRs.class);
    }

    public void getNotExistingUserInfo(Integer userId) {
        log.info("GET - попытка получения информации о несуществующем пользователе, 204");
        getUserInfoRequest(userId)
                .spec(noContent204);
    }

    private ValidatableResponse deleteUserRequest(Integer userId) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .log().all()
                .when()
                .delete("/user/{userId}")
                .then()
                .log().all();
    }

    public void deleteUser(Integer userId) {
        log.info("DELETE - удаление пользователя, 204");
        deleteUserRequest(userId)
                .spec(noContent204);
    }

    public void deleteNotExistingUser(Integer userId) {
        log.info("DELETE - попытка удаления несуществующего пользователя, 404");
        deleteUserRequest(userId)
                .spec(notFound404);
    }

    public void deleteUserNegative(Integer userId) {
        log.info("DELETE - попытка удаления пользователя с имуществом, 409");
        deleteUserRequest(userId)
                .spec(conflict409);
    }

    private ValidatableResponse addMoneyRequest(Integer userId, BigDecimal amount) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .pathParam("amount", amount)
                .log().all()
                .when()
                .post("/user/{userId}/money/{amount}")
                .then()
                .log().all();
    }

    public UserRs addMoneyToUser(Integer userId, BigDecimal amount) {
        log.info("POST - Начисление денег пользователю, 200");
        return addMoneyRequest(userId, amount)
                .spec(success200)
                .extract()
                .as(UserRs.class);
    }

    public void addMoneyToNotExistingUser(Integer userId, BigDecimal amount) {
        log.info("POST - Попытка начисления денег несуществующему пользователю, 404");
        addMoneyRequest(userId, amount)
                .spec(notFound404);
    }

    public void addInvalidMoneyToUser(Integer userId, BigDecimal amount) {
        log.info("POST - Начисление денег пользователю, отрицательная сумма, 400");
        addMoneyRequest(userId, amount)
                .spec(badRequest400);
    }

    public UserRs buyCar(Integer userId, Integer carId) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().all()
                .when()
                .post("/user/{userId}/buyCar/{carId}")
                .then()
                .log().all()
                .spec(success200)
                .extract()
                .as(UserRs.class);
    }

    public UserRs sellCar(Integer userId, Integer carId) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().all()
                .when()
                .post("/user/{userId}/sellCar/{carId}")
                .then()
                .log().all()
                .spec(success200)
                .extract()
                .as(UserRs.class);
    }
}
