package ridecentric;

import base.InputFiles;
import io.InputFile;
import kdtree.KDTree;
import model.Ride;
import output.SolutionPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class RideCentricSol extends RideCentricBase {
    int samples;
    int freeCabs;
    KDTree<ArrayList<Cab>> kdTree;

    int rideIndex = 0;

    public RideCentricSol(InputFile file, boolean printToFile, boolean makeUniqueOutputFile) {
        super(file, printToFile, makeUniqueOutputFile);
    }

    @Override
    void postprocess(List<List<Ride>> ridesForCab) {

    }


    @Override
    void preprocess() {
        freeCabs = this.grid.F;
        kdTree = new KDTree<>(2);
        samples = 1000;
        Arrays.sort(this.rides, Comparator.comparingInt(ride -> ride.s));

        Random rnd = new Random();
        Set<String> keysUsed = new HashSet<>(samples);
        for (int i = 0; i < samples; i++) {
            int idx = rnd.nextInt(rides.length);
            Ride ride = this.rides[idx];
            String rideKey = ride.a + " " + ride.b;
            if (!keysUsed.contains(rideKey)) {
                kdTree.insert(new double[]{ride.a, ride.b}, new ArrayList<>());
                keysUsed.add(rideKey);
            }
        }

    }

    @Override
    Ride pickRide() {
        return this.rides[rideIndex++];
    }

    @Override
    int matchCab(Ride ride) {
        List<ArrayList<Cab>> closeStops = kdTree.nearest(new double[]{ride.a, ride.b}, 15 + new Random().nextInt(35));

        Cab best = null;
        ArrayList<Cab> stopUsed = null;
        int arriveBest = 0;

        for (ArrayList<Cab> stop : closeStops) {
            for (Cab cab : stop) {
                int arrive = cab.available + Math.abs(cab.x - ride.a) + Math.abs(cab.y - ride.b);
                if (arrive <= ride.s) {
                    stop.remove(cab);
                    cab.x = ride.x;
                    cab.y = ride.y;
                    cab.available = ride.s + Math.abs(ride.x - ride.a) + Math.abs(ride.y - ride.b);
                    kdTree.nearest(new double[]{cab.x, cab.y}).add(cab);
                    return cab.id;
                }

                if (best == null && (arrive <= ride.f - Math.abs(ride.a - ride.x) - Math.abs(ride.b - ride.y))) {
                    arriveBest = arrive;
                    best = cab;
                    stopUsed = stop;
                }
            }
        }

        if (best != null) {
            stopUsed.remove(best);
            best.available = arriveBest + Math.abs(ride.x - ride.a) + Math.abs(ride.y - ride.b);
            best.x = ride.x;
            best.y = ride.y;
            kdTree.nearest(new double[]{best.x, best.y}).add(best);
            return best.id;
        }

        if (freeCabs >= 0) {
            int newAvailable = ride.a + ride.b + Math.abs(ride.x - ride.a) + Math.abs(ride.y - ride.b);
            int newId = --freeCabs;
            Cab newCab = new Cab(newId, ride.x, ride.y, newAvailable);
            kdTree.nearest(new double[]{newCab.x, newCab.y}).add(newCab);
            return newId;
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

    public static void main(String[] args) throws FileNotFoundException {
        String[] names = new String[] {
                InputFiles.A_EXAMPLE,
                InputFiles.B_SHOULD_BE_EASY,
                InputFiles.C_NO_HURRY,
                InputFiles.D_METROPOLIS,
                InputFiles.E_HIGH_BONUS
        };
        long[] scores = new long[names.length];
        RideCentricSol[] sols = new RideCentricSol[names.length];
        int reps = 100;
        for (int r = 0; r < reps; r++) {
            for (int i = 0; i < names.length; i++) {
                RideCentricSol sol = new RideCentricSol(new InputFile(names[i]), false, true);
                if (sol.getSolution().score > scores[i]) {
                    scores[i] = sol.getSolution().score;
                    sols[i] = sol;
                    System.out.println(i + " " + scores[i]);
                }

            }
        }
        long score = 0;
        for (int i = 0; i < names.length; i++) {
            SolutionPrinter printer = new SolutionPrinter(sols[i].realSol);
            printer.printTo(new PrintStream(new File("solution" + i + ".out")));
            score += scores[i];
        }
        System.out.println("" + score);
    }
}
