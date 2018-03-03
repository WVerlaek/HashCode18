package output;

import datastructures.IPoint;
import model.Ride;
import util.DistUtil;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SolutionScorer extends solver.SolutionScorer<SelfDrivingSolution> {
    private IPoint[] driverLocations;
    private int nrDrivers;
    private List<Ride[]> rides;

    public SolutionScorer(SelfDrivingSolution solution) {
        super(solution);

        nrDrivers = solution.vehicleRides.size();
        rides = Collections.unmodifiableList(solution.vehicleRides);
    }

    @Override
    public long getScore() {
        long totalScore = 0;

        driverLocations = new IPoint[nrDrivers];
        outer:
        for (int i = 0; i < nrDrivers; i++) {
            int curTime = 0;
            int curR = 0;
            int curC = 0;
            for (Ride ride : rides.get(i)) {
                int timeToGetToStart = DistUtil.timeToRideTo(curR, curC, ride.a, ride.b);
                curTime = Math.max(curTime + timeToGetToStart, ride.s);

                int rideDuration = DistUtil.timeToRideTo(ride.a, ride.b, ride.x, ride.y);

                if (curTime + rideDuration > solution.grid.T) {
                    // this driver reached the time limit
                    continue outer;
                }

                if (curTime == ride.s) {
                    totalScore += solution.grid.B;
                }

                int endTime = curTime + rideDuration;

                if (endTime <= ride.f) {
                    totalScore += rideDuration;
                }

                curTime = endTime;
                curR = ride.x;
                curC = ride.y;
            }

        }
        return totalScore;
    }
}
