package api.models.house;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ParkingPlaceRs {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isWarm")
    @Expose
    private Boolean isWarm;
    @SerializedName("isCovered")
    @Expose
    private Boolean isCovered;
    @SerializedName("placesCount")
    @Expose
    private Integer placesCount;
}