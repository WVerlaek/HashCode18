import io.InputFile;
import io.InputReader;
import solver.HashcodeSolver;
import solver.Solution;


import java.io.PrintStream;

public class BruteForceSolver extends HashcodeSolver {

    public BruteForceSolver(InputFile file, boolean printToFile) {
        super(file, printToFile);
    }

    @Override
    public void parse(InputReader reader) {
        // ...
    }

    @Override
    public Solution solve() {
        return new BruteForceSolution(file, 0);
    }


    public static void main(String[] args) {
        new BruteForceSolver(new InputFile(InputFiles.KITTENS), true);
    }
}
