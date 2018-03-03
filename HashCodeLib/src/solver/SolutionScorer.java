package solver;

public abstract class SolutionScorer<T extends Solution> {

    protected T solution;

    public SolutionScorer(T solution) {
        this.solution = solution;
    }

    public abstract long getScore();

}
