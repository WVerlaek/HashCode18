package algorithm;

import population.Entity;
import population.Population;

import java.util.List;

public interface StepListener<E extends Entity> {

    void onFitnessEvaluated(Population<E> population);
    void onEntitiesSelected(Population<E> population, List<E> selected);
    void onOffspringGenerated(List<E> parents, List<E> children);
    void onStepFinished(long stepNr, Population<E> newPopulation);
}
