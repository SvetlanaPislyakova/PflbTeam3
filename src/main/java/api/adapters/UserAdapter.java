package api.adapters;

import api.models.car.CarRs;
import api.models.user.UserInfoRs;
import api.models.user.UserRq;
import api.models.user.UserRs;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Log4j2
public class UserAdapter extends BaseAdapter {

    private ValidatableResponse createUserRequest(UserRq userRq) {
        return given()
                .spec(getAuthSpec())
                .body(gson.toJson(userRq))
                .log().ifValidationFails()
                .when()
                .post("/user")
                .then()
                .log().ifValidationFails();
    }

    @Step("POST - создание нового пользователя, 201")
    public UserRs createUser(UserRq userRq) {
        log.info("POST - создание нового пользователя, 201");
        long start = System.currentTimeMillis();
        UserRs userRs = createUserRequest(userRq)
                .spec(created201)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .extract()
                .as(UserRs.class);
        long duration = System.currentTimeMillis() - start;
        log.info("Пользователь создан, id = {}, duration = {} ms", userRs.getId(), duration);
        return userRs;
    }

    @Step("POST - создание нового пользователя и получение его id, 201")
    public Integer createUserAndGetId(UserRq userRq) {
        log.info("POST - создание нового пользователя и получение его id, 201");
        long start = System.currentTimeMillis();
        Integer userId = createUserRequest(userRq)
                .spec(created201)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .extract()
                .path("id");
        long duration = System.currentTimeMillis() - start;
        log.info("Пользователь создан, id = {}, duration = {} ms", userId, duration);
        return userId;
    }

    @Step("POST - создание нового пользователя с невалидными данными, 400")
    public void createUserWithNullFields(UserRq userRq) {
        log.info("POST - создание нового пользователя с невалидными данными, 400");
        long start = System.currentTimeMillis();
        createUserRequest(userRq)
                .spec(badRequest400);
        long duration = System.currentTimeMillis() - start;
        log.info("Пользователь с невалидными данными не создан, duration = {} ms", duration);
    }

