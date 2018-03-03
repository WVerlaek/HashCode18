package solver;

import io.InputFile;
import io.InputReader;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * Created by s148327 on 23-2-2017.
 */
public abstract class Solution {

    public long score;
    public InputFile input;

    /**
     * Holds the solution to a particular problem.
     * @param score Score of this solution, which would be the score given by HashCode to this output.
     */
    public Solution(InputFile input, long score) {
        this.input = input;
        this.score = score;
    }

    public Solution(InputFile input, InputReader in) {
        throw new UnsupportedOperationException("Implement your own deserialization code for this solution.");
    }

    public abstract void printTo(PrintStream out);

}
