package base;

import io.InputFile;
import io.InputReader;
import model.*;
import output.SelfDrivingSolution;
import output.SolutionPrinter;
import solver.HashcodeSolver;
import solver.Solution;

import java.io.PrintStream;

public abstract class RidesSolver extends HashcodeSolver {
    public Grid grid;
    public Ride[] rides;
    public SelfDrivingSolution realSol;

    public RidesSolver(InputFile file, boolean printToFile) {
        super(file, printToFile);
    }

    @Override
    public void parse(InputReader reader) {
        InputReader.Line init = reader.readLine();
        grid = new Grid();
        grid.R = init.nextInt();
        grid.C = init.nextInt();
        grid.F = init.nextInt();
        grid.N = init.nextInt();
        grid.B = init.nextInt();
        grid.T = init.nextInt();

        rides = new Ride[grid.N];

        for (int i = 0; i < grid.N; i++) {
            InputReader.Line rideLine = reader.readLine();
            int a = rideLine.nextInt();
            int b = rideLine.nextInt();
            int x = rideLine.nextInt();
            int y = rideLine.nextInt();
            int s = rideLine.nextInt();
            int f = rideLine.nextInt();
            rides[i] = new Ride(i, a, b, x, y, s, f);
        }
    }

    @Override
    final public Solution solve() {
        SelfDrivingSolution solution = solve2();
        realSol = solution;
        return solution;
    }

    public abstract SelfDrivingSolution solve2();
}