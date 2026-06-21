package ui.dto;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class User {

    private static final Faker faker = new Faker();

    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final Integer age;
    private final String sex;
    private final BigDecimal money;
}
