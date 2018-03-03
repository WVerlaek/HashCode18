package io;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InputFile {
    private String name;

    /**
     * The name must match the input file exactly (after converting
     * to lowercase, and without extension)!
     */
    public InputFile(String name) {
        this.name = name;
    }

    public String inputFile() {
        return "input/" + name.toLowerCase() + ".in";
    }

    public String dataFile() {
        return "data/" + name.toLowerCase() + ".data";
    }

    public String outputFile() {
        return "output/" + name.toLowerCase() + ".out";
    }

    public String outputFile(long withTimestamp, long score) {
        String timestamp = formatTimestamp(withTimestamp);
        return "output/" + name.toLowerCase() + " " + score + " " + timestamp + ".out";
    }

    public String outputFile(String dir, long withTimestamp, long score) {
        String timestamp = formatTimestamp(withTimestamp);
        return dir + name.toLowerCase() + " " + score + " " + timestamp + ".out";
    }

    public String outputFile(long withTimestamp) {
        String timestamp = formatTimestamp(withTimestamp);
        return "output/" + name.toLowerCase() + " " + timestamp + ".out";
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("[yyyy-MM-dd HH-mm-ss]");
        Date now = new Date(timestamp);
        return sdfDate.format(now);
    }

    public String getName() {
        return name.toLowerCase();
    }
}
