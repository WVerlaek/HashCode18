package solver;

import io.BestSolutionTracker;
import io.IncineratorOutputStream;
import io.InputFile;
import io.InputReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public abstract class HashcodeSolver {

    protected Solution solution;
    public InputFile file;

    private BestSolutionTracker bestSolutionTracker = new BestSolutionTracker();

    /**
     * Construct a solver for a specific input.
     *
     * # Step 1
     * {@link #parse(InputReader)} will be called first, which allows you to
     *  read the input and setup your data-structures.
     *
     * # Step 2
     * {@link #solve()} is called next, here you
     *  solve the problem.
     *
     * # Step 3
     * {@link Solution#printTo(PrintStream)} is called, where you
     *  print (only!) the solution to the provided output PrintStream
     *  (similar usage as printing to System.out).
     *
     * @param file the input file to solve, and defines the output file
     * @param printToFile whether the output will be printed to the output file, otherwise it will be printed to System.out
     */
    public HashcodeSolver(InputFile file, boolean printToFile) {
        this.file = file;
        long now = System.currentTimeMillis();
        InputReader reader = new InputReader(file);

        // step 1: parse input
        parse(reader);

        // step 2: solve and print to output
        solution = solve();

        // step 3: print solution
        long score = solution.score;
        PrintStream out;
        if (printToFile) {
            String fileName = file.outputFile(now, score);
            File outputFile = new File(fileName);
            out = prepareOutputFile(outputFile);
            System.err.println("Printed output to " + fileName + " - Score: " + solution.score);
        } else {
            out = new IncineratorOutputStream();//System.out;
        }
        solution.printTo(out);
        out.flush();

        // track best solution
        bestSolutionTracker.checkIfBestSolution(solution, now);
    }

    private PrintStream prepareOutputFile(File file) {
        try {
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
            }

            return new PrintStream(new FileOutputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Read the input file, construct and fill your data-structures here. */
    public abstract void parse(InputReader reader);

    /**
     * Solve the problem, and print the output to the provided PrintStream.
     * When specified, the output will be written to the output file.
     *
     * Note: don't print any debugging lines through out, use System.out for that.
     */
    public abstract Solution solve();

    public Solution getSolution() {
        return solution;
    }
}
