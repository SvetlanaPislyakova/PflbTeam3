package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRs {

    @JsonProperty("access_token")
    String accessToken;
}
