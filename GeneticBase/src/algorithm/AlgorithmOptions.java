package algorithm;

import population.Simulator;
import population.Entity;
import population.Population;
import reproduction.Reproducer;
import selector.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* package */ class AlgorithmOptions<E extends Entity> {
    RenderingOptions<E> rendering = new RenderingOptions<>();

    Population<E> initialPopulation = new Population<>(new ArrayList<>());
    boolean rememberPopulationHistory = false;
    StepListener<E> stepListener;
    // compute fitness async?
    Simulator<E> simulator = new Simulator<E>() {
        private boolean showWarning = true;
        @Override
        public double runSimulationAndComputeFitnessSync(E entity) {
            if (showWarning) {
                System.err.println("Using default simulator.");
                showWarning = false;
            }
            return 0d;
        }
    };
    boolean simulateAsync = true;
    Reproducer<E> reproducer = new Reproducer<E>() {
        private boolean showWarning = true;
        @Override
        public List<E> reproduce(Random random, List<E> parents) {
            if (showWarning) {
                System.err.println("Using default reproducer.");
                showWarning = false;
            }
            return parents;
        }
    };
    Selector<E> selector = new Selector<E>() {
        private boolean showWarning = true;
        @Override
        public List<E> applySelection(Random random, Population<E> population) {
            if (showWarning) {
                System.err.println("Using default selector.");
                showWarning = false;
            }
            return population.getEntities();
        }
    };
    int seed = 0;
}
