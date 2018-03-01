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
        return outputFile(false);
    }

    public String outputFile(boolean makeUnique) {
        String unique;
        if (makeUnique) {
            SimpleDateFormat sdfDate = new SimpleDateFormat(" [yyyy-MM-dd HH-mm-ss]");
            Date now = new Date();
            unique = sdfDate.format(now);
        } else {
            unique = "";
        }
        return "output/" + name.toLowerCase() + unique + ".out";
    }
}
