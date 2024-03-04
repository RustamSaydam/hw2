package config;


public class AppConfig {

    public static String getDbUrl() {
        return "jdbc:postgresql://localhost:5432/database";
    }

    public static String getDbUser() {
        return "postgres";
    }

    public static String getDbPassword() {
        return "testtest";
    }

    public static String getDbDriver() {
        return "org.postgresql.Driver";
    }

}
