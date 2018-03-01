package solver;

import io.InputFile;
import io.InputReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public abstract class HashcodeSolver {

    protected Solution solution;
    public InputFile file;

    /**
     * Construct a solver for a specific input.
     *
     * # Step 1
     * {@link #parse(InputReader)} will be called first, which allows you to
     *  read the input and setup your data-structures.
     *
     * # Step 2
     * {@link #solveAndPrintSolution(PrintStream)} is called next, here you
     *  solve the problem, and print (only!) the solution to the provided
     *  output PrintStream (similar usage as printing to System.out).
     *
     * @param file the input file to solve, and defines the output file
     * @param printToFile whether the output will be printed to the output file, otherwise it will be printed to System.out
     */
    public HashcodeSolver(InputFile file, boolean printToFile, boolean makeUniqueOutputFile) {
        this.file = file;

        PrintStream out;
        if (printToFile) {
            out = prepareOutputFile(file, makeUniqueOutputFile);
        } else {
            out = System.out;
        }
        InputReader reader = new InputReader(file);

        parse(reader);
        solution = solveAndPrintSolution(out);

        if (printToFile)
            System.err.println("Printed output to " + file.outputFile());
    }

    private PrintStream prepareOutputFile(InputFile input, boolean makeUniqueOutputFile) {
        try {
            File file = new File(input.outputFile(makeUniqueOutputFile));
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
    public abstract Solution solveAndPrintSolution(PrintStream out);

    public Solution getSolution() {
        return solution;
    }
}
