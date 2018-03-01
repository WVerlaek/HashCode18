package output;

import model.Ride;

public class SolutionValidator {

    public boolean isValidSolution(SelfDrivingSolution solution) {
        boolean valid = true;

        // check that every ride is taken at most once
        boolean[] takenRide = new boolean[solution.vehicleRides.size()];
        for (Ride[] rides : solution.vehicleRides) {
            if (rides.length == 0) {
                valid = false;
                System.err.println("ERR: length-0 list of rides in solution");
            }

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
