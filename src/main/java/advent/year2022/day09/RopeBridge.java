package advent.year2022.day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RopeBridge {

    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day09/sample.txt";
    private static final String SAMPLE_INPUT_PATH_2 = "adventOfCode/2022/day09/sample2.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day09/input.txt";

    public static void main(String[] args) {
        RopeBridge ropeBridge = new RopeBridge();
        System.out.println("part1: " + ropeBridge.part1());
        System.out.println("part2: " + ropeBridge.part2());
    }

    public long part1() {
        List<Instruction> instructions = readFile();
        Rope rope = new Rope(2);
        instructions.forEach(rope::move);
        return rope.getTailVisited().size();
    }

    public long part2() {
        List<Instruction> instructions = readFile();
        Rope rope = new Rope(10);
        instructions.forEach(rope::move);
        return rope.getTailVisited().size();
    }

    private List<Instruction> readFile() {
        List<String> inputLines = new ArrayList<>();
        ClassLoader cl = RopeBridge.class.getClassLoader();
        File file = new File(Objects.requireNonNull(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile()));
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.err.println("File could not be found");
            throw new RuntimeException(e);
        }


        List<Instruction> instructions = new ArrayList<>();

        for (String line : inputLines) {
            String[] chunks = line.split("\\s+");
            instructions.add(new Instruction(Direction.valueOf(chunks[0]), Integer.parseInt(chunks[1])));
        }

        return instructions;
    }
}
