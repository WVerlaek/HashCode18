package postprocessing.genetic;

import reproduction.CrossOverReproducer;
import solver.Solution;

import java.util.Random;

public abstract class SolutionCrossOver<S extends Solution> {

    protected abstract S crossOver(Random random, S mom, S dad);

    /** Optional. You can return solutionToMutate if you do not want to mutate.
     * It is safe to mutate the original solution. */
    protected S mutate(Random random, S solutionToMutate) {
        return generateNewMutatedSolution(random, solutionToMutate);
    }

    /** Do not modify the original solution! */
    protected abstract S generateNewMutatedSolution(Random random, S solutionToMutateFromDO_NOT_MODIFY);
}
