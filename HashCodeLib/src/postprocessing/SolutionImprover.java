package postprocessing;

import postprocessing.SolutionCollector;
import solver.Solution;
import solver.SolutionScorer;

import java.util.function.Function;

public abstract class SolutionImprover<S extends Solution> {

    protected S solution;

    public SolutionImprover(S solution) {
        this.solution = solution;
    }

    public final void improve(Function<S, SolutionScorer<S>> scoreSupplier) {
        improve(new SolutionCollector<>(scoreSupplier));
    }

    protected abstract void improve(SolutionCollector<S> collector);
}
