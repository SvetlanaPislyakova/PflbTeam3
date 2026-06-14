package api.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRq {

    private final String username;
    private final String password;
}
