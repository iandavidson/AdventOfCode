package dev.davidson.ian.advent.year2015.day16;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public record AuntComposition(int id, Map<Trait, Integer> composition){
    public static AuntComposition newAuntComposition(String s) {
        Map<Trait, Integer> composition = new HashMap<>();

        Pattern pattern = Pattern.compile("^.*?(\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)");
        Matcher matcher  = pattern.matcher(s);
        if(matcher.find()){
            int id = Integer.parseInt(matcher.group(1));
            composition.put(Trait.valueOf(matcher.group(2)), Integer.parseInt(matcher.group(3)));
            composition.put(Trait.valueOf(matcher.group(4)), Integer.parseInt(matcher.group(5)));
            composition.put(Trait.valueOf(matcher.group(6)), Integer.parseInt(matcher.group(7)));
            return new AuntComposition(id, composition);
        }else{
            throw new IllegalStateException("couldn't find match w/ regex");
        }
    }
}
