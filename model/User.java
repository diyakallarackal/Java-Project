package model;

public class User extends Account {
    private double bodyWeight;

    public User(String username, String password, double bodyWeight) {
        super(username, password, "User"); // Calling superclass constructor
        this.bodyWeight = bodyWeight;
    }

    public double getBodyWeight() { return bodyWeight; }
    public void setBodyWeight(double bodyWeight) {
        if (bodyWeight > 0) { // Basic validation
            this.bodyWeight = bodyWeight;
        }
    }

    @Override
    public String toString() {
        return String.format("User: %s, Weight: %.1f kg", getUsername(), bodyWeight);
    }
}