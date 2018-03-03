package postprocessing;

import io.BestSolutionTracker;
import solver.Solution;
import solver.SolutionScorer;

import java.util.function.Function;

public class SolutionCollector<S extends Solution> {

    protected long bestScore = -1;
    protected BestSolutionTracker bestSolutionTracker = new BestSolutionTracker();
    protected Function<S, SolutionScorer<S>> scoreSupplier;

    public SolutionCollector(Function<S, SolutionScorer<S>> scoreSupplier) {
        this.scoreSupplier = scoreSupplier;
    }

    /**
     * Calculates the score of the solution,
     * and calls the {@link BestSolutionTracker} to check
     * if this is the best solution found so far.
     */
    public void collect(S solution) {
        long score = scoreSupplier.apply(solution).getScore();
        solution.score = score;
        if (score > bestScore) {
            bestScore = score;

            bestSolutionTracker.checkIfBestSolution(solution, System.currentTimeMillis());
        }
    }
}
