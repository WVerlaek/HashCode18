package algorithm;

import population.Population;

import java.util.List;

public class LoggerStepListener implements StepListener {
    @Override
    public void onFitnessEvaluated(Population population) {
        System.out.println("Fitness evaluated.");
    }

    @Override
    public void onEntitiesSelected(Population population, List selected) {
        System.out.println("Entities selected.");
    }

    @Override
    public void onOffspringGenerated(List parents, List children) {
        System.out.println("Offspring generated.");
    }

    @Override
    public void onStepFinished(long stepNr, Population newPopulation) {
        System.out.println("Step finished: " + stepNr);
    }
}
