package reproduction;

import population.Entity;

import java.util.List;
import java.util.Random;

public interface Reproducer<E> {
    List<E> reproduce(Random random, List<E> parents);
}
