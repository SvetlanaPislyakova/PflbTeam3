package api.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRs {

    @SerializedName("age")
    @Expose
    public Integer age;
    @SerializedName("firstName")
    @Expose
    public String firstName;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("money")
    @Expose
    public Integer money;
    @SerializedName("secondName")
    @Expose
    public String secondName;
    @SerializedName("sex")
    @Expose
    public String sex;
}
