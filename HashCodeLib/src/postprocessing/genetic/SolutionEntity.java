package postprocessing.genetic;

import population.Entity;
import solver.Solution;

/* package */ class SolutionEntity<S extends Solution> extends Entity {
    private S solution;

    public SolutionEntity(S solution) {
        super(0);
        this.solution = solution;
    }

    public long getScore() {
        return solution.score;
    }

    public S getSolution() {
        return solution;
    }
}
