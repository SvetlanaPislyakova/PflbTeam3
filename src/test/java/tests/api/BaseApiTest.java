package tests.api;

import api.adapters.LoginAdapter;
import api.models.LoginRq;
import utils.PropertyReader;

public class BaseApiTest {

    protected static final String email = System.getProperty("email", PropertyReader.getProperty("email"));
    protected static final String password = System.getProperty("password", PropertyReader.getProperty("password"));

    protected final String accessToken;

    public BaseApiTest() {
        LoginRq rq = LoginRq.builder()
                .username(email)
                .password(password)
                .build();
        this.accessToken = LoginAdapter.getAccessToken(rq);
    }
}
