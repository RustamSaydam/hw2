package repository;

import entity.Director;
import entity.Task;
import entity.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public List<Task> findTasksByParams(Integer taskId, String title, Integer directorId, Integer year, String genre, String description) {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {
            StringBuilder query = new StringBuilder("SELECT * FROM tasks WHERE 0=0");

            if (taskId != null) query.append(" AND taskId = ?");
            if (title != null) query.append(" AND title = ?");
            if (directorId != null) query.append(" AND directorId = ?");
            if (year != null) query.append(" AND year = ?");
            if (genre != null) query.append(" AND genre = ?");
            if (description != null) query.append(" AND description = ?");

            try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
                int parameterIndex = 1;

                if (taskId != null) statement.setInt(parameterIndex++, taskId);
                if (title != null) statement.setString(parameterIndex++, title);
                if (directorId != null) statement.setInt(parameterIndex++, directorId);
                if (year != null) statement.setInt(parameterIndex++, year);
                if (genre != null) statement.setString(parameterIndex++, genre);
                if (description != null) statement.setString(parameterIndex++, description);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Task task = new Task();
                        task.setTaskId(resultSet.getInt("taskId"));
                        task.setTitle(resultSet.getString("title"));
                        task.setYear(resultSet.getInt("year"));
                        task.setGenre(resultSet.getString("genre"));
                        task.setDescription(resultSet.getString("description"));

                        Director director = findDirectorByTaskId(task.getTaskId());
                        task.setDirector(director);

                        List<Review> reviews = findReviewsByTaskId(task.getTaskId());
                        task.setReviews(reviews);

                        tasks.add(task);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return tasks;
    }


    private Director findDirectorByTaskId(Integer taskId) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT * FROM directors WHERE directorId IN (SELECT directorId FROM tasks WHERE taskId = ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, taskId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Director director = new Director();
                        director.setDirectorId(resultSet.getInt("directorId"));
                        director.setName(resultSet.getString("name"));
                        return director;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Review> findReviewsByTaskId(Integer taskId) {
        List<Review> reviews = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "SELECT * FROM reviews WHERE taskId = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, taskId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Review review = new Review();
                        review.setReviewId(resultSet.getInt("reviewId"));
                        review.setText(resultSet.getString("text"));
                        reviews.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public void addTask(Task task) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "INSERT INTO tasks (title, year, genre, description, directorId) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, task.getTitle());
                statement.setInt(2, task.getYear());
                statement.setString(3, task.getGenre());
                statement.setString(4, task.getDescription());

                if (task.getDirector() != null) {
                    statement.setInt(5, task.getDirector().getDirectorId());
                } else {
                    statement.setNull(5, Types.INTEGER);
                }

                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedTaskId = generatedKeys.getInt(1);
                        task.setTaskId(generatedTaskId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(Task task) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "UPDATE tasks SET title = ?, year = ?, genre = ?, description = ?, directorId = ? WHERE taskId = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, task.getTitle());
                statement.setInt(2, task.getYear());
                statement.setString(3, task.getGenre());
                statement.setString(4, task.getDescription());

                if (task.getDirector() != null) {
                    statement.setInt(5, task.getDirector().getDirectorId());
                } else {
                    statement.setNull(5, Types.INTEGER);
                }

                statement.setInt(6, task.getTaskId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(String title) {
        try (Connection connection = ConnectionManager.getConnection()) {
            String query = "DELETE FROM tasks WHERE title = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, title);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
