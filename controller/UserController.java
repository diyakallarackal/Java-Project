package controller;

import model.DatabaseManager;
import view.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    private final DatabaseManager db;
    private final UserView userView;
    private final String username;
    private JFrame userFrame;

    public UserController(DatabaseManager db, UserView userView, String username) {
        this.db = db;
        this.userView = userView;
        this.username = username;

        // Initialize the view and event listeners
        initializeUserView();
        initializeEventListeners();
        loadUserWorkouts(); // Load existing workouts when controller starts
    }

    private void initializeUserView() {
        // Set welcome message
        userView.setWelcomeMessage(username);
    }

    private void initializeEventListeners() {
        // Using inner classes for different actions
        userView.getAddWorkoutButton().addActionListener(new AddWorkoutListener());
        userView.getUpdateWeightButton().addActionListener(new UpdateWeightListener());
        userView.getLogoutButton().addActionListener(new LogoutListener());

        // Add Enter key support for input fields
        userView.getRepsField().addActionListener(new AddWorkoutListener());
        userView.getBodyWeightField().addActionListener(new UpdateWeightListener());
    }

    public void showUserView() {
        try {
            userFrame = new JFrame("User Dashboard - " + username);
            userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            userFrame.setSize(700, 500);
            userFrame.setLocationRelativeTo(null);
            userFrame.add(userView);
            userFrame.setVisible(true);
        } catch (Exception e) {
            handleViewError("Error opening user dashboard", e);
        }
    }

    // Load user's existing workouts from database
    private void loadUserWorkouts() {
        try {
            List<String> workouts = db.getUserWorkouts(username);
            DefaultListModel<String> model = userView.getWorkoutListModel();
            model.clear();

            for (String workout : workouts) {
                model.addElement(workout);
            }

            System.out.println("Loaded " + workouts.size() + " workouts for user: " + username);

        } catch (SQLException e) {
            System.err.println("Error loading workouts: " + e.getMessage());
            // Don't show error to user - might be first time user with no workouts
        }
    }

    // Inner class for adding workouts
    private class AddWorkoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addWorkout();
        }
    }

    // Inner class for updating weight
    private class UpdateWeightListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateWeight();
        }
    }

    // Inner class for logout
    private class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logout();
        }
    }

    private void addWorkout() {
        String exercise = userView.getExerciseField().getText().trim();
        String weightStr = userView.getWeightField().getText().trim();
        String repsStr = userView.getRepsField().getText().trim();

        try {
            // Validate input
            validateWorkoutInput(exercise, weightStr, repsStr);

            // Parse numeric values
            double weight = Double.parseDouble(weightStr);
            int reps = Integer.parseInt(repsStr);

            // Validate positive values
            if (weight <= 0 || reps <= 0) {
                throw new IllegalArgumentException("Weight and reps must be positive numbers");
            }

            // Save to database
            boolean success = db.addWorkout(username, exercise, weight, reps);

            if (success) {
                // Add to workout list in UI
                String workoutEntry = String.format("%s - %.1f kg x %d reps", exercise, weight, reps);
                userView.getWorkoutListModel().addElement(workoutEntry);
                userView.clearWorkoutFields();
                showSuccessMessage("Workout added successfully!");

                // Scroll to the bottom to show new workout
                int lastIndex = userView.getWorkoutListModel().size() - 1;
                if (lastIndex >= 0) {
                    userView.getWorkoutList().ensureIndexIsVisible(lastIndex);
                }
            } else {
                showErrorMessage("Failed to save workout to database");
            }

        } catch (NumberFormatException e) {
            showErrorMessage("Please enter valid numbers for weight and reps");
        } catch (SQLException e) {
            handleUnexpectedError("Database error saving workout", e);
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        } catch (Exception e) {
            handleUnexpectedError("Error adding workout", e);
        }
    }

    private void validateWorkoutInput(String exercise, String weight, String reps) {
        if (exercise.isEmpty()) {
            throw new IllegalArgumentException("Exercise cannot be empty");
        }
        if (weight.isEmpty()) {
            throw new IllegalArgumentException("Weight cannot be empty");
        }
        if (reps.isEmpty()) {
            throw new IllegalArgumentException("Reps cannot be empty");
        }
    }

    private void updateWeight() {
        String weightStr = userView.getBodyWeightField().getText().trim();

        try {
            // Validate input
            if (weightStr.isEmpty()) {
                throw new IllegalArgumentException("Please enter your weight");
            }

            // Parse and validate weight
            double weight = Double.parseDouble(weightStr);
            if (weight <= 0) {
                throw new IllegalArgumentException("Weight must be a positive number");
            }

            // Update weight in database
            boolean success = db.updateUserWeight(username, weight);

            if (success) {
                userView.clearWeightField();
                showSuccessMessage("Weight updated to: " + weight + " kg");

                // Update welcome message with new weight
                userView.setWelcomeMessage(username + " (" + weight + " kg)");
            } else {
                showErrorMessage("Failed to update weight in database");
            }

        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid weight");
        } catch (SQLException e) {
            handleUnexpectedError("Database error updating weight", e);
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        } catch (Exception e) {
            handleUnexpectedError("Error updating weight", e);
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                userView,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (userFrame != null) {
                userFrame.dispose();
            }
        }
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(
                userView,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                userView,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
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

    private void handleUnexpectedError(String message, Exception e) {
        JOptionPane.showMessageDialog(
                userView,
                message + ": " + e.getMessage(),
                "System Error",
                JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }
}