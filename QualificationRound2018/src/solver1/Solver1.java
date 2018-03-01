package solver1;

import io.InputFile;
import io.InputReader;
import solver.HashcodeSolver;
import solver.Solution;

import java.io.PrintStream;

public class Solver1 extends HashcodeSolver {
    /**
     * Construct a solver for a specific input.
     * <p>
     * # Step 1
     * {@link #parse(InputReader)} will be called first, which allows you to
     * read the input and setup your data-structures.
     * <p>
     * # Step 2
     * {@link #solveAndPrintSolution(PrintStream)} is called next, here you
     * solve the problem, and print (only!) the solution to the provided
     * output PrintStream (similar usage as printing to System.out).
     *
     * @param file                 the input file to solve, and defines the output file
     * @param printToFile          whether the output will be printed to the output file, otherwise it will be printed to System.out
     * @param makeUniqueOutputFile whether to override the default output file, or create a new unique file
     */
    public Solver1(InputFile file, boolean printToFile, boolean makeUniqueOutputFile) {
        super(file, printToFile, makeUniqueOutputFile);
    }

    @Override
    public void parse(InputReader reader) {
        //...
    }

    @Override
    public Solution solveAndPrintSolution(PrintStream out) {
        return null;
    }
}
