package api.adapters;

import api.models.login.LoginRq;

import static io.restassured.RestAssured.given;

public class LoginAdapter extends BaseAdapter {

    public static String getAccessToken(LoginRq rq) {
        return given()
                .spec(spec)
                .body(gson.toJson(rq))
                .when()
                .post("/login")
                .then()
                .spec(accepted202)
                .extract()
                .path("access_token");
    }
}
