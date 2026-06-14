package api.models.user;

import com.github.javafaker.Faker;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserRq {

    private static Faker faker = new Faker();

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("age")
    @Expose
    @Builder.Default
    public Integer age = faker.number().numberBetween(10, 100);
    @SerializedName("firstName")
    @Expose
    @Builder.Default
    public String firstName = faker.name().firstName();
    @SerializedName("money")
    @Expose
    @Builder.Default
    public BigDecimal money = BigDecimal.valueOf
            (faker.number().randomDouble(2, 0, 1000000));
    @SerializedName("secondName")
    @Expose
    @Builder.Default
    public String secondName = faker.name().lastName();
    @SerializedName("sex")
    @Expose
    @Builder.Default
    public String sex = faker.options().option("MALE", "FEMALE");
}
