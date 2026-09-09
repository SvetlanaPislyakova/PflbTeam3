package api.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class UserRs {

    @JsonProperty("age")
    private Integer age;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("money")
    private BigDecimal money;
    @JsonProperty("secondName")
    private String secondName;
    @JsonProperty("sex")
    private String sex;
}
