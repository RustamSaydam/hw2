package repository;

import config.AppConfig;
import entity.Director;

import java.sql.*;

public class DirectorDAO {
    public Director findDirectorBytaskId(Integer taskId) {
        Director director = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT * FROM directors WHERE directorId = (SELECT directorId FROM tasks WHERE taskId = ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, taskId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        director = mapDirector(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return director;
    }

    public Director findDirectorByName(String name) {
        Director director = null;

        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT * FROM directors WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        director = mapDirector(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return director;
    }

    public void addDirector(Director director) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "INSERT INTO directors (name) VALUES (?)";

            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, director.getName());

                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedDirectorId = generatedKeys.getInt(1);
                        director.setDirectorId(generatedDirectorId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Director mapDirector(ResultSet resultSet) throws SQLException {
        Director director = new Director();
        director.setDirectorId(resultSet.getInt("directorId"));
        director.setName(resultSet.getString("name"));

        return director;
    }
}