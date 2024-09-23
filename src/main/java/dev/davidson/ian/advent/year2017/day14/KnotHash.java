package dev.davidson.ian.advent.year2017.day14;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record KnotHash(String line){

    private static final List<Integer> END = List.of(17, 31, 73, 47, 23);
    private static final Integer SIZE = 256;

    public String apply(){
        byte [] bytes = this.line.getBytes(StandardCharsets.UTF_8);
        List<Integer> inputs = IntStream.range(0, bytes.length).map(i -> bytes[i]).boxed().collect(Collectors.toList());
        inputs.addAll(END);
        List<Integer> rope = IntStream.range(0, SIZE).boxed().collect(Collectors.toList());

        int skip = 0;
        int currentIndex = 0;
        for(int i = 0; i< 64; i++){
            for(int shift : inputs){
                tieKnot(rope, currentIndex, currentIndex + shift);
                currentIndex = (currentIndex + shift + skip) % SIZE;
                skip++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 16; i++){
            Integer temp = rope.get(16 * i);
            for(int j = 1; j < 16; j++){
                temp ^= rope.get(16 * i + j);
            }

            sb.append(Integer.toHexString(temp));
        }

        return sb.toString();
    }

    private void tieKnot(List<Integer> rope, final int currentIndex, final int finalIndex){
        List<Integer> indexSequence = new ArrayList<>();
        for(int i = currentIndex; i  <  finalIndex; i++){
            indexSequence.add(i % SIZE);
        }

        Collections.reverse(indexSequence);

        List<Integer> valueSequence = new ArrayList<>();
        for(int idx : indexSequence){
            valueSequence.add(rope.get(idx));
        }

        for(int i = 0; i < valueSequence.size(); i++){
            rope.set((i + currentIndex) % SIZE, valueSequence.get(i));
        }
    }
}
