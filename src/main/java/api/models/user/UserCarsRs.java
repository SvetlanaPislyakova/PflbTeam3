package api.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class UserCarsRs {

    @SerializedName("engineType")
    @Expose
    public String engineType;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("mark")
    @Expose
    public String mark;
    @SerializedName("model")
    @Expose
    public String model;
    @SerializedName("price")
    @Expose
    public BigDecimal price;
}
