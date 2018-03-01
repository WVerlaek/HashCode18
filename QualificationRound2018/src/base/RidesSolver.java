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

    public RidesSolver(InputFile file, boolean printToFile, boolean makeUniqueOutputFile) {
        super(file, printToFile, makeUniqueOutputFile);
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
    public Solution solveAndPrintSolution(PrintStream out) {
        SelfDrivingSolution solution = solve();

        SolutionPrinter printer = new SolutionPrinter(solution);
        printer.printTo(out);

        return solution;
    }

    public abstract SelfDrivingSolution solve();
}