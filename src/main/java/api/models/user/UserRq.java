package api.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class UserRq {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("money")
    @Expose
    private BigDecimal money;
    @SerializedName("secondName")
    @Expose
    private String secondName;
    @SerializedName("sex")
    @Expose
    private String sex;
}
