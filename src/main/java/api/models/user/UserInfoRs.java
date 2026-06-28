package api.models.user;

import api.models.car.CarRs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoRs extends UserRs {

    @SerializedName("cars")
    @Expose
    private List<CarRs> cars;
    @SerializedName("house")
    @Expose
    private Integer house;
}
