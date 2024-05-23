package dev.davidson.ian.advent.year2015.day07;

import dev.davidson.ian.advent.year2015.day07.instruction.AssignmentInstruction;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class SomeAssemblyRequired {

    private static final String TERMINAL_LABEL = "a";

    private static final String SAMPLE_PATH = "adventOfCode/2015/day07/sample.txt";
    private static final String INPUT_PATH = "adventOfCode/2015/day07/input.txt";

    public static void main(String[] args) {
        SomeAssemblyRequired someAssemblyRequired = new SomeAssemblyRequired();
        log.info("part1: {}", someAssemblyRequired.part1());
        log.info("part2: {}", someAssemblyRequired.part2());
    }

    public int part1() {
        Map<String, Wire> map = new HashMap<>();
        List<Operation> operations = readFile(map);

        while (map.get(TERMINAL_LABEL).getValue() == null) {
            for (Operation current : operations) {
                if (current.isEligible()) {
                    current.evaluate();
                }
            }
        }

        return map.get(TERMINAL_LABEL).getValue();
    }

    public int part2(){
        Map<String, Wire> map = new HashMap<>();
        List<Operation> operations = readFile(map);

        while (map.get(TERMINAL_LABEL).getValue() == null) {
            for (Operation current : operations) {
                if(current instanceof AssignmentInstruction assignmentInstruction
                        && ((AssignmentInstruction) current).getResult().getLabel().equals("b")){

                    assignmentInstruction.getOperand().setValue(46065);
                }

                if (current.isEligible()) {
                    current.evaluate();
                }
            }
        }

        return map.get(TERMINAL_LABEL).getValue();
    }

    private List<Operation> readFile(Map<String, Wire> map) {
        List<Operation> operations = new ArrayList<>();

        ClassLoader cl = SomeAssemblyRequired.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(INPUT_PATH)).getFile());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String next = scanner.nextLine();
                operations.add(OperationFactory.newOperation(next, map));
                int i = 0;
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }

        return operations;
    }


}
