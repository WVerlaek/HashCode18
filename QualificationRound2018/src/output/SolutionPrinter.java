package output;

import model.Ride;

import java.io.PrintStream;

public class SolutionPrinter extends solver.SolutionPrinter<SelfDrivingSolution> {

    public SolutionPrinter(SelfDrivingSolution solution) {
        super(solution);
    }

    @Override
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

        for (int i = 0; i < solution.grid.F - solution.vehicleRides.size(); i++) {
            out.println();
        }
    }
}
