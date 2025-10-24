import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    JTextField usernameField = new JTextField(15);
    JPasswordField passwordField = new JPasswordField(15);
    JComboBox<String> roleCombo = new JComboBox<>(new String[] { "Admin", "User" });
    JButton loginButton = new JButton("Login");
    JLabel messageLabel = new JLabel();

    public LoginView() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        add(roleCombo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        messageLabel.setForeground(Color.RED);
        add(messageLabel, gbc);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getRole() {
        return (String) roleCombo.getSelectedItem();
    }

    public void setMessage(String msg) {
        messageLabel.setText(msg);
    }
}
