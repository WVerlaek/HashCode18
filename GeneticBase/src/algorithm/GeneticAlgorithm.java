package algorithm;

import population.Entity;
import population.Population;
import population.Simulator;
import util.Threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class GeneticAlgorithm<E extends Entity> {

    private AlgorithmOptions<E> options;

    private List<Population<E>> populationHistory;

    private List<StepListener<E>> listeners = new ArrayList<>();

    private long totalNumberOfSteps;
    private boolean isRunning = false;

    private Random random;

    /* package */ GeneticAlgorithm(AlgorithmOptions<E> options) {
        this.options = options;

        // initial population
        populationHistory = new ArrayList<>();
        populationHistory.add(options.initialPopulation);

        if (options.stepListener != null)
            listeners.add(options.stepListener);

        totalNumberOfSteps = 0L;

        random = new Random(options.seed);
    }

    public int getSeed() {
        return options.seed;
    }

    public void addListener(StepListener<E> listener) {
        listeners.add(listener);
    }

    public void removeListener(StepListener<E> listener) {
        listeners.remove(listener);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public RenderingOptions<E> getRenderingOptions() {
        return options.rendering;
    }

    public StepOptions<E> getDefaultStepOptions() {
        return new StepOptions<>();
    }

    /**
     * Runs the algorithm!
     */
    public void run(StopCondition<E> stopCondition) {
        if (isRunning) throw new IllegalStateException("Already running!");
        isRunning = true;
        Population<E> currentPopulation = populationHistory.get(populationHistory.size() - 1);

        StepOptions<E> stepOptions = getDefaultStepOptions();

        while (!stopCondition.shouldStop(totalNumberOfSteps, currentPopulation)) {
            runSingleStep(stepOptions);
            currentPopulation = populationHistory.get(populationHistory.size() - 1);
        }
        isRunning = false;
    }

    public void runSingleStep() {
        runSingleStep(getDefaultStepOptions());
    }

    public void runSingleStep(StepOptions<E> stepOptions) {
        Population<E> currentPopulation = populationHistory.get(populationHistory.size() - 1);

        Population<E> newPopulation = applyStep(currentPopulation);

        if (options.rememberPopulationHistory) {
            // add to list
            populationHistory.add(newPopulation);
        } else {
            // overwrite last
            populationHistory.set(0, newPopulation);
        }

        totalNumberOfSteps++;
    }

    /**
     * Single step in algorithm.
     * @param population Population to apply step from.
     * @return New population.
     */
    private Population<E> applyStep(Population<E> population) {
        // clear fitness
//        population.getEntities().forEach(E::_clearFitness);
        // don't clear fitness, entities are immutable!

        // compute fitness
        Simulator<E> simulator = options.simulator;
        if (options.simulateAsync) {
            List<E> entities = population.getEntities();
            CountDownLatch latch = new CountDownLatch(entities.size());
            for (E entity : entities) {
                if (!entity._hasComputedFitness()) {
                    simulator.simulateAsync(entity, fitness -> {
                        entity._setFitness(fitness);
                        latch.countDown();
                    });
                } else {
                    latch.countDown();
                }
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            for (E entity : population.getEntities()) {
                if (!entity._hasComputedFitness()) {
                    double fitness = simulator.simulateSync(entity);
                    entity._setFitness(fitness);
                }
            }
        }
        listeners.forEach(listener -> listener.onFitnessEvaluated(population));

        // selection
        List<E> survivors = options.selector.applySelection(random, population);
        listeners.forEach(listener -> listener.onEntitiesSelected(population, survivors));

        // reproduce
        List<E> children = options.reproducer.reproduce(random, survivors);
        listeners.forEach(listener -> listener.onOffspringGenerated(survivors, children));

        // new population
        List<E> newPopulationEntities = new ArrayList<>();
        newPopulationEntities.addAll(survivors);
        newPopulationEntities.addAll(children);

        Population<E> newPopulation = new Population<>(newPopulationEntities);
        listeners.forEach(listener -> listener.onStepFinished(totalNumberOfSteps, newPopulation));
        return newPopulation;
    }

    public void runAsync(StopCondition<E> stopCondition) {
        Threads.submit(() -> run(stopCondition));
    }
}
