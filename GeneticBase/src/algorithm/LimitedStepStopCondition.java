package algorithm;

import population.Population;

public class LimitedStepStopCondition implements StopCondition {
    private int totalNrSteps;

    public LimitedStepStopCondition(int totalNrSteps) {
        this.totalNrSteps = totalNrSteps;
    }

    @Override
    public boolean shouldStop(long totalNrSteps, Population population) {
        return totalNrSteps >= this.totalNrSteps;
    }
}
