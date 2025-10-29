package model;

public class WorkoutStats {
    private final String username;
    private final int totalWorkouts;
    private final double avgBodyWeight;

    public WorkoutStats(String username, int totalWorkouts, double avgBodyWeight) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
        this.totalWorkouts = Math.max(totalWorkouts, 0); // Ensure non-negative
        this.avgBodyWeight = Math.max(avgBodyWeight, 0); // Ensure non-negative
    }

    public String getUsername() { return username; }
    public int getTotalWorkouts() { return totalWorkouts; }
    public double getAvgBodyWeight() { return avgBodyWeight; }

    @Override
    public String toString() {
        return String.format("User: %s, Workouts: %d, Avg Weight: %.1f kg",
                username, totalWorkouts, avgBodyWeight);
    }
}
