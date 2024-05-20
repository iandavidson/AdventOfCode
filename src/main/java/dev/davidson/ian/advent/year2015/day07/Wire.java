package dev.davidson.ian.advent.year2015.day07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wire {
    private Integer value;
    private String label;

    private static final Pattern PATTERN = Pattern.compile("(\\d+)");

    public Wire(final String value) {
        Matcher m = PATTERN.matcher(value);

        if(m.find()){
            this.value = Integer.parseInt(m.group(1).trim());
            this.label = null;
        }else{
            this.value = null;
            this.label = value.trim();
        }
    }

    public Boolean isEligible(Map<String, Integer> labelMap) {
        if(value != null){
            return true;
        }else {
            return labelMap.containsKey(label);
        }
    }
}
