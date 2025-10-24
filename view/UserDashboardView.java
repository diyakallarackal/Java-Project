import javax.swing.*;
import java.awt.*;

public class UserDashboardView extends JPanel {
    JTextField exerciseNameField = new JTextField(10);
    JTextField weightField = new JTextField(5);
    JTextField repsField = new JTextField(5);
    JTextField bodyWeightField = new JTextField(5);
    JButton addWorkoutButton = new JButton("Add Workout");
    JButton updateWeightButton = new JButton("Update Weight");
    DefaultListModel<String> workoutListModel = new DefaultListModel<>();
    JList<String> workoutList = new JList<>(workoutListModel);
    JButton logoutButton = new JButton("Logout");

    public UserDashboardView() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Exercise:"));
        inputPanel.add(exerciseNameField);
        inputPanel.add(new JLabel("Weight (kg):"));
        inputPanel.add(weightField);
        inputPanel.add(new JLabel("Reps:"));
        inputPanel.add(repsField);
        inputPanel.add(addWorkoutButton);

        JPanel weightPanel = new JPanel();
        weightPanel.add(new JLabel("Body Weight (kg):"));
        weightPanel.add(bodyWeightField);
        weightPanel.add(updateWeightButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(weightPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(workoutList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Accessor methods as needed
}
