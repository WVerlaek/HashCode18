package io;

import solver.Solution;
import util.ScoreFormat;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class BestSolutionTracker {

    private static final String BEST_SOLUTIONS_DIR = "output_best/";
    private static final String BEST_SOLUTION_SCORES_FILE = ".best_solution_scores";

    public static final long NO_SCORE_FOUND = -1;

    public boolean checkIfBestSolution(Solution solution, long timestamp) {
        InputFile input = solution.input;
        long currentBest = getBestSolutionScore(input);
        if (currentBest == NO_SCORE_FOUND || currentBest < solution.score) {
            setBestSolution(input, solution, timestamp);

            System.err.println("Found new best solution for input " + input.getName() + " with score " +
                    ScoreFormat.format(solution.score) + " (compared to " + ScoreFormat.format(currentBest) + ")");
            return true;
        }
        return false;
    }

    public boolean hasBestSolution(InputFile input) {
        return getBestSolutionScore(input) != NO_SCORE_FOUND;
    }

    public long getBestSolutionScore(InputFile input) {
        List<ScoreEntry> entries = getBestScoreEntries();
        return entries.stream()
                .filter(scoreEntry -> scoreEntry.inputName.equals(input.getName()))
                .findFirst()
                .map(scoreEntry -> scoreEntry.score)
                .orElse(NO_SCORE_FOUND);
    }

    private void setBestSolutionScore(InputFile input, long score) {
        List<ScoreEntry> entries = getBestScoreEntries();
        for (ScoreEntry entry : entries) {
            if (entry.inputName.equals(input.getName())) {
                entry.score = score;

                writeBestScoreEntries(entries);
                return;
            }
        }

        // entry not found, add new entry
        entries.add(new ScoreEntry(input.getName(), score));
        writeBestScoreEntries(entries);
    }

    public InputStream getBestSolutionInputStream(InputFile input) {
        File dir = getBestSolutionsDir();
        try {
            for (File solution : dir.listFiles()) {
                if (solution.getName().contains(input.getName())) {
                    return new FileInputStream(solution);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ScoreEntry> getBestScoreEntries() {
        try {
            File scoreFile = getBestSolutionScoresFile();
            List<String> lines = Files.readAllLines(scoreFile.toPath());

            List<ScoreEntry> entries = new ArrayList<>();
            for (String line : lines) {
                int split = line.lastIndexOf(" ");
                if (split >= 0) {
                    String name = line.substring(0, split);
                    String score = line.substring(split + 1);
                    entries.add(new ScoreEntry(name, Long.parseLong(score)));
                }
            }
            return entries;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeBestScoreEntries(List<ScoreEntry> entries) {
        try {
            File scoreFile = getBestSolutionScoresFile();
            PrintWriter writer = new PrintWriter(scoreFile);

            for (ScoreEntry entry : entries) {
                writer.println(entry.inputName + " " + entry.score);
            }

            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class ScoreEntry {
        String inputName;
        long score;

        public ScoreEntry(String inputName, long score) {
            this.inputName = inputName;
            this.score = score;
        }
    }

    private void setBestSolution(InputFile input, Solution solution, long ts) {
        getBestSolutionsDir(); // creates the directory if needed
        String fileName = input.outputFile(BEST_SOLUTIONS_DIR, ts, solution.score);

        setBestSolutionScore(input, solution.score);

        // delete previous best
        deleteBestSolution(input);

        try {
            File outputFile = new File(fileName);
            PrintStream out = new PrintStream(outputFile);

            solution.printTo(out);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteBestSolution(InputFile file) {
        File dir = getBestSolutionsDir();
        for (File solution : dir.listFiles()) {
            if (solution.getName().contains(file.getName())) {
                solution.delete();
            }
        }
    }

    private File getBestSolutionsDir() {
        File file = new File(BEST_SOLUTIONS_DIR);
        file.mkdirs();
        return file;
    }

    private File getBestSolutionScoresFile() {
        File file = new File(BEST_SOLUTION_SCORES_FILE);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
