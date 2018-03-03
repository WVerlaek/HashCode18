package postprocessing;

import base.InputFiles;
import io.InputFile;
import model.Ride;
import output.SelfDrivingSolution;
import output.SolutionScorer;
import postprocessing.genetic.GeneticSolutionImprover;
import simulationsolver.BasicSimSolver;

import java.util.List;

public class GeneticDrivingImprover extends GeneticSolutionImprover<SelfDrivingSolution> {

    public GeneticDrivingImprover(SelfDrivingSolution solution, Ride[] allRides, int populationSize, int maxGenerations) {
        super(solution, new CrossOver(allRides), populationSize, maxGenerations);
    }

    public static void main(String[] args) {
        String file = InputFiles.D_METROPOLIS;
        InputFile input = new InputFile(file);

        BasicSimSolver solver = new BasicSimSolver(input, false);
        SelfDrivingSolution solution = solver.getSolution();

        int population = 200;
        int generations = 10000;
        GeneticDrivingImprover improver = new GeneticDrivingImprover(solution, solver.rides, population, generations);
        improver.improve(SolutionScorer::new);
    }
}
