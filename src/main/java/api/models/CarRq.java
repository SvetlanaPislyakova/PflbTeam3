package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CarRq {

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
