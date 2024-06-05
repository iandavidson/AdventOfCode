package dev.davidson.ian.advent.year2015.day19;

public record Rule(String pattern, String treated) implements  Comparable<Rule> {

    @Override
    public int compareTo(Rule other){
//        return other.treated.length() - treated.length();
        return treated.length() - other.treated.length() ;
    }

}
