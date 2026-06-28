package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRs {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
}
