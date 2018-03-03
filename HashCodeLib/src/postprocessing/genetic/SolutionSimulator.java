package postprocessing.genetic;

import solver.Solution;

/* package */ class SolutionSimulator<S extends Solution> implements population.Simulator<SolutionEntity<S>> {
    @Override
    public double runSimulationAndComputeFitnessSync(SolutionEntity<S> entity) {
        // TODO Compute score here?
        return entity.getScore();
    }
}
