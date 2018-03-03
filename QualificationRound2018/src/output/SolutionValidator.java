package output;

import model.Ride;

public class SolutionValidator extends solver.SolutionValidator<SelfDrivingSolution> {

    @Override
    public boolean isValidSolution(SelfDrivingSolution solution) {
        boolean valid = true;


        // check that we don't output more lists of rides than available vehicles
        if (solution.vehicleRides.size() > solution.grid.F) {
            valid = false;
            System.err.println("ERR more lists of rides than available vehicles");
        }

        // check that every ride is taken at most once
        boolean[] takenRide = new boolean[solution.grid.N];
        for (Ride[] rides : solution.vehicleRides) {
            for (Ride ride : rides) {
                int id = ride.id;
                if (id < 0 || id >= solution.grid.N) {
                    valid = false;
                    System.err.println("ERR Ride id invalid: " + id);
                } else {
                    if (takenRide[id]) {
                        valid = false;
                        System.err.println("ERR Ride taken more than once: " + id);
                    }
                    takenRide[id] = true;
                }
            }
        }

        return valid;
    }
}
