package util;

import java.text.DecimalFormat;

public class ScoreFormat {

    private static DecimalFormat format = new DecimalFormat("#,###");

    public static String format(long score) {
        return format.format(score);
    }
}
