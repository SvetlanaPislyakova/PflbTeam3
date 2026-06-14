package api.adapters;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAdapter {

    static Gson gson = new Gson();

    public static RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri("http://82.142.167.37:4879")
            .setContentType(ContentType.JSON)
            .build();

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

    public static ResponseSpecification forbidden403 = new ResponseSpecBuilder()
            .expectStatusCode(403)
            .build();

    public static ResponseSpecification badRequest400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();
}
