import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FitTrackController {
    private JFrame frame;  //main panel
    private CardLayout cardLayout = new CardLayout();   //2 panels sides
    
    private JPanel mainPanel = new JPanel(cardLayout);

    private Map<String, Account> accounts = new HashMap<>();
    private User currentUser = null;

    private LoginView loginView;
    private UserDashboardView userDashboardView;
    private AdminDashboardView adminDashboardView;

    public FitTrackController() {
        frame = new JFrame("FitTrack MVC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        loginView = new LoginView();
        userDashboardView = new UserDashboardView();
        adminDashboardView = new AdminDashboardView();

        mainPanel.add(loginView, "Login");
        mainPanel.add(userDashboardView, "UserDashboard");
        mainPanel.add(adminDashboardView, "AdminDashboard");

        frame.add(mainPanel);

        initData();
        addListeners();
        frame.setVisible(true);

        showLogin();
    }

    private void initData() {
        accounts.put("admin", new Account("admin", "admin123", "Admin"));
        accounts.put("user1", new User("user1", "user123", 70.0));
    }

    private void addListeners() {
        loginView.loginButton.addActionListener(e -> handleLogin());
        adminDashboardView.addUserButton.addActionListener(e -> handleAddUser());
        adminDashboardView.deleteUserButton.addActionListener(e -> handleDeleteUser());
        userDashboardView.addWorkoutButton.addActionListener(e -> handleAddWorkout());
        userDashboardView.updateWeightButton.addActionListener(e -> handleUpdateWeight());
        userDashboardView.logoutButton.addActionListener(e -> showLogin());
        adminDashboardView.logoutButton.addActionListener(e -> showLogin());
    }

    private void handleLogin() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        String role = loginView.getRole();

        Account acc = accounts.get(username);
        if (acc != null && acc.getPassword().equals(password) && acc.getRole().equals(role)) {
            loginView.setMessage("");
            if (role.equals("Admin")) {
                showAdminDashboard();
            } else {
                currentUser = (User) acc;
                showUserDashboard();
            }
        } else {
            loginView.setMessage("Invalid credentials");
        }
    }

    private void showLogin() {
        currentUser = null;
        clearLoginFields();
        cardLayout.show(mainPanel, "Login");
    }

    private void clearLoginFields() {
        loginView.usernameField.setText("");
        loginView.passwordField.setText("");
        loginView.setMessage("");
    }

    private void showUserDashboard() {
        refreshUserWorkouts();
        userDashboardView.bodyWeightField.setText(String.valueOf(currentUser.getBodyWeight()));
        cardLayout.show(mainPanel, "UserDashboard");
    }

    private void showAdminDashboard() {
        refreshUserList();
        cardLayout.show(mainPanel, "AdminDashboard");
    }

    private void refreshUserWorkouts() {
        DefaultListModel<String> model = userDashboardView.workoutListModel;
        model.clear();
        for (WorkoutSession ws : currentUser.getWorkoutSessions()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Date: ").append(ws.getDate()).append(", BodyWeight: ").append(ws.getBodyWeight())
                    .append("kg, Exercises: ");
            for (Exercise ex : ws.getExercises()) {
                sb.append(ex.getName())
                        .append("(").append(ex.getWeight()).append("kg x ").append(ex.getReps()).append("), ");
            }
            model.addElement(sb.toString());
        }
    }

    private void refreshUserList() {
        DefaultListModel<String> model = adminDashboardView.userListModel;
        model.clear();
        for (Account acc : accounts.values()) {
            if (acc.getRole().equals("User")) {
                model.addElement(acc.getUsername());
            }
        }
    }

    private void handleAddUser() {
        String username = adminDashboardView.addUserField.getText().trim();
        String password = new String(adminDashboardView.addUserPass.getPassword());
        String weightText = adminDashboardView.addUserWeight.getText().trim();

        try {
            double weight = Double.parseDouble(weightText);
            if (!username.isEmpty() && !password.isEmpty() && weight > 0) {
                if (!accounts.containsKey(username)) {
                    accounts.put(username, new User(username, password, weight));
                    refreshUserList();
                    adminDashboardView.addUserField.setText("");
                    adminDashboardView.addUserPass.setText("");
                    adminDashboardView.addUserWeight.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "User already exists.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Enter valid data.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Weight must be a valid number.");
        }
    }

    private void handleDeleteUser() {
        String selected = adminDashboardView.userList.getSelectedValue();
        if (selected != null) {
            accounts.remove(selected);
            refreshUserList();
        }
    }

    private void handleAddWorkout() {
        String exercise = userDashboardView.exerciseNameField.getText().trim();
        String weightStr = userDashboardView.weightField.getText().trim();
        String repsStr = userDashboardView.repsField.getText().trim();

        if (exercise.isEmpty() || weightStr.isEmpty() || repsStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Enter all workout details.");
            return;
        }
        try {
            double weight = Double.parseDouble(weightStr);
            int reps = Integer.parseInt(repsStr);
            if (weight <= 0 || reps <= 0) {
                JOptionPane.showMessageDialog(frame, "Weight and reps must be positive.");
                return;
            }
            WorkoutSession session = new WorkoutSession(new Date(), currentUser.getBodyWeight());
            session.addExercise(new Exercise(exercise, weight, reps));
            currentUser.addWorkoutSession(session);
            refreshUserWorkouts();

            userDashboardView.exerciseNameField.setText("");
            userDashboardView.weightField.setText("");
            userDashboardView.repsField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Weight and reps must be numeric.");
        }
    }

    private void handleUpdateWeight() {
        try {
            double newWeight = Double.parseDouble(userDashboardView.bodyWeightField.getText().trim());
            if (newWeight <= 0) {
                JOptionPane.showMessageDialog(frame, "Weight must be positive.");
                return;
            }
            currentUser.setBodyWeight(newWeight);
            JOptionPane.showMessageDialog(frame, "Body weight updated.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Enter a valid weight.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FitTrackController());
    }
}

