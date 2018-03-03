package selector;

import population.Entity;
import population.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CubicSelector<E extends Entity> implements Selector<E> {
    @Override
    public List<E> applySelection(Random random, Population<E> population) {
        population.sortOnFitness();

        int n = population.size();
        int half = n / 2;

        List<E> result = new ArrayList<>();

        for (int i = 0; i < half; i++) {
            double f = i / (double) n;
            double rand = (Math.pow(random.nextDouble() * 2 - 1, 3) + 1) / 2d;
            boolean weakerDies = f <= rand;
            result.add(population.getEntities().get(weakerDies ? i : n - 1 - i));
        }
        return result;
    }
}
