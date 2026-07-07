package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRq {

    @SerializedName("username")
    @Expose
    private final String username;
    @SerializedName("password")
    @Expose
    private final String password;
}
