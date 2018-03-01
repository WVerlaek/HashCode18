package ridecentric;

import base.RidesSolver;
import io.InputFile;
import kdtree.KDTree;
import model.Ride;
import output.SelfDrivingSolution;

import java.util.*;

public class RideCentricSol extends RideCentricBase {
    int width = 5;

    int freeCabs = this.grid.F;
    int rows = this.grid.R;
    int columns = this.grid.C;
    int nrOfRides = this.grid.N;
    int bonus = this.grid.B;
    int nrOfSteps = this.grid.T;
    KDTree<ArrayList<Cab>> kdTree = new KDTree<>(2);

    int rideIndex = 0;

    public RideCentricSol(InputFile file, boolean printToFile, boolean makeUniqueOutputFile) {
        super(file, printToFile, makeUniqueOutputFile);
    }


    @Override
    void preprocess() {
        Arrays.sort(this.rides, Comparator.comparingInt(ride -> ride.s));

        Random rnd = new Random();
        for (int i = 0; i < 100; i++) {
            Ride ride = this.rides[rnd.nextInt(rides.length)];
            kdTree.insert(new double[]{ride.a, ride.b}, new ArrayList<>());
        }

    }

    @Override
    Ride pickRide() {
        return this.rides[rideIndex++];
    }

    @Override
    int matchCab(Ride ride) {
        List<ArrayList<Cab>> closeStops = kdTree.nearest(new double[]{ride.a, ride.b}, 5);

        Cab best = null;

        for (ArrayList<Cab> stop : closeStops) {
            for (Cab cab : stop) {
                int arrive = cab.available + Math.abs(cab.x - ride.a) + Math.abs(cab.y - ride.b);
                if (arrive <= ride.s) {
                    stop.remove(cab);
                    return cab.id;
                }

                if (arrive <= ride.f - Math.abs(ride.a - ride.x) - Math.abs(ride.b - ride.y)) {
                    best = cab;
                }
            }
        }

        if (best != null) {
            return best.id;
        }

        if (freeCabs >= 0) {
            return --freeCabs;
        }

        return -1;
    }

    @Override
    boolean isDone() {
        return rideIndex >= this.rides.length;
    }

    private class Cab {
        public int id;
        public int x;
        public int y;
        public int available;

        Cab(int id, int x, int y, int available) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.available = available;
        }
    }
}
