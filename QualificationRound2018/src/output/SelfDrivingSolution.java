package output;

import model.Grid;
import model.Ride;
import solver.Solution;

import java.util.ArrayList;
import java.util.List;

public class SelfDrivingSolution extends Solution {

    protected List<Ride[]> vehicleRides;
    protected Grid grid;


    /**
     * Holds the solution to a particular problem.
     *
     * @param score Score of this solution, which would be the score given by HashCode to this output.
     */
    private SelfDrivingSolution(long score, List<Ride[]> vehicleRides, Grid grid) {
        super(score);

        this.vehicleRides = vehicleRides;
        this.grid = grid;
    }


    public static class Builder {

        private static Ride[] rideArrayType = new Ride[0];
        private List<Ride[]> vehicleRides = new ArrayList<>();
        private Grid grid;

        public Builder(Grid grid) {
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
            SelfDrivingSolution solution = new SelfDrivingSolution(0L /* temp */, vehicleRides, grid);
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
}
