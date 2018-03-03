import io.InputFile;
import solver.Solution;

import java.io.PrintStream;

public class BruteForceSolution extends Solution {
    /**
     * Holds the solution to a particular problem.
     *
     * @param score Score of this solution, which would be the score given by HashCode to this output.
     */
    public BruteForceSolution(InputFile input, long score) {
        super(input, score);
    }

    @Override
    public void printTo(PrintStream out) {
        out.println("Print is working!");
    }
}
