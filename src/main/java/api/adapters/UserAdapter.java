package api.adapters;

import api.models.UserRq;
import api.models.UserRs;

import static io.restassured.RestAssured.given;

public class UserAdapter extends BaseAdapter {

    public static UserRs createUser(UserRq userRq, String token) {
        return given()
                .spec(spec)
                .header("Authorization", "Bearer" + token)
                .body(gson.toJson(userRq))
                .log().all()
                .when()
                .post("/user")
                .then()
                .log().all()
                .spec(ok201)
                .extract()
                .as(UserRs.class);
    }

    public static void getUserById(Integer userId) {
        given()
                .spec(spec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}")
                .then()
                .log().all()
                .spec(ok200)
                .extract()
                .as(UserRs.class);
    }

    public static void getUsers() {
        given()
                .spec(spec)
                .log().all()
                .when()
                .get("/users")
                .then()
                .log().all()
                .spec(ok200);
    }

    public static void getUserCars(Integer userId) {
        given()
                .spec(spec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}/cars")
                .then()
                .log().all()
                .spec(ok200);
    }

    public static void getUserInfo(Integer userId) {
        given()
                .spec(spec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .get("/user/{userId}/info")
                .then()
                .log().all()
                .spec(ok200);
    }

    public static void deleteUser(Integer userId) {
        given()
                .spec(spec)
                .pathParam("userId", userId)
                .log().all()
                .when()
                .delete("/user/{userId}")
                .then()
                .log().all()
                .spec(ok201);
    }
}
