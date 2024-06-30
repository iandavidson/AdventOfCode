package dev.davidson.ian.advent.year2022.day21;

import lombok.Data;

@Data
public class Monkey {

    private final String name;
    private final Long defaultValue;
    private final String dep1;
    private final String dep2;
    private final OPERATION operation;

    public Monkey(final String line) {
        String[] chunks = line.split("\\s+");
        this.name = chunks[0].substring(0, chunks[0].length() - 1);

        if (chunks.length == 2) {
            //default value
            this.defaultValue = Long.parseLong(chunks[1]);
            this.dep1 = null;
            this.dep2 = null;
            this.operation = null;
        } else {
            //deps
            this.defaultValue = null;
            this.dep1 = chunks[1];
            this.dep2 = chunks[3];
            this.operation = OPERATION.OPERATION_MAP.get(chunks[2]);
        }
    }
}
