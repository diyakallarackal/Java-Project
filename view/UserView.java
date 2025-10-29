package view;

import javax.swing.*;
import java.awt.*;

public class UserView extends JPanel {
    private final JTextField exerciseField;
    private final JTextField weightField;
    private final JTextField repsField;
    private final JButton addWorkoutButton;
    private final JTextField bodyWeightField;
    private final JButton updateWeightButton;
    private final JButton logoutButton;
    private final DefaultListModel<String> workoutListModel;
    private final JList<String> workoutList;
    private final JLabel welcomeLabel;

    public UserView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize components
        exerciseField = new JTextField(15);
        weightField = new JTextField(5);
        repsField = new JTextField(5);
        addWorkoutButton = new JButton("Add Workout");
        bodyWeightField = new JTextField(5);
        updateWeightButton = new JButton("Update Weight");
        logoutButton = new JButton("Logout");
        workoutListModel = new DefaultListModel<>();
        workoutList = new JList<>(workoutListModel);
        welcomeLabel = new JLabel("Welcome! ", SwingConstants.CENTER);

        // Style components
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        workoutList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create workout input panel
        JPanel workoutPanel = createWorkoutPanel();
        JPanel weightPanel = createWeightPanel();
        JPanel listPanel = createListPanel();

        // Layout organization
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        inputPanel.add(workoutPanel);
        inputPanel.add(weightPanel);
        topPanel.add(inputPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(listPanel, BorderLayout.CENTER);
        add(createLogoutPanel(), BorderLayout.SOUTH);
    }

    private JPanel createWorkoutPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Add Workout"));
        panel.add(new JLabel("Exercise:"));
        panel.add(exerciseField);
        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);
        panel.add(new JLabel("Reps:"));
        panel.add(repsField);
        panel.add(addWorkoutButton);
        return panel;
    }

    private JPanel createWeightPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Update Body Weight"));
        panel.add(new JLabel("Body Weight (kg):"));
        panel.add(bodyWeightField);
        panel.add(updateWeightButton);
        return panel;
    }

    private JPanel createListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Workout History"));
        panel.add(new JScrollPane(workoutList), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLogoutPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(logoutButton);
        return panel;
    }

    // Getters
    public JTextField getExerciseField() { return exerciseField; }
    public JTextField getWeightField() { return weightField; }
    public JTextField getRepsField() { return repsField; }
    public JButton getAddWorkoutButton() { return addWorkoutButton; }
    public JTextField getBodyWeightField() { return bodyWeightField; }
    public JButton getUpdateWeightButton() { return updateWeightButton; }
    public JButton getLogoutButton() { return logoutButton; }
    public DefaultListModel<String> getWorkoutListModel() { return workoutListModel; }
    public JList<String> getWorkoutList() { return workoutList; }
    public JLabel getWelcomeLabel() { return welcomeLabel; }

    // Utility methods
    public void setWelcomeMessage(String username) {
        welcomeLabel.setText("Welcome, " + username + "!");
    }

    public void clearWorkoutFields() {
        exerciseField.setText("");
        weightField.setText("");
        repsField.setText("");
    }

    public void clearWeightField() {
        bodyWeightField.setText("");
    }
}
