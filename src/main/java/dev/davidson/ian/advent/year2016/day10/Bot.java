package dev.davidson.ian.advent.year2016.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Bot{

    public static Bot newBotChip(final Integer id, final Integer chipId){
        List<Integer> chips = new ArrayList<>();
        chips.add(chipId);
        return Bot.builder().id(id).chips(chips).build();
    }

    private final Integer id;

    @Builder.Default
    private final List<Integer> chips = new ArrayList<>();
    private Rule rule;

    public void addChip(Integer chipId){
        chips.add(chipId);
        Collections.sort(chips);
    }

    public void emptyChips(){
        chips.removeFirst();
        chips.removeFirst();
    }

    public boolean canExecute(){
        return chips.size() == 2;
    }

    public int getHigh(){
        if(canExecute()){
            return chips.getLast();
        }else{
            throw new IllegalStateException("can't execute");
        }
    }

    public int getLow(){
        if(canExecute()){
            return chips.getFirst();
        }else{
            throw new IllegalStateException("can't execute");
        }
    }
}