    private ValidatableResponse changeUserRequest(Integer userId, UserRq userRq) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .body(gson.toJson(userRq))
                .log().ifValidationFails()
                .when()
                .put("/user/{userId}")
                .then()
                .log().ifValidationFails();
    }

    @Step("PUT - Изменение пользователя, 202")
    public UserRs changeUser(Integer userId, UserRq userRq) {
        log.info("PUT - Изменение пользователя, 202");
        long start = System.currentTimeMillis();
        UserRs userRs = changeUserRequest(userId, userRq)
                .spec(accepted202)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .extract()
                .as(UserRs.class);
        long duration = System.currentTimeMillis() - start;
        log.info("Пользователь с id = {} изменен, duration = {} ms", userId, duration);
        return userRs;
    }

    @Step("PUT - Попытка изменения несуществующего пользователя, 404")
    public void changeNotExistingUser(Integer userId, UserRq userRq) {
        log.info("PUT - Попытка изменения несуществующего пользователя, 404");
        long start = System.currentTimeMillis();
        changeUserRequest(userId, userRq)
                .spec(notFound404);
        long duration = System.currentTimeMillis() - start;
        log.info("Несуществующий пользователь с id = {} не изменен, duration = {} ms", userId, duration);
    }

    private ValidatableResponse getUserRequest(Integer userId) {
        return given()
                .spec(getBaseSpec())
                .pathParam("userId", userId)
                .log().ifValidationFails()
                .when()
                .get("/user/{userId}")
                .then()
                .log().ifValidationFails();
    }

    @Step("GET - получение пользователя по id, 200")
    public UserRs getUserById(Integer userId) {
        log.info("GET - получение пользователя по id, 200");
        long start = System.currentTimeMillis();
        UserRs userRs = getUserRequest(userId)
                .spec(success200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .extract()
                .as(UserRs.class);
        long duration = System.currentTimeMillis() - start;
        log.info("Данные о пользователе с id = {} получены, duration = {} ms", userId, duration);
        return userRs;
    }

    @Step("GET - получение несуществующего пользователя, 204")
    public void getNotExistingUserById(Integer userId) {
        log.info("GET - получение несуществующего пользователя, 204");
        long start = System.currentTimeMillis();
        getUserRequest(userId)
                .spec(noContent204);
        long duration = System.currentTimeMillis() - start;
        log.info("Данные о несуществующем пользователе с id = {} не получены, duration = {} ms", userId, duration);
    }

    @Step("GET - получение списка пользователей, 200")
    public List<UserRs> getUsers() {
        log.info("GET - получение списка пользователей, 200");
        long start = System.currentTimeMillis();
        List<UserRs> users = given()
                .spec(getBaseSpec())
                .log().ifValidationFails()
                .when()
                .get("/users")
                .then()
                .log().ifValidationFails()
                .spec(success200)
                .body(matchesJsonSchemaInClasspath("schemas/user-list-schema.json"))
                .extract()
                .jsonPath()
                .getList("", UserRs.class);
        long duration = System.currentTimeMillis() - start;
        log.info("Список пользователей получен, duration = {} ms", duration);
        return users;
    }

    @Step("GET - получение автомобилей пользователя, 200")
    public List<CarRs> getUserCars(Integer userId) {
        log.info("GET - получение автомобилей пользователя, 200");
        long start = System.currentTimeMillis();
        List<CarRs> cars = given()
                .spec(getBaseSpec())
                .pathParam("userId", userId)
                .log().ifValidationFails()
                .when()
                .get("/user/{userId}/cars")
                .then()
                .log().ifValidationFails()
                .spec(success200)
                .body(matchesJsonSchemaInClasspath("schemas/user-cars-schema.json"))
                .extract()
                .jsonPath()
                .getList("", CarRs.class);
        long duration = System.currentTimeMillis() - start;
        log.info("Список автомобилей пользователя с id = {} получен, duration = {} ms", userId, duration);
        return cars;
    }

    private ValidatableResponse getUserInfoRequest(Integer userId) {
        return given()
                .spec(getBaseSpec())
                .pathParam("userId", userId)
                .log().ifValidationFails()
                .when()
                .get("/user/{userId}/info")
                .then()
                .log().ifValidationFails();
    }

    @Step("GET - получение информации об имуществе пользователя, 200")
    public UserInfoRs getUserInfo(Integer userId) {
        log.info("GET - получение информации об имуществе пользователя, 200");
        long start = System.currentTimeMillis();
        UserInfoRs userInfoRs = getUserInfoRequest(userId)
                .spec(success200)
                .body(matchesJsonSchemaInClasspath("schemas/user-info-schema.json"))
                .extract()
                .as(UserInfoRs.class);
        long duration = System.currentTimeMillis() - start;
        log.info("Информация об имуществе пользователя с id = {} получена, duration = {} ms", userId, duration);
        return userInfoRs;
    }

    @Step("GET - попытка получения информации о несуществующем пользователе, 204")
    public void getNotExistingUserInfo(Integer userId) {
        log.info("GET - попытка получения информации о несуществующем пользователе, 204");
        long start = System.currentTimeMillis();
        getUserInfoRequest(userId)
                .spec(noContent204);
        long duration = System.currentTimeMillis() - start;
        log.info("Информация об имуществе несуществующего пользователя с id = {} не получена, duration = {} ms", userId, duration);
    }

    private ValidatableResponse deleteUserRequest(Integer userId) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .log().ifValidationFails()
                .when()
                .delete("/user/{userId}")
                .then()
                .log().ifValidationFails();
    }

    @Step("DELETE - удаление пользователя, 204")
    public void deleteUser(Integer userId) {
        log.info("DELETE - удаление пользователя, 204");
        long start = System.currentTimeMillis();
        deleteUserRequest(userId)
                .spec(noContent204);
        long duration = System.currentTimeMillis() - start;
        log.info("Пользователь с id = {} удален, duration = {} ms", userId, duration);
    }

    @Step("DELETE - попытка удаления несуществующего пользователя, 404")
    public void deleteNotExistingUser(Integer userId) {
        log.info("DELETE - попытка удаления несуществующего пользователя, 404");
        long start = System.currentTimeMillis();
        deleteUserRequest(userId)
                .spec(notFound404);
        long duration = System.currentTimeMillis() - start;
        log.info("Несуществующий пользователь с id = {} не удален, duration = {} ms", userId, duration);

    }

    @Step("DELETE - попытка удаления пользователя с имуществом, 409")
    public void deleteUserNegative(Integer userId) {
        log.info("DELETE - попытка удаления пользователя с имуществом, 409");
        long start = System.currentTimeMillis();
        deleteUserRequest(userId)
                .spec(conflict409);
        long duration = System.currentTimeMillis() - start;
        log.info("Пользователь с id = {} не удален, так как у него есть имущество, duration = {} ms", userId, duration);
    }

    private ValidatableResponse addMoneyRequest(Integer userId, BigDecimal amount) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .pathParam("amount", amount)
                .log().ifValidationFails()
                .when()
                .post("/user/{userId}/money/{amount}")
                .then()
                .log().ifValidationFails();
    }

    @Step("POST - Начисление денег пользователю, 200")
    public UserRs addMoneyToUser(Integer userId, BigDecimal amount) {
        log.info("POST - Начисление денег пользователю, 200");
        long start = System.currentTimeMillis();
        UserRs userRs = addMoneyRequest(userId, amount)
                .spec(success200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .extract()
                .as(UserRs.class);
        long duration = System.currentTimeMillis() - start;
        log.info("Деньги пользователю с id = {} добавлены, duration = {} ms", userId, duration);
        return userRs;
    }

    @Step("POST - Попытка начисления денег несуществующему пользователю, 404")
    public void addMoneyToNotExistingUser(Integer userId, BigDecimal amount) {
        log.info("POST - Попытка начисления денег несуществующему пользователю, 404");
        long start = System.currentTimeMillis();
        addMoneyRequest(userId, amount)
                .spec(notFound404);
        long duration = System.currentTimeMillis() - start;
        log.info("Деньги не добавлены, так как пользователь с id = {} не существует, duration = {} ms", userId, duration);
    }

    @Step("POST - Начисление денег пользователю, отрицательная сумма, 400")
    public void addInvalidMoneyToUser(Integer userId, BigDecimal amount) {
        log.info("POST - Начисление денег пользователю, отрицательная сумма, 400");
        long start = System.currentTimeMillis();
        addMoneyRequest(userId, amount)
                .spec(badRequest400);
        long duration = System.currentTimeMillis() - start;
        log.info("Деньги не добавлены, так как сумма отрицательная, duration = {} ms", duration);
    }

    private ValidatableResponse buyCarRequest(Integer userId, Integer carId) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().ifValidationFails()
                .when()
                .post("/user/{userId}/buyCar/{carId}")
                .then()
                .log().ifValidationFails();
    }

    @Step("POST - Покупка автомобиля пользователем, 200")
    public void buyCar(Integer userId, Integer carId) {
        log.info("POST - Покупка автомобиля пользователем, 200");
        long start = System.currentTimeMillis();
        buyCarRequest(userId, carId)
                .spec(success200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
        long duration = System.currentTimeMillis() - start;
        log.info("Пользователем с id = {} куплен автомобиль id = {}, duration = {} ms", userId, carId, duration);
    }

    private ValidatableResponse sellCarRequest(Integer userId, Integer carId) {
        return given()
                .spec(getAuthSpec())
                .pathParam("userId", userId)
                .pathParam("carId", carId)
                .log().ifValidationFails()
                .when()
                .post("/user/{userId}/sellCar/{carId}")
                .then()
                .log().ifValidationFails();
    }

    @Step("POST - Продажа автомобиля пользователем, 200")
    public void sellCar(Integer userId, Integer carId) {
        log.info("POST - Продажа автомобиля пользователем, 200");
        long start = System.currentTimeMillis();
        sellCarRequest(userId, carId)
                .spec(success200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
        long duration = System.currentTimeMillis() - start;
        log.info("Пользователем с id = {} продан автомобиль id = {}, duration = {} ms", userId, carId, duration);
    }
}
