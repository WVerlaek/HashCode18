package reproduction;

import population.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class SingleReproducer<E extends Entity> implements Reproducer<E> {

    @Override
    public List<E> reproduce(Random random, List<E> parents) {
        return parents.stream()
                .map(parent -> mutate(random, parent))
                .collect(Collectors.toList());
    }

    protected abstract E mutate(Random random, E parent);
}
