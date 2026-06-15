package api.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public class UserInfoRs {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("cars")
    @Expose
    private List<Car> cars;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("house")
    @Expose
    private Integer house;
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
