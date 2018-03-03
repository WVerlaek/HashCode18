package output;

import io.InputFile;
import io.InputReader;
import model.Grid;
import model.Ride;
import solver.Solution;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class SelfDrivingSolution extends Solution {

    public List<Ride[]> vehicleRides;
    public Grid grid;


    /**
     * Holds the solution to a particular problem.
     *
     * @param score Score of this solution, which would be the score given by HashCode to this output.
     */
    private SelfDrivingSolution(InputFile input, long score, List<Ride[]> vehicleRides, Grid grid) {
        super(input, score);

        this.vehicleRides = vehicleRides;
        this.grid = grid;
    }

    public SelfDrivingSolution(InputFile input, InputReader in) {
        super(input, in);

        try {
            // todo refactor try catch into library method of InputReader
            int nLines = 0;
            while (true) {
                InputReader.Line line = in.readLine();
                if (line.length() > 0) {
                    nLines++;

                    int n = line.nextInt();
                    int[] rides = new int[n];
                    for (int i = 0; i < n; i++) {
                        rides[i] = line.nextInt();
                    }
                }
            }
        } catch (Exception e) {

        }

    }

    public static class Builder {

        private static Ride[] rideArrayType = new Ride[0];
        private List<Ride[]> vehicleRides = new ArrayList<>();
        private Grid grid;
        private InputFile input;

        public Builder(InputFile input, Grid grid) {
            this.input = input;
            this.grid = grid;
        }

        public Builder addVehicle(Ride... rides) {
            vehicleRides.add(rides);
            return this;
        }

        public Builder addVehicle(List<Ride> rides) {
            return addVehicle(rides.toArray(rideArrayType));
        }

        public SelfDrivingSolution build(boolean validateSolution) {
            SelfDrivingSolution solution = new SelfDrivingSolution(input, 0L /* temp */, vehicleRides, grid);
            solution.score = new SolutionScorer(solution).getScore();

            if (validateSolution) {
                SolutionValidator validator = new SolutionValidator();
                if (!validator.isValidSolution(solution)) {
                    System.err.println("Solution not valid!");
                }
            }

            return solution;
        }
    }

    @Override
    public void printTo(PrintStream out) {
        new SolutionPrinter(this).printTo(out);
    }
}
