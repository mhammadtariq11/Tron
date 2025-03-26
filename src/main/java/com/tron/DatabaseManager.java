package main.java.com.tron;

import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/highscores_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "progtech";

    // Connect to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Save or update the winner's score
    public static void saveScore(String playerName, int score) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Check if player already has a record
            String checkQuery = "SELECT score FROM highscore WHERE player_name = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, playerName);
                try (ResultSet resultSet = checkStmt.executeQuery()) {
                    if (resultSet.next()) {
                        // Player exists, so increment their score
                        int currentScore = resultSet.getInt("score");
                        String updateQuery = "UPDATE highscore SET score = ? WHERE player_name = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, currentScore + score);  // Increment score with the current score of the winner
                            updateStmt.setString(2, playerName);
                            updateStmt.executeUpdate();
                        }
                    } else {
                        // Player does not exist, so insert a new record
                        String insertQuery = "INSERT INTO highscore (player_name, score) VALUES (?, ?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                            insertStmt.setString(1, playerName);
                            insertStmt.setInt(2, score);  // Insert the winner's score
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving score: " + e.getMessage());
            e.printStackTrace();
        }
    }



    // Get top 10 high scores
    public static List<String> getTopScores() {
        List<String> topScores = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String query = "SELECT player_name, score FROM highscore ORDER BY score DESC LIMIT 10";
            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    String playerName = resultSet.getString("player_name");
                    int score = resultSet.getInt("score");
                    topScores.add(playerName + ": " + score);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving top scores: " + e.getMessage());
            e.printStackTrace();
        }
        return topScores;
    }
}
