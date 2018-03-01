import io.InputFile;
import io.InputReader;
import solver.HashcodeSolver;
import solver.Solution;


import java.io.PrintStream;

public class BruteForceSolver extends HashcodeSolver {

    public BruteForceSolver(InputFile file, boolean printToFile, boolean makeUniqueOutputFile) {
        super(file, printToFile, makeUniqueOutputFile);
    }

    @Override
    public void parse(InputReader reader) {
        // ...
    }

    @Override
    public Solution solveAndPrintSolution(PrintStream out) {
        out.println("print is working!");
        return new BruteForceSolution(0);
    }


    public static void main(String[] args) {
        new BruteForceSolver(new InputFile(InputFiles.KITTENS), true, true);
    }
}
