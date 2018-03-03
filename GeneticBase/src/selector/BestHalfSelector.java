package selector;

import population.Entity;
import population.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BestHalfSelector<E extends Entity> implements Selector<E> {
    @Override
    public List<E> applySelection(Random random, Population<E> population) {
        population.sortOnFitness();

        List<E> result = new ArrayList<>();
        int half = population.size() / 2;
        for (int i = 0; i < half; i++) {
            result.add(population.getEntities().get(i));
        }

        // todo remove debug
//        double maxFitness = Double.NEGATIVE_INFINITY;
//        for (E entity : population.getEntities()) {
//            maxFitness = Math.max(maxFitness, entity._getFitness());
//        }
//
//        if (Math.abs(maxFitness - result.get(0)._getFitness()) > 0.001) {
//            System.err.println("Best entity not in result");
//        }

        return result;
    }
}
