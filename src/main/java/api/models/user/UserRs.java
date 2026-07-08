package api.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserRs {

    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("id")
    @Expose
    private Integer id;
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
