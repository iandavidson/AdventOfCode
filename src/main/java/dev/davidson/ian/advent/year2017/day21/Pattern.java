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
        /*


        123
        456
        789

        369
        258
        147
         */
        List<String> turned270 = new ArrayList<>();
        for(int c = input.getFirst().length()-1; c > -1; c--){
            StringBuilder row = new StringBuilder();
            for(int r = 0; r < input.size(); r++){
                row.append(input.get(r).charAt(c));
            }

            turned270.add(row.toString());
        }

        return new Pattern(input, rotations);
    }

    public boolean isMatch(final List<String> compare){
        //protection so we can blindly check any matrix against another
        if(compare.size() != pattern.size()){
            return false;
        }

        return isMatchHelper(compare, patternRotations.get(0)) ||
                isMatchHelper(compare,patternRotations.get(1)) ||
                isMatchHelper(compare,patternRotations.get(2)) ||
                isMatchHelper(compare,patternRotations.get(3));
    }

    private boolean isMatchHelper(final List<String> compare, final List<String> rotation){

        for(int i =0 ; i < compare.size(); i++){
            for(int j= 0;j < compare.get(i).length(); j++){
                if(compare.get(i).charAt(j) != rotation.get(i).charAt(j)){
                    return false;
                }
            }
        }

        return true;
    }
}
