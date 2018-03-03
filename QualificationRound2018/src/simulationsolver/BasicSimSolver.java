package simulationsolver;

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

    public BasicSimSolver(InputFile file, boolean printToFile) {
        super(file, printToFile);
    }

    @Override
    public void parse(InputReader reader) {
        super.parse(reader);

        int nRides = grid.N;
        takenRides = new boolean[nRides];
    }

    @Override
    public SelfDrivingSolution solve2() {

        // keeps track of which rides are done by which cabs
        List<List<Ride>> ridesOfCabs = new ArrayList<>();

        Queue<Event> Q = new PriorityQueue<>();
        for (int i = 0; i < grid.F; i++) {
            Q.add(new Event(0, i, 0, 0));
            ridesOfCabs.add(new ArrayList<>());
        }

        RideFinder rideFinder = new NaiveRideFinder();


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


        SelfDrivingSolution.Builder builder = new SelfDrivingSolution.Builder(file, grid);

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
        for (String file : InputFiles.ALL_FILES) {
            totalScore += new BasicSimSolver(new InputFile(file), false).getSolution().score;
        }
//        totalScore += new BasicSimSolver(new InputFile(InputFiles.D_METROPOLIS), true).getSolution().score;
//        System.err.println(totalScore);
    }

    @Override
    public SelfDrivingSolution getSolution() {
        return (SelfDrivingSolution) solution;
    }
}
