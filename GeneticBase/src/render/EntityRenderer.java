package render;

import population.Entity;

import java.awt.*;

public interface EntityRenderer<E extends Entity> {

    void paintEntity(Graphics2D g, E entity, int width, int height);

    default Dimension getPreferredDimension() {
        return new Dimension(300, 300);
    }
}
