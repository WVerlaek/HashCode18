package ui;

import algorithm.GeneticAlgorithm;

import javax.swing.*;
import java.awt.*;

public class OldAlgorithmFrame extends JFrame {

    public static OldAlgorithmFrame create(GeneticAlgorithm algorithm) {
        OldAlgorithmFrame frame = new OldAlgorithmFrame();

        frame.add(new ProgressGraph(algorithm));

        frame.setPreferredSize(new Dimension(900, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }
}
