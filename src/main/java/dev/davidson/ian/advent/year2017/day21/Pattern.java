package dev.davidson.ian.advent.year2017.day21;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Pattern(List<String> pattern, List<List<String>> patternRotations) {

    public static Pattern newPattern(List<String> input){
        List<List<String>> rotations = new ArrayList<>();

        //regular
        rotations.add(new ArrayList<>(input));

        //90 degrees
        /*

        123
        456
        789

        becomes
        741
        852
        963

        270 becomes:

        369
        258
        147
         */
        List<String> turned90 = new ArrayList<>();
        for(int r = input.size()-1; r > -1; r--){
            StringBuilder row = new StringBuilder();
            for(int c = 0; c < input.getFirst().length(); c++){
                row.append(input.get(r).charAt(c));
            }
            turned90.add(row.toString());
        }
        rotations.add(turned90);


        //180 degrees
        List<String> turned180 = new ArrayList<>(input);
        Collections.reverse(turned180);
        rotations.add(turned180.stream().map(str -> new StringBuilder(str).reverse().toString()).toList());

        //270 degrees



        return new Pattern(rows, rotations);
    }

    public boolean isMatch(final String compare){

    }
}
