package postprocessing.genetic;

import population.MutableEntity;
import solver.Solution;

/* package */ class MutableSolutionEntity<S extends Solution> extends SolutionEntity<S> implements MutableEntity {
    public MutableSolutionEntity(S solution) {
        super(solution);
    }
}
