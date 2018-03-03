package reproduction;

import com.sun.istack.internal.Nullable;
import population.Entity;
import population.MutableEntity;

import java.util.*;

public abstract class CrossOverReproducer<E extends Entity, M extends MutableEntity> implements Reproducer<E> {
    @Override
    public List<E> reproduce(Random random, List<E> parents) {
        int n = parents.size();

        List<E> children = new ArrayList<>();

        while (children.size() < n) {
            Collections.shuffle(parents);
            for (int i = 0; i < n - 1; i += 2) {
                E mom = parents.get(i);
                E dad = parents.get(i + 1);

                // cross over
                M child = crossOver(random, mom, dad);
                if (child != null) {

                    // mutate
                    E mutatedChild = mutate(random, child);
                    if (mutatedChild != null) {
                        children.add(mutatedChild);
                    }
                }
            }
        }

        return children;
    }

    @Nullable
    protected abstract M crossOver(Random random, E mom, E dad);

    @Nullable
    protected abstract E mutate(Random random, M child);
}
