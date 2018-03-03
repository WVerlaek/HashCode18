package population;

import population.Entity;
import util.DoubleCallback;
import util.Threads;

@FunctionalInterface
public interface Simulator<E extends Entity> {

    default void simulateAsync(E entity, DoubleCallback fitness) {
        Threads.submit(() -> {
            double fit = runSimulationAndComputeFitnessSync(entity);
            fitness.accept(fit);
        });
    }

    default double simulateSync(E entity) {
        return runSimulationAndComputeFitnessSync(entity);
    }

    double runSimulationAndComputeFitnessSync(E entity);
}
