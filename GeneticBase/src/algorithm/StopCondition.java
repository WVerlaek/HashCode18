package algorithm;

import population.Entity;
import population.Population;

@FunctionalInterface
public interface StopCondition<E extends Entity> {

    boolean shouldStop(long totalNrSteps, Population<E> population);
}
