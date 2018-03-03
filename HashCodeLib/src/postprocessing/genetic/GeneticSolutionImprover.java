package postprocessing.genetic;

import algorithm.GeneticAlgorithm;
import algorithm.GeneticAlgorithmBuilder;
import algorithm.StepListener;
import population.Population;
import postprocessing.SolutionCollector;
import postprocessing.SolutionImprover;
import selector.CubicSelector;
import solver.Solution;
import ui.AlgorithmFrame;
import util.ScoreFormat;

import java.util.List;

public class GeneticSolutionImprover<S extends Solution> extends SolutionImprover<S> {

    protected SolutionCrossOver<S> crossOver;
    protected int n;
    protected int maxGenerations;

    public GeneticSolutionImprover(S solution, SolutionCrossOver<S> crossOver, int populationSize, int maxGenerations) {
        super(solution);
        this.crossOver = crossOver;
        this.n = populationSize;
        this.maxGenerations = maxGenerations;
    }

    @Override
    final public void improve(SolutionCollector<S> collector) {
        int seed = 123;
        InitialPopulationFactory<S> populationFactory = new InitialPopulationFactory<>(n, seed, crossOver);

        GeneticAlgorithm<SolutionEntity<S>> algorithm = new GeneticAlgorithmBuilder<SolutionEntity<S>>()
                .setInitialPopulation(populationFactory.get(solution))
                .rememberPopulationHistory(false)
                .setSelector(new CubicSelector<>())
                .setSeed(seed)
                .setMaxGraphHistory(50000)
                .setReproducer(new SolutionCrossOverReproducer<>(crossOver))
                .setSimulator(new SolutionSimulator<>())
                .setSimulateAsync(false)
                .setStepListener(new StepListener<SolutionEntity<S>>() {
                    long step = 0;
                    @Override
                    public void onFitnessEvaluated(Population<SolutionEntity<S>> population) {
                        // newPopulation is sorted
                        population.sortOnFitness();
                        SolutionEntity<S> best = population.getEntities().get(0);
                        collector.collect(best.getSolution());

                        if (step % 50 == 0) {
                            System.out.println("generation " + step + ", best score=" + ScoreFormat.format(best.getScore()));
                        }
                        step++;
                    }

                    @Override
                    public void onEntitiesSelected(Population<SolutionEntity<S>> population, List<SolutionEntity<S>> selected) {
                    }

                    @Override
                    public void onOffspringGenerated(List<SolutionEntity<S>> parents, List<SolutionEntity<S>> children) {
                    }

                    @Override
                    public void onStepFinished(long stepNr, Population<SolutionEntity<S>> newPopulation) {
                    }
                })
                .build();

        // run for maxGenerations number of steps
        AlgorithmFrame.createAndShow(algorithm);
//        algorithm.run((totalNrSteps, population) -> totalNrSteps > maxGenerations);
    }
}
