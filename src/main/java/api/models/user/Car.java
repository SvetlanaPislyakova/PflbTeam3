package api.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Car {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("engineType")
    @Expose
    public String engineType;
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
