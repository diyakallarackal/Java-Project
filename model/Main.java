package model;
import controller.LoginController;
import model.DatabaseManager;
import view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Use SwingUtilities for thread-safe GUI operations
        SwingUtilities.invokeLater(() -> {
            try {
                initializeAndShowGUI();
            } catch (Exception e) {
                handleFatalError("Failed to start application", e);
            }
        });
    }

    private static void initializeAndShowGUI() {
        DatabaseManager db = new DatabaseManager();

        try {
            // Connect to database
            db.connect();
            System.out.println("Database connection established successfully");

            // Create and setup login view
            LoginView loginView = new LoginView();
            new LoginController(db, loginView); // Controller is used but we don't need to store it

            // Create and show login frame
            createLoginFrame(loginView);

            // Add shutdown hook to close database connection
            addShutdownHook(db);

        } catch (Exception e) {
            handleDatabaseConnectionError(e);
        }
    }

    private static void createLoginFrame(LoginView loginView) {
        JFrame loginFrame = new JFrame("FitTrack - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(350, 250);
        loginFrame.setLocationRelativeTo(null); // Center the window
        loginFrame.setResizable(false);
        loginFrame.add(loginView);
        loginFrame.setVisible(true);
    }

    private static void addShutdownHook(DatabaseManager db) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                db.disconnect();
                System.out.println("Application shutdown completed");
            } catch (Exception e) {
                System.err.println("Error during shutdown: " + e.getMessage());
            }
        }));
    }

    private static void handleDatabaseConnectionError(Exception e) {
        String errorMessage = "Cannot connect to database: " + e.getMessage() +
                "\nPlease check your database connection and try again.";

        JOptionPane.showMessageDialog(
                null,
                errorMessage,
                "Database Connection Error",
                JOptionPane.ERROR_MESSAGE
        );

        System.err.println("Database connection failed:");
        e.printStackTrace();
        System.exit(1);
    }

    private static void handleFatalError(String message, Exception e) {
        JOptionPane.showMessageDialog(
                null,
                message + ": " + e.getMessage(),
                "Fatal Error",
                JOptionPane.ERROR_MESSAGE
        );

        System.err.println(message);
        e.printStackTrace();
        System.exit(1);
    }
}