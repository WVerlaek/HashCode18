package postprocessing.genetic;

import reproduction.CrossOverReproducer;
import solver.Solution;

import java.util.Random;

class SolutionCrossOverReproducer<S extends Solution> extends CrossOverReproducer<SolutionEntity<S>, MutableSolutionEntity<S>> {

    private SolutionCrossOver<S> crossOver;

    SolutionCrossOverReproducer(SolutionCrossOver<S> crossOver) {
        this.crossOver = crossOver;
    }

    @Override
    final protected MutableSolutionEntity<S> crossOver(Random random, SolutionEntity<S> mom, SolutionEntity<S> dad) {
        return new MutableSolutionEntity<>(crossOver.crossOver(random, mom.getSolution(), dad.getSolution()));
    }

    @Override
    final protected SolutionEntity<S> mutate(Random random, MutableSolutionEntity<S> child) {
        return new SolutionEntity<>(crossOver.mutate(random, child.getSolution()));
    }

}
