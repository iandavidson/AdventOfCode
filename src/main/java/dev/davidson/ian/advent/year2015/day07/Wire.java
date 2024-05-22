package dev.davidson.ian.advent.year2015.day07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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

    public Integer get(){
        if(value != null){
            return value;
        }else{
            return null;
        }
    }
}
