package postprocessing.genetic;

import population.Population;
import solver.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class InitialPopulationFactory<S extends Solution> {

    protected int n;
    protected Random random;
    protected SolutionCrossOver<S> crossOver;

    public InitialPopulationFactory(int n, int seed, SolutionCrossOver<S> crossOver) {
        this.n = n;
        this.random = new Random(seed);
        this.crossOver = crossOver;
    }

    public Population<SolutionEntity<S>> get(S mutateFrom) {
        List<SolutionEntity<S>> result = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            S generated = crossOver.generateNewMutatedSolution(random, mutateFrom);
            result.add(new SolutionEntity<>(generated));
        }

        return new Population<>(result);
    }
}
