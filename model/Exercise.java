public class Exercise {
    private String name;
    private double weight;
    private int reps;

    public Exercise(String name, double weight, int reps) {
        this.name = name;
        this.weight = weight;
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }
}
