package ui;

import population.Entity;
import population.Simulator;
import render.EntityRenderer;

import javax.swing.*;
import java.awt.*;

public class EntityPanel extends JPanel {

    private Entity entity;
    private EntityRenderer renderer;

    public void setEntityRenderer(EntityRenderer renderer) {
        this.renderer = renderer;
        repaint();
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = ((Graphics2D) g);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (renderer != null && entity != null) {
            renderer.paintEntity(g2, entity, getWidth(), getHeight());
        }
    }
}
