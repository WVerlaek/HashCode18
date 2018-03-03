package ui;

import algorithm.GeneticAlgorithm;
import algorithm.IndefiniteStopCondition;
import algorithm.StepListener;
import population.Entity;
import population.Population;
import render.EntityRenderer;

import javax.swing.*;
import java.util.List;

public class AlgorithmFrame implements StepListener {
    private ProgressGraph progressGraph1;
    private JPanel contentPane;
    private JButton playButton;
    private JLabel stepsLabel;
    private JPanel entityPanel;
    private JButton step1Button;
    private JButton step10Button;

    private GeneticAlgorithm algorithm;

    private IndefiniteStopCondition stopCondition = new IndefiniteStopCondition();

    public AlgorithmFrame(GeneticAlgorithm algorithm) {
        this.algorithm = algorithm;

        playButton.addActionListener(e -> {
            if (algorithm.isRunning()) {
                // stop
                stopCondition.stop();
                playButton.setText("Play");
                step1Button.setEnabled(true);
                step10Button.setEnabled(true);
            } else {
                // start
                stopCondition.reset();
                algorithm.runAsync(stopCondition);
                playButton.setText("Stop");
                step1Button.setEnabled(false);
                step10Button.setEnabled(false);
            }
        });
        step1Button.addActionListener(e -> {
            algorithm.runSingleStep();
        });
        step10Button.addActionListener(e -> {
            for (int i = 0; i < 10; i++) {
                algorithm.runSingleStep();
            }
        });

        progressGraph1.setAlgorithm(algorithm);
        EntityPanel entityPanel1 = (EntityPanel) entityPanel;
        EntityRenderer renderer = algorithm.getRenderingOptions().getEntityRenderer();
        entityPanel1.setEntityRenderer(renderer);
        if (renderer != null) {
            entityPanel1.setMinimumSize(renderer.getPreferredDimension());
        }

        algorithm.addListener(this);
    }

    public void createUIComponents() {
        progressGraph1 = new ProgressGraph();
        entityPanel = new EntityPanel();
    }

    public static void createAndShow(GeneticAlgorithm algorithm) {
        JFrame frame = new JFrame("");
        AlgorithmFrame algorithmFrame = new AlgorithmFrame(algorithm);

        frame.setContentPane(algorithmFrame.contentPane);

//        frame.setPreferredSize(new Dimension(900, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void onFitnessEvaluated(Population population) {
        population.sortOnFitness();
        Entity best = population.getEntities().isEmpty()
                ? null
                : (Entity) population.getEntities().get(0);
        ((EntityPanel) entityPanel).setEntity(best);
    }

    @Override
    public void onEntitiesSelected(Population population, List selected) {

    }

    @Override
    public void onOffspringGenerated(List parents, List children) {

    }

    @Override
    public void onStepFinished(long stepNr, Population newPopulation) {
        stepsLabel.setText("Steps: " + stepNr);
    }
}
