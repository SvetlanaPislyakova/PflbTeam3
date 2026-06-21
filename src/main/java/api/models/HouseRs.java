package api.models;

import api.models.user.UserRs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HouseRs {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("floorCount")
    @Expose
    private Integer floorCount;
    @SerializedName("price")
    @Expose
    private BigDecimal price;
    @SerializedName("lodgers")
    @Expose
    private List<UserRs> lodgers;
}
