package view;

import javax.swing.*;
import java.awt.*;

public class AdminView extends JPanel {
    private final DefaultListModel<String> userListModel;
    private final JList<String> userList;
    private final JButton refreshButton;
    private final JButton viewStatsButton;
    private final JButton logoutButton;
    private final JLabel statsLabel;

    public AdminView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize components
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        refreshButton = new JButton("Refresh Users");
        viewStatsButton = new JButton("View User Stats");
        logoutButton = new JButton("Logout");
        statsLabel = new JLabel("Statistics will appear here", SwingConstants.CENTER);

        // Style components
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        statsLabel.setBorder(BorderFactory.createTitledBorder("Workout Statistics"));
        statsLabel.setVerticalAlignment(SwingConstants.TOP);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshButton);
        buttonPanel.add(viewStatsButton);
        buttonPanel.add(logoutButton);

        // Create main panel with proper layout
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Users"));
        centerPanel.add(new JScrollPane(userList), BorderLayout.CENTER);

        // Add components to main panel
        add(buttonPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(statsLabel, BorderLayout.SOUTH);
    }

    // Getters
    public DefaultListModel<String> getUserListModel() { return userListModel; }
    public JList<String> getUserList() { return userList; }
    public JButton getRefreshButton() { return refreshButton; }
    public JButton getViewStatsButton() { return viewStatsButton; }
    public JButton getLogoutButton() { return logoutButton; }
    public JLabel getStatsLabel() { return statsLabel; }

    // Utility method to update stats
    public void updateStats(String statsText) {
        statsLabel.setText("<html>" + statsText + "</html>");
    }
}
