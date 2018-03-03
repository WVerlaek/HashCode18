package ui;

import algorithm.GeneticAlgorithm;
import algorithm.StepListener;
import population.Population;
import util.CircularArrayList;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProgressGraph extends JPanel implements StepListener {

    private CircularArrayList<Double> averages;
    private CircularArrayList<Double> minimums;
    private CircularArrayList<Double> maximums;

    private double min = Double.POSITIVE_INFINITY;
    private double max = Double.NEGATIVE_INFINITY;

    // padding
    private int paddingLeft = 10;
    private int labelWidth = 40;
    private int paddingRight = 10;
    private int paddingTop = 10;
    private int paddingBottom = 10;

    private GeneticAlgorithm algorithm;

    private int maxHistory;

    public ProgressGraph(GeneticAlgorithm<?> algorithm) {
        setAlgorithm(algorithm);
    }

    public ProgressGraph() {

    }

    public void setAlgorithm(GeneticAlgorithm algorithm) {
        if (this.algorithm != null) {
            this.algorithm.removeListener(this);
        }
        this.algorithm = algorithm;
        this.maxHistory = algorithm.getRenderingOptions().getMaxGraphHistory();
        this.averages = new CircularArrayList<>(this.maxHistory);
        this.minimums = new CircularArrayList<>(this.maxHistory);
        this.maximums = new CircularArrayList<>(this.maxHistory);
        this.max = Double.NEGATIVE_INFINITY;
        this.min = Double.POSITIVE_INFINITY;
        algorithm.addListener(this);
    }

    public void addPopulation(Population population) {
        double avg = population.getAvgFitness();
        double min = population.getMinFitness();
        double max = population.getMaxFitness();

        SwingUtilities.invokeLater(() -> {
            averages.addOverwrite(avg);
            minimums.addOverwrite(min);
            maximums.addOverwrite(max);

            this.min = Math.min(min, this.min);
            this.max = Math.max(max, this.max);

            repaint();
        });
    }

    public int getTotalPopulations() {
        return averages.size();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = ((Graphics2D) g);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(paddingLeft + labelWidth, paddingTop, getWidth() - paddingLeft - labelWidth - paddingRight,
                getHeight() - paddingTop - paddingBottom);

        int total = getTotalPopulations();
        if (total <= 1) return;

        Color gridLineCol = new Color(180, 180, 180);

        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < total - 1; i++) {
            double avg = averages.get(i);
            double nextAvg = averages.get(i + 1);
            g.drawLine(
                    getX(i), getY(avg),
                    getX(i + 1), getY(nextAvg));
        }
        for (int i = 0; i < total - 1; i++) {
            double min = minimums.get(i);
            double nextMin = minimums.get(i + 1);
            g.drawLine(
                    getX(i), getY(min),
                    getX(i + 1), getY(nextMin));
        }
        for (int i = 0; i < total - 1; i++) {
            double max = maximums.get(i);
            double nextMax = maximums.get(i + 1);
            g.drawLine(
                    getX(i), getY(max),
                    getX(i + 1), getY(nextMax));
        }

        // labels
        g.setColor(Color.DARK_GRAY);
        g.drawString(fd(min), paddingLeft, getHeight() - paddingBottom);
        g.drawString(fd(max), paddingLeft, paddingTop);

        double range = max - min;
        double minOffset = range / 5;
        if (min < -minOffset && max > minOffset) {
            int y0 = getY(0d);
            g.drawString("0", paddingLeft, y0);
            g.setColor(gridLineCol);
            g.drawLine(paddingLeft + labelWidth, y0, getWidth() - paddingRight, y0);
        }
    }

    private int getX(int i) {
        int width = getWidth();
        int totalPaddingLeft = paddingLeft + labelWidth;
        return totalPaddingLeft + (int) ((width - totalPaddingLeft - paddingRight) * (i / (double) getTotalPopulations()));
    }

    private int getY(double val) {
        int height = getHeight();

        double offset = 1d - ((val - min) / (max - min));
        return paddingTop + (int) ((height - paddingTop - paddingBottom) * offset);
    }

    private DecimalFormat format = new DecimalFormat("0.##");
    private String fd(double d) {
        return format.format(d);
    }

    @Override
    public void onFitnessEvaluated(Population population) {
        addPopulation(population);
    }

    @Override
    public void onEntitiesSelected(Population population, List entities) {

    }

    @Override
    public void onOffspringGenerated(List parents, List children) {

    }

    @Override
    public void onStepFinished(long stepNr, Population newPopulation) {

    }
}
