package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarRq {

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
    private Double price;
}
