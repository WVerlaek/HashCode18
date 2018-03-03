package algorithm;

import population.Simulator;
import population.Entity;
import population.Population;
import render.EntityRenderer;
import reproduction.Reproducer;
import selector.Selector;

public class GeneticAlgorithmBuilder<E extends Entity> {

    private AlgorithmOptions<E> algorithmOptions;

    public GeneticAlgorithmBuilder() {
        algorithmOptions = new AlgorithmOptions<>();
    }

    public GeneticAlgorithmBuilder<E> setInitialPopulation(Population<E> population) {
        algorithmOptions.initialPopulation = population;
        return this;
    }

    public GeneticAlgorithmBuilder<E> rememberPopulationHistory(boolean remember) {
        algorithmOptions.rememberPopulationHistory = remember;
        return this;
    }

    public GeneticAlgorithmBuilder<E> setStepListener(StepListener<E> stepListener) {
        algorithmOptions.stepListener = stepListener;
        return this;
    }

    public GeneticAlgorithmBuilder<E> setReproducer(Reproducer<E> reproducer) {
        algorithmOptions.reproducer = reproducer;
        return this;
    }

    public GeneticAlgorithmBuilder<E> setSelector(Selector<E> selector) {
        algorithmOptions.selector = selector;
        return this;
    }

    public GeneticAlgorithmBuilder<E> setSimulator(Simulator<E> simulator) {
        algorithmOptions.simulator = simulator;
        return this;
    }

    public GeneticAlgorithmBuilder<E> setSimulateAsync(boolean async) {
        algorithmOptions.simulateAsync = async;
        return this;
    }

    public GeneticAlgorithmBuilder<E> setSeed(int seed) {
        algorithmOptions.seed = seed;
        return this;
    }

    public GeneticAlgorithmBuilder<E> setEntityRenderer(EntityRenderer<E> renderer) {
        algorithmOptions.rendering.entityRenderer = renderer;
        return this;
    }

    public GeneticAlgorithmBuilder<E> setMaxGraphHistory(int max) {
        algorithmOptions.rendering.maxGraphHistory = max;
        return this;
    }

    public GeneticAlgorithm<E> build() {
        return new GeneticAlgorithm<>(algorithmOptions);
    }

}
