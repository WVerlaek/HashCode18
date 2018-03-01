package BasicSimSolver;

import datastructures.Pair;
import model.Grid;
import model.Ride;
import util.DistUtil;

public class NaiveRideFInder extends RideFinder {
    static double TRAVEL_COST_CONST = -1;
    static double RIDE_REWARD_CONST = 1;

    static final double SCORE_NO_NEXT_RIDE_FOUND = -10;
    static final double SCORE_NEXT_RIDE_DISCOUNT_FACTOR = 0.4d;

    public Pair<Ride, Double> findMaxRewardRide(Grid grid, Ride[] rides, boolean[] takenRides, int time, int cabRow, int cabCol, int depth) {
        Ride bestRide = null;
        double bestReward = Double.MIN_VALUE;

        for (int i = 0; i < rides.length; i++) {
            Ride ride = rides[i];
            int endTime = DistUtil.endTime(cabRow, cabCol, time, ride);

            // conditions...
            if (takenRides[i])
                continue;
            if (endTime > grid.T)
                continue;
            if (endTime > ride.f)
                continue;

//            double reward = findMaxRewardRide(grid, rides, takenRides, time, cabRow, cabCol, depth -rides[i], time, cabRow, cabCol, grid.B);
//            if (reward > bestReward) {
//                bestRide = rides[i];
//            }


            // calculate reward here...
            double scoreNextRide = 0d;
            if (depth > 0) {
                takenRides[i] = true;
                // recurse as well...
                Pair<Ride, Double> recurse = findMaxRewardRide(grid, rides, takenRides, endTime, ride.x, ride.y, depth - 1);
                takenRides[i] = false;
                scoreNextRide = recurse == null ? SCORE_NO_NEXT_RIDE_FOUND : (recurse.b * SCORE_NEXT_RIDE_DISCOUNT_FACTOR);
            }

            // calculate score
            int travelTime = DistUtil.timeToRideTo(cabRow, cabCol, ride.a, ride.b);
            int bonus = (time + travelTime <= ride.s) ? grid.B : 0;
            double totalScore = TRAVEL_COST_CONST * Integer.max(travelTime, ride.s - time) +
                    RIDE_REWARD_CONST * DistUtil.timeToRideTo(ride.a, ride.b, ride.x, ride.y) +
                    bonus +
                    scoreNextRide;

            // check if this is best seen so far, update max
            if (totalScore > bestReward) {
                bestReward = totalScore;
                bestRide = ride;
            }
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
        int depth = 4;

        Pair<Ride, Double> bestRide = findMaxRewardRide(grid, rides, takenRides, time, cabRow, cabCol, depth);
        return bestRide.a;
    }
}
