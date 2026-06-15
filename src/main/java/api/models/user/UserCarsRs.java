package api.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class UserCarsRs {

    @SerializedName("engineType")
    @Expose
    private String engineType;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mark")
    @Expose
    private String mark;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("price")
    @Expose
    private BigDecimal price;
}
