package org.example.advent.year2022.day13;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class DistressElement {

    private static final Character L_B = '[';
    private static final Character R_B = ']';
    private static final Character COMMA = ',';

    @Builder.Default
    private List<DistressElement> innerPackets = new ArrayList<>();

    @Builder.Default
    private Integer value = null;

    private ElementType elementType;

    public static DistressElement newDistressPacket(final String line) {
        if (line.charAt(0) == L_B) {
            String trimmedLine = line.substring(1, line.length() - 1);
            if(trimmedLine.isEmpty()){
                return DistressElement.builder().innerPackets(new ArrayList<>()).elementType(ElementType.EMPTY_LIST).build();
            }

            List<DistressElement> innerPackets = new ArrayList<>();

            List<Integer> splitIndexes = findValidCommaIndexes(trimmedLine);
            //add length of trimmed string so we have left and right boundaries for substring call
            splitIndexes.add(trimmedLine.length());

            int leftIndex = 0;
            for(Integer rightIndex : splitIndexes){
                innerPackets.add(
                        newDistressPacket(trimmedLine.substring(leftIndex, rightIndex))
                );

                leftIndex = rightIndex + 1;
            }

            return DistressElement.builder().innerPackets(innerPackets).elementType(ElementType.LIST).build();
        } else {

            Integer value = Integer.parseInt(line);
            return DistressElement.builder().value(value).elementType(ElementType.SINGLE_ITEM).build();
        }
    }

    private static List<Integer> findValidCommaIndexes(String trimmedLine){
        List<Integer> foundValidSplitIndexes = new ArrayList<>();
        int left = 0;
        int right = 0;
        for(int i = 0; i < trimmedLine.length(); i++){
            if(trimmedLine.charAt(i) == L_B){
                left++;
            } else if(trimmedLine.charAt(i) == R_B){
                right++;
            } else if(trimmedLine.charAt(i) == COMMA && left == right){
                foundValidSplitIndexes.add(i);
            }
        }

        return foundValidSplitIndexes;
    }
}
