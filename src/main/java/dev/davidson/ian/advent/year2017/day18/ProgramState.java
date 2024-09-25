package dev.davidson.ian.advent.year2017.day18;

import java.util.Map;
import java.util.Queue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ProgramState {

    private final Map<Character, Long> registerMap;
    private int index;
    private final Queue<Long> queue;

    public void bumpIndex(){
        this.index++;
    }

    public void addToQueue(final Long value){
        queue.add(value);
    }
}
