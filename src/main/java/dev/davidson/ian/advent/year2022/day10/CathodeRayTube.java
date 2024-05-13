package dev.davidson.ian.advent.year2022.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CathodeRayTube {
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/2022/day10/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2022/day10/input.txt";
    private static final String MINI_INPUT_PATH = "adventOfCode/2022/day10/mini.txt";


    public static void main(String[] args) {
        CathodeRayTube cathodeRayTube = new CathodeRayTube();
        System.out.println("part1: " + cathodeRayTube.part1());
        System.out.println("part2:");
        cathodeRayTube.part2();
    }

    public Long part1() {
        List<Instruction> instructions = readFile();

        Long cycle = 1L;
        Long register = 1L;

        Long grandTotal = 0L;

        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.instructionType().getCycles(); i++) {
                cycle++;

                if (i == 1) {
                    register += instruction.amount();
                }

                if ((cycle - 20) % 40 == 0) {
                    System.out.println("cycle: " + cycle + "; register value: " + register + " product: " + cycle * register);
                    grandTotal += cycle * register;
                }
            }
        }

        return grandTotal;
    }

    public void part2() {
        List<Instruction> instructions = readFile();

        Long cycle = 1L;
        Long register = 1L;

        List<Character> crt = new ArrayList<>();

        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.instructionType().getCycles(); i++) {

                if (register - 1 <= ((cycle - 1) % 40) && ((cycle - 1) % 40) <= register + 1) {
                    crt.add('#');
                } else {
                    crt.add('.');
                }

                if (i == 1) {
                    register += instruction.amount();
                }

                cycle++;
            }
        }

        for (int i = 0; i < 6; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 40; j++) {
                sb.append(crt.get(i * 40 + j));
            }
            System.out.println(sb);
        }
    }

    private List<Instruction> readFile() {
        List<String> inputLines = new ArrayList<>();
        ClassLoader cl = CathodeRayTube.class.getClassLoader();
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

        return inputLines.stream().map(Instruction::parse).toList();
    }
}
