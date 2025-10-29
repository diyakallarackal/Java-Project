package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JComboBox<String> roleCombo;
    private final JButton loginButton;
    private final JLabel messageLabel;

    public LoginView() {
        // Using GridBagLayout for better control
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize components
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        roleCombo = new JComboBox<>(new String[]{"Admin", "User"});
        loginButton = new JButton("Login");
        messageLabel = new JLabel(" ", SwingConstants.CENTER);

        // Style components
        messageLabel.setForeground(Color.RED);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);

        // Add components with proper layout
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        add(roleCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(loginButton, gbc);

        gbc.gridy = 4;
        add(messageLabel, gbc);

        // Set preferred size for better UI
        setPreferredSize(new Dimension(300, 200));
    }

    // Getters with proper encapsulation
    public JTextField getUsernameField() { return usernameField; }
    public JPasswordField getPasswordField() { return passwordField; }
    public JComboBox<String> getRoleCombo() { return roleCombo; }
    public JButton getLoginButton() { return loginButton; }
    public JLabel getMessageLabel() { return messageLabel; }

    // Utility method to clear fields
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText(" ");
    }
}
