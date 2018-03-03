package algorithm;

import population.Entity;
import render.EntityRenderer;

public class RenderingOptions<E extends Entity> {
    EntityRenderer<E> entityRenderer;
    int maxGraphHistory = Integer.MAX_VALUE;

    public EntityRenderer<E> getEntityRenderer() {
        return entityRenderer;
    }
    public int getMaxGraphHistory() {
        return maxGraphHistory;
    }
}
