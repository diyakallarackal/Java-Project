import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutSession {
    private Date date;
    private double bodyWeight;
    private List<Exercise> exercises = new ArrayList<>();

    public WorkoutSession(Date date, double bodyWeight) {
        this.date = date;
        this.bodyWeight = bodyWeight;
    }

    public Date getDate() {
        return date;
    }

    public double getBodyWeight() {
        return bodyWeight;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }
}
