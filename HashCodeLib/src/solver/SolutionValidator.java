package solver;

public abstract class SolutionValidator<T extends Solution> {

    public abstract boolean isValidSolution(T solution);
}
