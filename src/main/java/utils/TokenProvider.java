package utils;

import api.adapters.LoginAdapter;
import api.models.LoginRq;

public class TokenProvider {

    private static String accessToken;

    public static String getAccessToken() {
        if (accessToken == null) {
            LoginRq rq = LoginRq.builder()
                    .username(System.getProperty("email", PropertyReader.getProperty("email")))
                    .password(System.getProperty("password", PropertyReader.getProperty("password")))
                    .build();
            accessToken = LoginAdapter.getAccessToken(rq);
        }
        return accessToken;
    }
}
