package render;

import population.Entity;
import population.Population;

public abstract class StepRenderer<E extends Entity> {

    public abstract void draw(Population<E> population);

}
