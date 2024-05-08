package EncodeDecodeStrings;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class Solution {

    private final String delim = "\t$";

    public String encode(List<String> strs) {
        if(strs.isEmpty()){
            return null;
        }

        return String.join(delim, strs);
    }

    public List<String> decode(String str) {
        if(str == null){
            return Collections.emptyList();
        }

        return Arrays.asList(str.split("\\\t\\$"));
    }
}
