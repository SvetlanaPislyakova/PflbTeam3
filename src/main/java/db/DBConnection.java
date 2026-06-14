package db;

import lombok.extern.log4j.Log4j2;

import java.sql.*;

@Log4j2
public class DBConnection {

    private final String URL = "jdbc:postgresql://82.142.167.37:4832/pflb_trainingcenter";
    protected static final String DB_USER = "";
    protected static final String DB_PASSWORD = "";

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void connect() {
        try {
            connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
            statement = connection.createStatement();
            log.info("Соединение с БД выполнено");
        } catch (SQLException e) {
            throw new RuntimeException("Соединение с БД не выполнено");
        }
    }

    public ResultSet selectById(String tableName, Long id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE id = %s;", tableName, id);
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet selectList(String tableName, String colName) {
        try {
            String sql = String.format("SELECT %s FROM %s;", colName, tableName);
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if(resultSet != null) resultSet.close();
            if(statement != null) statement.close();
            if(connection != null) connection.close();
            log.info("Подключение к БД закрыто");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при закрытии соединения с БД");
        }
    }
}
