package api.adapters;

import api.models.LoginRq;
import api.models.LoginRs;

import static io.restassured.RestAssured.given;

public class LoginAdapter extends BaseAdapter {

    public static String getAccessToken(LoginRq rq) {
        return given()
                .spec(spec)
                .body(gson.toJson(rq))
                .when()
                .post("/login")
                .then()
                .spec(ok202)
                .extract()
                .path("access_token");
    }

}
