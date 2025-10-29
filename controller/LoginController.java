package controller;

import model.DatabaseManager;
import view.LoginView;
import view.AdminView;
import view.UserView;

import javax.swing.*;
import java.awt.*; // ADD THIS LINE
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginController {
    private final DatabaseManager db;
    private final LoginView loginView;

    public LoginController(DatabaseManager db, LoginView loginView) {
        this.db = db;
        this.loginView = loginView;
        initializeEventListeners();
    }

    private void initializeEventListeners() {
        loginView.getLoginButton().addActionListener(new LoginButtonListener());
        loginView.getPasswordField().addActionListener(new LoginButtonListener());
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            login();
        }
    }

    private void login() {
        String username = loginView.getUsernameField().getText().trim();
        String password = new String(loginView.getPasswordField().getPassword());
        String role = (String) loginView.getRoleCombo().getSelectedItem();

        try {
            validateLoginInput(username, password, role);
            authenticateUser(username, password, role);
        } catch (IllegalArgumentException ex) {
            loginView.getMessageLabel().setText(ex.getMessage());
            loginView.getMessageLabel().setForeground(Color.RED);
        } catch (SQLException ex) {
            handleDatabaseError(ex);
        } catch (Exception ex) {
            handleUnexpectedError(ex);
        }
    }

    private void validateLoginInput(String username, String password, String role) {
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("Please select a role");
        }
    }

    private void authenticateUser(String username, String password, String role) throws SQLException {
        if (db.authenticate(username, password, role)) {
            loginView.getMessageLabel().setText("Login successful! Redirecting...");
            loginView.getMessageLabel().setForeground(Color.BLUE);

            SwingUtilities.invokeLater(() -> {
                closeLoginWindow();
                openDashboard(username, role);
            });
        } else {
            throw new IllegalArgumentException("Invalid username, password or role");
        }
    }

    private void closeLoginWindow() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(loginView);
        if (frame != null) {
            frame.dispose();
        }
    }

    private void openDashboard(String username, String role) {
        try {
            if ("Admin".equals(role)) {
                openAdminDashboard();
            } else {
                openUserDashboard(username);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error opening dashboard: " + ex.getMessage(),
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAdminDashboard() {
        AdminView adminView = new AdminView();
        AdminController adminController = new AdminController(db, adminView);
        adminController.showAdminView();
    }

    private void openUserDashboard(String username) {
        UserView userView = new UserView();
        UserController userController = new UserController(db, userView, username);
        userController.showUserView();
    }

    private void handleDatabaseError(SQLException ex) {
        String errorMessage = "Database error: " + ex.getMessage();
        loginView.getMessageLabel().setText(errorMessage);
        loginView.getMessageLabel().setForeground(Color.RED);
        System.err.println("Database error during login:");
        ex.printStackTrace();
    }

    private void handleUnexpectedError(Exception ex) {
        String errorMessage = "Unexpected error: " + ex.getMessage();
        JOptionPane.showMessageDialog(loginView,
                errorMessage,
                "System Error",
                JOptionPane.ERROR_MESSAGE);
        System.err.println("Unexpected error during login:");
        ex.printStackTrace();
    }
}