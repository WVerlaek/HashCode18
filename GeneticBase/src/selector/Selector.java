package selector;

import population.Entity;
import population.Population;

import java.util.List;
import java.util.Random;

public interface Selector<E extends Entity> {

    List<E> applySelection(Random random, Population<E> population);
}
