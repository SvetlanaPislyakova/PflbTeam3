package api.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Car {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("engineType")
    @Expose
    private String engineType;
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
