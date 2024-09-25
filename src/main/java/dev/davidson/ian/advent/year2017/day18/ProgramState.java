package dev.davidson.ian.advent.year2017.day18;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import lombok.Data;

@Data
public class ProgramState {

    private final Map<Character, Long> map;
    private final Queue<Long> queue;
    private int index;
    private int sendCount;

    public ProgramState(final Long startValue) {
        this.map = new HashMap<>();
        this.index = 0;
        this.sendCount = 0;
        this.queue = new LinkedList<>();

        map.put('p', startValue);
    }

    public void bumpIndex() {
        this.index++;
    }

    public void bumpSendCount() {
        this.sendCount++;
    }

    public void addToQueue(final Long value) {
        queue.add(value);
    }
}
