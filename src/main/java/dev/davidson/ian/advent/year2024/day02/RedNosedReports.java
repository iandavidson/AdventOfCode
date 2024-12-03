package dev.davidson.ian.advent.year2024.day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedNosedReports {

    private static final String INPUT_PATH = "adventOfCode/2024/day02/real.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2024/day02/sample.txt";

    public static void main(String[] args) {
        RedNosedReports redNosedReports = new RedNosedReports();
        log.info("Part1 test input: {}", redNosedReports.part1(readFile(SAMPLE_PATH)));
        log.info("Part1 real input: {}", redNosedReports.part1(readFile(INPUT_PATH)));
        log.info("\n");
        log.info("Part2 test input: {}", redNosedReports.part2(readFile(SAMPLE_PATH)));
        log.info("Part2 real input: {}", redNosedReports.part2(readFile(INPUT_PATH)));
    }

    private static List<Report> readFile(final String filePath) {
        List<Report> reports = new ArrayList<>();

        ClassLoader cl = RedNosedReports.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                reports.add(Report.newReport(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Unable to read file");
        }

        return reports;
    }

    public Integer part1(final List<Report> reports) {
        int count = 0;

        for (Report report : reports) {
            if (report.isSafe()) {
                count++;
            }
        }

        return count;
    }

    public Integer part2(final List<Report> reports) {
        int count = 0;

        for (Report report : reports) {
            if (report.isSafePart2()) {
                count++;
            }
        }

        return count;
    }
}
