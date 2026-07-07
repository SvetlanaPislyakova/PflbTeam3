package ui.steps;

import db.DBConnection;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import ui.dto.User;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBSteps {

    @Step("Получение списка из базы данных")
    public List<String> getListFromDB(String table, String colName) {
        DBConnection connection = new DBConnection();
        List<String> values = new ArrayList<>();
        try {
            connection.connect();
            ResultSet result = connection.selectList(table, colName);
            while (result.next()) {
                values.add(result.getString(colName));
            }
            return values;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    @Step("Проверка наличия в БД пользователя с id = {userId}")
    public void checkUserInDB(User user, Integer userId) {
        SoftAssertions softly = new SoftAssertions();
        DBConnection connection = new DBConnection();
        try {
            connection.connect();
            ResultSet result = connection.selectById("person", userId);
            while (result.next()) {
                softly.assertThat(result.getInt("Age")).isEqualTo(user.getAge());
                softly.assertThat(result.getString("first_name")).isEqualTo(user.getFirstName());
                softly.assertThat(result.getString("second_name")).isEqualTo(user.getLastName());
                softly.assertThat(result.getBigDecimal("money")).isEqualByComparingTo(user.getMoney());
                softly.assertThat(result.getBoolean("sex")).isEqualTo(user.getSex().equals("MALE"));
            }
            softly.assertAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    @Step("Проверка наличия в БД пользователя с id = {userId}")
    public boolean isUserExistsInDB(Integer userId) {
        DBConnection connection = new DBConnection();
        try {
            connection.connect();
            ResultSet result = connection.selectById("person", userId);
            return result.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    @Step("Получение баланса пользователя из БД")
    public BigDecimal getUserBalance(int userId) {
        DBConnection connection = new DBConnection();
        try {
            connection.connect();
            ResultSet result = connection.selectById("person", userId);
            if (result.next()) {
                return result.getBigDecimal("money");
            }
            return BigDecimal.ZERO;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    @Step("Проверка наличия автомобиля в БД с id = {carId}")
    public boolean isCarExistsInDB(Integer carId) {
        DBConnection connection = new DBConnection();
        try {
            connection.connect();
            ResultSet result = connection.selectById("car", carId);
            return result.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    @Step("Проверка наличия дома в БД с id = {houseId}")
    public boolean isHouseExistsInDB(Integer houseId) {
        DBConnection connection = new DBConnection();
        try {
            connection.connect();
            ResultSet result = connection.selectById("house", houseId);
            return result.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }
}
