package api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class HouseRq {

    @SerializedName("floorCount")
    @Expose
    private Integer floorCount;
    @SerializedName("price")
    @Expose
    private BigDecimal price;
    @SerializedName("parkingPlaces")
    @Expose
    private List<Object> parkingPlaces;
    @SerializedName("lodgers")
    @Expose
    private List<Object> lodgers;
}
