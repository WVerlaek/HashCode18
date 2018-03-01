package BasicSimSolver;

import base.RidesSolver;
import io.InputFile;
import io.InputReader;
import model.Ride;
import output.SelfDrivingSolution;
import solver.Solution;

import java.io.PrintStream;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

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

    //
//    public double computeRideReward(int t, int r, int c, Ride ride) {
//
//    }
//
//    public Ride findRide(int t, int r, int c) {
//        return null;
//    }
//
//    public Triple<Ride, Integer, Integer, Integer> perCab(int t, int r, int c, int depthLim) {
//        return
//    }
//


    @Override
    public SelfDrivingSolution solve() {

        Queue<Event> Q = new PriorityQueue<>();
        for (int i = 0; i < grid.F; i++) {
            Q.add(new Event(0, i));
        }


        while (!Q.isEmpty()) {
            Event e = Q.poll();
            if (e.time > grid.T) {
                break;
            }

            int cabId = e.cabId;

            // todo search for new ride

        }


        SelfDrivingSolution.Builder builder = new SelfDrivingSolution.Builder(grid);

        // add rides for cabs

        return builder.build(true);
    }

    static class Event implements Comparable<Event> {
        int cabId;
        int time;

        public Event(int time, int cabId) {
            this.cabId = cabId;
            this.time = time;
        }

        @Override
        public int compareTo(Event o) {
            return Integer.compare(time, o.time);
        }
    }
}
