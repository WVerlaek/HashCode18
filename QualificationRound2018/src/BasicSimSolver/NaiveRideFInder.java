package BasicSimSolver;

import datastructures.Pair;
import model.Grid;
import model.Ride;
import util.DistUtil;

public class NaiveRideFInder extends RideFinder {
    static double TRAVEL_COST_CONST = -1;
    static double RIDE_REWARD_CONST = 1;

    public Pair<Ride, Double> findMaxRewardRide(Grid grid, Ride[] rides, boolean[] takenRides, int time, int cabRow, int cabCol) {
        Ride bestRide = null;
        double bestReward = Double.MIN_VALUE;

        for (int i =0; i < rides.length; i++) {
            Ride ride = rides[i];
            int endTime = DistUtil.endTime(cabRow, cabCol, time, ride);

            // conditions...
            if (takenRides[i])
                continue;
            if (endTime > grid.T)
                continue;
            if (ride.a < time)
                continue;
            if (endTime > ride.f)
                continue;
            // conditions...

//            double reward = findMaxRewardRide(grid, rides, takenRides, time, cabRow, cabCol, depth -rides[i], time, cabRow, cabCol, grid.B);
//            if (reward > bestReward) {
//                bestRide = rides[i];
//            }


            // calculate reward here...
            takenRides[i] = true;
            // recurse as well...
//            findNextRide(...)
            takenRides[i] = false;

            // check if this is best seen so far, update max


            // NB dont return rides that won't finish in time

        }

        return new Pair<>(bestRide, bestReward);


//        int travelTime = DistUtil.timeToRideTo(cabRow, cabCol, ride.a, ride.b);
//        if (time + travelTime > ride.s) {
//            bonus = 0;
//        }
//
//
//        takenRides[ride.id] = true;
//        // recurse
//
//        takenRides[ride.id] = false;
//
//
//        //FIXME: need to account for having to wait
//        return TRAVEL_COST_CONST * Integer.max(travelTime, ride.s - time) +
//               RIDE_REWARD_CONST * DistUtil.timeToRideTo(ride.a, ride.b, ride.x, ride.y) +
//               bonus;
    }

    @Override
    public Ride findNextRide(Grid grid, Ride[] rides, int cabId, int time, boolean[] takenRides, int cabRow, int cabCol) {
        Ride bestRide = null;
        double bestReward = Double.MIN_VALUE;

        for (int i =0; i < rides.length; i++) {
            Ride ride = rides[i];
            int endTime = DistUtil.endTime(cabRow, cabCol, time, ride);
            if (takenRides[i])
                continue;
            if (endTime > grid.T)
                continue;
            if (ride.a < time)
                continue;
            if (endTime > ride.f)
                continue;

//            double reward = findMaxRewardRide(rides[i], time, cabRow, cabCol, grid.B);
//            if (reward > bestReward) {
//                bestRide = rides[i];
//            }

            // NB dont return rides that won't finish in time

        }
        return bestRide;
    }

    public int ridesfindRide(Grid grid, Ride[] rides, int cabId, int time, boolean[] takenRides, int cabRow, int cabCol, int depth) {
        return 0;
    }
}
