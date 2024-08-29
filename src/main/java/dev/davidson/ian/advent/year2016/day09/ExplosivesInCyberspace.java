package dev.davidson.ian.advent.year2016.day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExplosivesInCyberspace {

    private static final String INPUT_PATH = "adventOfCode/2016/day09/input.txt";
    private static final String SAMPLE_1_PATH = "adventOfCode/2016/day09/sample1.txt";


    public static void main(String[] args) {
        ExplosivesInCyberspace explosivesInCyberspace = new ExplosivesInCyberspace();
        log.info("Part1: {}", explosivesInCyberspace.part1());
        log.info("Part2: {}", explosivesInCyberspace.part2());
    }

    public long part1() {
        String line = readFile();

        long count = 0;
        //find first that we know is just text and no compression
        int i = 0;

        while (i < line.length()) {
            while (i < line.length() && line.charAt(i) != '(') {
                count++;
                i++;
            }

            if (i >= line.length()) {
                break;
            }


            int left = i;
            //assuming that first character is '('
            while (line.charAt(i) != ')') {
                i++;
            }

            String rule = line.substring(left + 1, i);
            String[] ruleParts = rule.split("x");
            int preLength = Integer.parseInt(ruleParts[0]);
            int repeats = Integer.parseInt(ruleParts[1]);

            count += (long) preLength * repeats;

            i += preLength;
            i++;
        }

        return count;
    }

    public long part2(){
        String line = readFile();
        return decompressExpression(line);
    }

    private long decompressExpression(final String line){
        //(3890x4): "(476x3)(469x6)(2x11)JA(178x6)(21x4)(15x7)VLGRVVFKAFABGTK"
        //(25x3): "(3x3)ABC(2x3)XY(5x2)PQRST
        long count = 0;
        int i = 0;
        while (i < line.length()) {
            while (i < line.length() && line.charAt(i) != '(') {
                count++;
                i++;
            }

            if (i >= line.length()) {
                break;
            }


            int left = i;
            //assuming that first character is '('
            while (line.charAt(i) != ')') {
                i++;
            }

            String rule = line.substring(left + 1, i);
            String[] ruleParts = rule.split("x");
            int preLength = Integer.parseInt(ruleParts[0]);
            int repeats = Integer.parseInt(ruleParts[1]);

            i++;//move past ')'

            count += (decompressExpression(line.substring(i, i + preLength)) * repeats);

            i += preLength;
        }

        return count;
    }

    private String readFile() {
        String line = "";
        ClassLoader cl = ExplosivesInCyberspace.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return line;
    }
}
