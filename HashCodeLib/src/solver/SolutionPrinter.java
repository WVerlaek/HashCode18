package solver;

import java.io.PrintStream;

public abstract class SolutionPrinter<T extends Solution> {

    protected T solution;

    public SolutionPrinter(T solution) {
        this.solution = solution;
    }

    public abstract void printTo(PrintStream out);
}
