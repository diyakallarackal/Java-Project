import java.util.ArrayList;
import java.util.List;

public class User extends Account {
    private double bodyWeight;
    private List<WorkoutSession> workoutSessions = new ArrayList<>();

    public User(String username, String password, double bodyWeight) {
        super(username, password, "User");
        this.bodyWeight = bodyWeight;
    }

    public double getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public List<WorkoutSession> getWorkoutSessions() {
        return workoutSessions;
    }

    public void addWorkoutSession(WorkoutSession session) {
        workoutSessions.add(session);
    }
}
