package dev.davidson.ian.advent.year2015.day07;

import dev.davidson.ian.advent.year2015.day07.instruction.AndInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.AssignmentInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.NotInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.OrInstruction;
import dev.davidson.ian.advent.year2015.day07.instruction.ShiftInstruction;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationFactory {
    private static final Pattern LABEL_PATTERN = Pattern.compile("\\D+");

    public static Operation newOperation(final String line, Map<String, Wire> map) {
        /*

456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h

         */
        String[] split = line.split("\\s+->\\s+");
        String[] operationTokens = split[0].split("\\s+");

        if (line.contains("AND")) {
//            x AND y -> d
            return new AndInstruction(newWire(operationTokens[0].trim(), map), newWire(operationTokens[2], map), newWire(split[1].trim(), map));

        } else if (line.contains("OR")) {
//            x OR y -> e
            return new OrInstruction(newWire(operationTokens[0].trim(), map), newWire(operationTokens[2], map), newWire(split[1].trim(), map));

        } else if (line.contains("SHIFT")) {
//            x LSHIFT 2 -> f
            return new ShiftInstruction(operationTokens[1].trim(), newWire(operationTokens[0].trim(), map),
                    Integer.parseInt(operationTokens[2].trim()), newWire(split[1].trim(), map));

        } else if (line.contains("NOT")) {
            //NOT x -> h
            return new NotInstruction(newWire(operationTokens[1].trim(), map), newWire(split[1].trim(), map));

        } else {
            //outright assignment
            //123 -> x
            // y -> x !!!!!
            return new AssignmentInstruction(newWire(split[0].trim(), map), newWire(split[1].trim(), map));
        }
    }

    public static Wire newWire(String val, Map<String, Wire> map) {

        Matcher m = LABEL_PATTERN.matcher(val);
        if (m.find()) {
            //value is a string
            if (map.containsKey(m.group(0))) {
                return map.get(val);
            } else {
                Wire wire = new Wire(val);
                map.put(val, wire);
                return wire;
            }
        } else {
            return new Wire(val);
        }
    }
}
