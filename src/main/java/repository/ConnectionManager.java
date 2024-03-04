package repository;

import config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL = AppConfig.getDbUrl();
    private static final String USER = AppConfig.getDbUser();
    private static final String PASSWORD = AppConfig.getDbPassword();
    private static final String DRIVER_REGISTRATION_ERROR_MESSAGE = "Failed to register driver";

    static {
        registerDriver();
    }

    private static void registerDriver() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(DRIVER_REGISTRATION_ERROR_MESSAGE, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}