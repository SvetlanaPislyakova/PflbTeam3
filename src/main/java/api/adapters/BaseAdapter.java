package api.adapters;

import api.models.login.LoginRq;
import com.google.gson.Gson;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;

public class BaseAdapter {

    private static final String email = System.getProperty("email", PropertyReader.getProperty("email"));
    private static final String password = System.getProperty("password", PropertyReader.getProperty("password"));
    static Gson gson = new Gson();

    private String getBaseUri() {
        return PropertyReader.getProperty("baseUri");
    }

    protected RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(getBaseUri())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

    private String getAccessToken() {
        LoginRq rq = LoginRq.builder()
                .username(email)
                .password(password)
                .build();
        return given()
                .spec(getBaseSpec())
                .body(gson.toJson(rq))
                .when()
                .post("/login")
                .then()
                .spec(accepted202)
                .extract()
                .path("access_token");
    }

    protected RequestSpecification getAuthSpec() {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpec())
                .addHeader("Authorization", "Bearer " + getAccessToken())
                .build();
    }

    public static ResponseSpecification success200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification created201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification accepted202 = new ResponseSpecBuilder()
            .expectStatusCode(202)
            .build();

    public static ResponseSpecification noContent204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification badRequest400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();

    public static ResponseSpecification notFound404 = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .build();

    public static ResponseSpecification conflict409 = new ResponseSpecBuilder()
            .expectStatusCode(409)
            .build();
}
