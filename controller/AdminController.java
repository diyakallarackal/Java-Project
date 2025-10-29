package controller;

import model.DatabaseManager;
import model.User;
import model.WorkoutStats;
import view.AdminView;

import javax.swing.*;
import java.awt.*; // ADD THIS LINE
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private final DatabaseManager db;
    private final AdminView adminView;
    private JFrame adminFrame;

    public AdminController(DatabaseManager db, AdminView adminView) {
        this.db = db;
        this.adminView = adminView;
        initializeEventListeners();
    }

    private void initializeEventListeners() {
        adminView.getRefreshButton().addActionListener(new RefreshButtonListener());
        adminView.getViewStatsButton().addActionListener(new StatsButtonListener());
        adminView.getLogoutButton().addActionListener(new LogoutButtonListener());
    }

    public void showAdminView() {
        try {
            adminFrame = new JFrame("Admin Dashboard - FitTrack");
            adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            adminFrame.setSize(800, 600);
            adminFrame.setLocationRelativeTo(null);
            adminFrame.add(adminView);
            refreshUserList();
            adminFrame.setVisible(true);
        } catch (Exception e) {
            handleViewError("Error opening admin dashboard", e);
        }
    }

    private class RefreshButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshUserList();
        }
    }

    private class StatsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showUserStats();
        }
    }

    private class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logout();
        }
    }

    private void refreshUserList() {
        SwingWorker<List<User>, Void> worker = new SwingWorker<List<User>, Void>() {
            @Override
            protected List<User> doInBackground() throws Exception {
                return db.getAllUsers();
            }

            @Override
            protected void done() {
                try {
                    List<User> users = get();
                    updateUserList(users);
                } catch (Exception e) {
                    handleDatabaseError("Error loading users", e);
                }
            }
        };
        worker.execute();
    }

    private void updateUserList(List<User> users) {
        DefaultListModel<String> model = adminView.getUserListModel();
        model.clear();

        if (users.isEmpty()) {
            model.addElement("No users found");
        } else {
            for (User user : users) {
                String userInfo = String.format("%s (Weight: %.1f kg)",
                        user.getUsername(), user.getBodyWeight());
                model.addElement(userInfo);
            }
        }
        showTempMessage("User list refreshed successfully", Color.BLUE);
    }

    private void showUserStats() {
        SwingWorker<List<WorkoutStats>, Void> worker = new SwingWorker<List<WorkoutStats>, Void>() {
            @Override
            protected List<WorkoutStats> doInBackground() throws Exception {
                return db.getUserWorkoutStats();
            }

            @Override
            protected void done() {
                try {
                    List<WorkoutStats> stats = get();
                    updateStatsDisplay(stats);
                } catch (Exception e) {
                    handleDatabaseError("Error loading statistics", e);
                }
            }
        };
        worker.execute();
    }

    private void updateStatsDisplay(List<WorkoutStats> stats) {
        if (stats.isEmpty()) {
            adminView.updateStats("No workout statistics available");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<html><h3>Workout Statistics</h3>");
        sb.append("<table border='0' cellpadding='3'>");
        sb.append("<tr><th>User</th><th>Workouts</th><th>Avg Weight</th></tr>");

        for (WorkoutStats stat : stats) {
            sb.append("<tr>")
                    .append("<td>").append(stat.getUsername()).append("</td>")
                    .append("<td align='center'>").append(stat.getTotalWorkouts()).append("</td>")
                    .append("<td align='right'>").append(String.format("%.1f kg", stat.getAvgBodyWeight())).append("</td>")
                    .append("</tr>");
        }
        sb.append("</table></html>");

        adminView.updateStats(sb.toString());
        showTempMessage("Statistics loaded successfully", Color.BLUE);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                adminView,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (adminFrame != null) {
                adminFrame.dispose();
            }
        }
    }

    private void handleDatabaseError(String message, Exception e) {
        String fullMessage = message + ": " + e.getMessage();
        JOptionPane.showMessageDialog(
                adminView,
                fullMessage,
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
        System.err.println(message);
        e.printStackTrace();
    }

    private void handleViewError(String message, Exception e) {
        JOptionPane.showMessageDialog(
                null,
                message + ": " + e.getMessage(),
                "View Error",
                JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }

    private void showTempMessage(String message, Color color) {
        // This could be enhanced to show temporary status messages
        System.out.println(message);
    }
}