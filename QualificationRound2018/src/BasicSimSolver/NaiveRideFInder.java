package BasicSimSolver;

import model.Grid;
import model.Ride;
import util.DistUtil;

public class NaiveRideFInder extends RideFinder {
    static double TRAVEL_COST_CONST = -1;
    static double RIDE_REWARD_CONST = 1;

    public double rideRewardNaive(Ride ride, int time, int cabRow, int cabCol, int bonus) {
        // PRE: ride is actually available to cab
        // PRE: time <= ride.s
        int travelTime = DistUtil.timeToRideTo(cabRow, cabCol, ride.a, ride.b);
        if (travelTime != ride.s - time) {
            bonus = 0;
        }

        //FIXME: need to account for having to wait
        return TRAVEL_COST_CONST * travelTime +
               RIDE_REWARD_CONST * DistUtil.timeToRideTo(ride.a, ride.b, ride.x, ride.y) +
               bonus;

    }

    @Override
    public Ride findNextRide(Grid grid, Ride[] rides, int cabId, int time, boolean[] takenRides, int cabRow, int cabCol) {
        Ride bestRide = null;
        double bestReward = Double.MIN_VALUE;

        for (int i =0; i < rides.length; i++) {
            if (takenRides[i] || DistUtil.endTime(cabRow, cabCol, time, rides[i]) > grid.T) {
                continue;
            }

            double reward = rideRewardNaive(rides[i], time, cabRow, cabCol, grid.B);
            if (reward > bestReward) {
                bestRide = rides[i];
            }




            // NB dont return rides that won't finish in time

        }
        return bestRide;
    }
}
