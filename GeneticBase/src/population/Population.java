package population;

import java.util.Collections;
import java.util.List;

public class Population<E extends Entity> {

    private List<E> entities;
    private boolean sorted = false;

    public Population(List<E> entities) {
        this.entities = entities;
    }

    public List<E> getEntities() {
        return entities;
    }

    public double getMaxFitness() {
        double max = Double.NEGATIVE_INFINITY;
        for (E entity : getEntities()) {
            if (entity._hasComputedFitness()) {
                max = Math.max(max, entity._getFitness());
            } else {
                throw new IllegalStateException("Fitness has not been computed yet for entities.");
            }
        }
        return max;
    }

    public double getMinFitness() {
        double min = Double.POSITIVE_INFINITY;
        for (E entity : getEntities()) {
            if (entity._hasComputedFitness()) {
                min = Math.min(min, entity._getFitness());
            } else {
                throw new IllegalStateException("Fitness has not been computed yet for entities.");
            }
        }
        return min;
    }

    public double getAvgFitness() {
        double avg = 0d;
        List<E> entities = getEntities();
        for (E entity : entities) {
            if (entity._hasComputedFitness()) {
                avg += entity._getFitness();
            } else {
                throw new IllegalStateException("Fitness has not been computed yet for entities.");
            }
        }
        return avg / entities.size();
    }

    public int size() {
        return entities.size();
    }

    public void sortOnFitness() {
        if (!sorted) {
            Collections.sort(entities);
            sorted = true;
        }
    }
}
