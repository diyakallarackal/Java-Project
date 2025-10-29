package model;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL ="jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn;

    public void connect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to XAMPP MySQL successfully!");
        } catch (Exception e) {
            throw new SQLException("Database connection failed: " + e.getMessage());
        }
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✅ Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    public boolean authenticate(String username, String password, String role) throws SQLException {
        String query = "SELECT * FROM accounts WHERE username = ? AND password = ? AND role = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT username, password, bodyWeight FROM accounts WHERE role = 'User'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("password"), rs.getDouble("bodyWeight")));
            }
        }
        return users;
    }

    public boolean addUser(User user) throws SQLException {
        String query = "INSERT INTO accounts (username, password, role, bodyWeight) VALUES (?, ?, 'User', ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setDouble(3, user.getBodyWeight());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateUserWeight(String username, double weight) throws SQLException {
        String query = "UPDATE accounts SET bodyWeight = ? WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, weight);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        }
    }

    public List<WorkoutStats> getUserWorkoutStats() throws SQLException {
        List<WorkoutStats> stats = new ArrayList<>();
        String query = "SELECT a.username, COUNT(w.id) as totalWorkouts, AVG(a.bodyWeight) as avgBodyWeight " +
                "FROM accounts a LEFT JOIN workouts w ON a.username = w.username " +
                "WHERE a.role = 'User' GROUP BY a.username";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                stats.add(new WorkoutStats(rs.getString("username"), rs.getInt("totalWorkouts"), rs.getDouble("avgBodyWeight")));
            }
        }
        return stats;
    }

    public boolean addWorkout(String username, String exercise, double weight, int reps) throws SQLException {
        String query = "INSERT INTO workouts (username, exercise, weight, reps) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, exercise);
            ps.setDouble(3, weight);
            ps.setInt(4, reps);
            return ps.executeUpdate() > 0;
        }
    }

    public List<String> getUserWorkouts(String username) throws SQLException {
        List<String> workouts = new ArrayList<>();
        String query = "SELECT exercise, weight, reps FROM workouts WHERE username = ? ORDER BY workout_date DESC";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                workouts.add(rs.getString("exercise") + " - " + rs.getDouble("weight") + "kg x " + rs.getInt("reps") + " reps");
            }
        }
        return workouts;
    }
}