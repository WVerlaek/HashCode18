package io;

import java.io.*;

/**
 * Use this to read input files.
 * Uses a {@link BufferedReader} internally, which is much
 * faster compared to using a {@link java.util.Scanner},
 * while providing a Scanner-like interface.
 */
public class InputReader {

    private final BufferedReader br;

    /**
     * Will read from {@link System#in}.
     */
    public InputReader() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Will read from file at pathName.
     */
    public InputReader(String pathName) {
        File f = new File(pathName);
        try {
            br = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Will read from the input file specified in InputFile.
     */
    public InputReader(InputFile input) {
        this(input.inputFile());
    }

    /** Read a single line from the input. Moves cursor to the next line. */
    public Line readLine() {
        try {
            return new Line(br.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Read a single line from the input. Moves cursor to the next line. */
    public String readLineString() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Read a single line and parse as int. */
    public int readLineInt() {
        return Integer.parseInt(readLineString());
    }

    /** Read a single line and parse as long. */
    public long readLineLong() {
        return Long.parseLong(readLineString());
    }

    /** Read a single line and parse as double. */
    public double readLineDouble() {
        return Double.parseDouble(readLineString());
    }

    /** Read a single line and parse as char. If the line contains multiple characters,
     * only the first is returned (the rest will be ignored, cursor goes to next line). */
    public char readLineChar() {
        return readLineString().charAt(0);
    }


    public static class Line {
        private String line;
        private int idx;
        public Line(String line) {
            this.line = line;
            this.idx = 0;
        }

        /**
         * Get the next string on this line.
         * Example:
         *  If line is "abc def ghi ", then calling nextString() three times will return
         *  "abc", "def", "ghi" respectively.
         */
        public String nextString() {
            if (idx >= line.length()) throw new IllegalStateException("End of line was already reached (length = " + line.length() + ")");

            int end = idx;
            while (end < line.length() && line.charAt(end) != ' ') end++;

            String s = line.substring(idx, end);
            idx = end + 1; // skip space
            return s;
        }

        /**
         * Get the next string on this line parsed as int.
         *
         * Note: this will not search further when the next string
         * is not an int, and will throw an exception instead.
         */
        public int nextInt() {
            return Integer.parseInt(nextString());
        }

        /**
         * Get the next string on this line parsed as double.
         *
         * Note: this will not search further when the next string
         * is not a double, and will throw an exception instead.
         */
        public double nextDouble() {
            return Double.parseDouble(nextString());
        }

        /**
         * Get the next string on this line parsed as long.
         *
         * Note: this will not search further when the next string
         * is not a long, and will throw an exception instead.
         */
        public long nextLong() {
            return Long.parseLong(nextString());
        }

        /**
         * Get the character at the current read position. Multiple characters may
         * follow this character, and will be returned by consecutive calls as well.
         * If a space follows the current char, the whitespace will be skipped and the
         * next read will be after the space.
         *
         * Examples:
         *  Line is "ab cd"
         *  Then calling nextChar() 4 times will return 'a', 'b', 'c', 'd' respectively.
         *
         *  Line is "ab  cd" (note the double space)
         *  Then calling nextChar() 4 times will return 'a', 'b', ' ', 'c' respectively.
         *
         *  Line is "a100"
         *  Then calling nextChar() followed by nextInt() will return 'a', 100 respectively.
         */
        public char nextChar() {
            if (idx >= line.length()) throw new IllegalStateException("End of line was already reached (length = " + line.length() + ")");

            char c = line.charAt(idx);
            idx++;

            // check if followed by space, increment idx if so
            if (idx < line.length() && line.charAt(idx) == ' ') idx++;

            return c;
        }

        @Override
        public String toString() {
            return line;
        }
    }
}
