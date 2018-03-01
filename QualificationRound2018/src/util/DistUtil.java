package util;

import model.Ride;

public class DistUtil {

    public static int timeToRideTo(int rFrom, int cFrom, int rTo, int cTo) {
        return Math.abs(rFrom - rTo) + Math.abs(cFrom - cTo);
    }

    public static int endTime(int rFrom, int cFrom, int curTime, Ride ride) {
        int timeToStart = timeToRideTo(rFrom, cFrom, ride.a, ride.b);
        int rideDuration = timeToRideTo(ride.a, ride.b, ride.x, ride.y);

        return Math.max(curTime + timeToStart, ride.s) + rideDuration;
    }
}
