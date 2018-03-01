package util;

public class DistUtil {

    public static int timeToRideTo(int rFrom, int cFrom, int rTo, int cTo) {
        return Math.abs(rFrom - rTo) + Math.abs(cFrom - cTo);
    }
}
