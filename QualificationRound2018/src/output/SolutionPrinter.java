package output;

import model.Ride;

import java.io.PrintStream;

public class SolutionPrinter {

    private SelfDrivingSolution solution;

    public SolutionPrinter(SelfDrivingSolution solution) {
        this.solution = solution;
    }

    public void printTo(PrintStream out) {
        for (Ride[] rides : solution.vehicleRides) {
            int m = rides.length;
            if (m == 0) continue;

            out.print(m);

            for (Ride ride : rides) {
                out.print(' ');
                out.print(ride.id);
            }
            out.println();
        }
    }
}
