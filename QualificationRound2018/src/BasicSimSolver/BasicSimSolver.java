package BasicSimSolver;

import base.InputFiles;
import base.RidesSolver;
import io.InputFile;
import io.InputReader;
import model.Ride;
import output.SelfDrivingSolution;
import solver.Solution;
import util.DistUtil;

import java.io.PrintStream;
import java.util.*;

public class BasicSimSolver extends RidesSolver {

    boolean[] takenRides;

    /**
     * Construct a solver for a specific input.
     * <p>
     * # Step 1
     * {@link #parse(InputReader)} will be called first, which allows you to
     * read the input and setup your data-structures.
     * <p>
     * # Step 2
     * {@link #solveAndPrintSolution(PrintStream)} is called next, here you
     * solve the problem, and print (only!) the solution to the provided
     * output PrintStream (similar usage as printing to System.out).
     *
     * @param file                 the input file to solve, and defines the output file
     * @param printToFile          whether the output will be printed to the output file, otherwise it will be printed to System.out
     * @param makeUniqueOutputFile whether to override the default output file, or create a new unique file
     */
    public BasicSimSolver(InputFile file, boolean printToFile, boolean makeUniqueOutputFile) {
        super(file, printToFile, makeUniqueOutputFile);
    }

    @Override
    public void parse(InputReader reader) {
        super.parse(reader);

        int nRides = grid.N;
        takenRides = new boolean[nRides];
    }

    @Override
    public SelfDrivingSolution solve() {

        // keeps track of which rides are done by which cabs
        List<List<Ride>> ridesOfCabs = new ArrayList<>();

        Queue<Event> Q = new PriorityQueue<>();
        for (int i = 0; i < grid.F; i++) {
            Q.add(new Event(0, i, 0, 0));
            ridesOfCabs.add(new ArrayList<>());
        }

        RideFinder rideFinder = new NaiveRideFInder();


        while (!Q.isEmpty()) {
            Event e = Q.poll();
            if (e.time > grid.T) {
                break;
            }

            int cabId = e.cabId;

            Ride nextRide = rideFinder.findNextRide(grid, rides, cabId, e.time, takenRides, e.row, e.col);
            if (nextRide == null) {
                // find a ride through linear search
                for (int i = 0; i < rides.length; i++) {
                    if (takenRides[i]) {
                        continue;
                    }
                    Ride ride = rides[i];

                    int endTime = DistUtil.endTime(e.row, e.col, e.time, ride);
                    if (endTime <= grid.T && endTime <= ride.f) {
                        nextRide = ride;
                        break;
                    } // else not able to complete ride on time
                }
            }

            if (nextRide != null) {
                // mark ride
                if (takenRides[nextRide.id]) {
                    System.err.println("Ride already taken... " + nextRide.id);
                }
                takenRides[nextRide.id] = true;
                ridesOfCabs.get(cabId).add(nextRide);

                int endTime = DistUtil.endTime(e.row, e.col, e.time, nextRide);
                Q.add(new Event(endTime, cabId, nextRide.x, nextRide.y));
            } else {
                // no more rides available, even not after a linear search
                // this cap will stop its work.
            }
        }


        SelfDrivingSolution.Builder builder = new SelfDrivingSolution.Builder(grid);

        // add rides for cabs
        for (List<Ride> rides : ridesOfCabs) {
            builder.addVehicle(rides);
        }

        return builder.build(true);
    }

    static class Event implements Comparable<Event> {
        int cabId;
        int time;
        int row, col;

        public Event(int time, int cabId, int row, int col) {
            this.cabId = cabId;
            this.time = time;
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Event o) {
            return Integer.compare(time, o.time);
        }
    }

    public static void main(String[] args) {
        long totalScore = 0;
//        for (String file : InputFiles.ALL_FILES) {
//            totalScore += new BasicSimSolver(new InputFile(file), true, true).getSolution().score;
//        }
        totalScore += new BasicSimSolver(new InputFile(InputFiles.D_METROPOLIS), true, true).getSolution().score;
        System.err.println(totalScore);
    }
}
