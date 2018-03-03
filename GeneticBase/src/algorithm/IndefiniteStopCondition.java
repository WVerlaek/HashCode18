package algorithm;

import population.Population;

public class IndefiniteStopCondition implements StopCondition {
    private boolean shouldStop = false;
    @Override
    public boolean shouldStop(long totalNrSteps, Population population) {
        return shouldStop;
    }

    public void stop() {
        shouldStop = true;
    }

    public void reset() {
        shouldStop = false;
    }
}
