import javax.swing.*;
import java.awt.*;

public class AdminDashboardView extends JPanel {
    DefaultListModel<String> userListModel = new DefaultListModel<>();
    JList<String> userList = new JList<>(userListModel);
    JTextField addUserField = new JTextField(10);
    JPasswordField addUserPass = new JPasswordField(10);
    JTextField addUserWeight = new JTextField(5);
    JButton addUserButton = new JButton("Add User");
    JButton deleteUserButton = new JButton("Delete User");
    JButton logoutButton = new JButton("Logout");

    public AdminDashboardView() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Username:"));
        topPanel.add(addUserField);
        topPanel.add(new JLabel("Password:"));
        topPanel.add(addUserPass);
        topPanel.add(new JLabel("Weight(kg):"));
        topPanel.add(addUserWeight);
        topPanel.add(addUserButton);
        topPanel.add(deleteUserButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(userList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Accessor methods as needed
}
