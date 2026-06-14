package api.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public class UserInfoRs {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("cars")
    @Expose
    public List<Car> cars;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("house")
    @Expose
    public Integer house;
    @SerializedName("money")
    @Expose
    public BigDecimal money;
    @SerializedName("secondName")
    @Expose
    public String secondName;
    @SerializedName("sex")
    @Expose
    public String sex;
}
