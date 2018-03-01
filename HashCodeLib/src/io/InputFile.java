package io;

public enum InputFile {
    // These must match the input files exactly (after converting to lowercase)!
    FILE_NAME_1,
    FILE_NAME_2;

    public String inputFile() {
        return "input/" + name().toLowerCase() + ".in";
    }

    public String dataFile() {
        return "data/" + name().toLowerCase() + ".data";
    }

    public String outputFile() {
        return "output/" + name().toLowerCase() + ".out";
    }
}
