package selector;

import population.Entity;
import population.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinearSelector<E extends Entity> implements Selector<E> {

    @Override
    public List<E> applySelection(Random random, Population<E> population) {
        population.sortOnFitness();

        int total = population.size();
        int n = total / 2;
        List<E> selected = new ArrayList<>(n);

        boolean[] added = new boolean[total];

        while (selected.size() < n) {
            int candidate = random.nextInt(total);

            if (added[candidate]) continue;

            double chance = 1d - (candidate / (double) total);
            if (chance >= random.nextDouble()) {
                selected.add(population.getEntities().get(candidate));
                added[candidate] = true;
            }
        }

        return selected;
    }
}
