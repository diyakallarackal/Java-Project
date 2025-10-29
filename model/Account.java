package model;

public abstract class Account {
    private String username;
    private String password;
    private String role;

    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Proper encapsulation with getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Protected setters for inheritance
    protected void setPassword(String password) { this.password = password; }
    protected void setRole(String role) { this.role = role; }
}