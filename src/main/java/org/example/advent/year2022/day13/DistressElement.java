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
    private static final String COMMA = ",";

    @Builder.Default
    private List<DistressElement> innerPackets = new ArrayList<>();

    @Builder.Default
    private Integer value = null;

    private ElementType elementType;

    public static DistressElement newDistressPacket(final String line) {
        if (line.charAt(0) == L_B) {
            String trimmedLine = line.substring(1, line.length() - 1);
            List<DistressElement> innerPackets = new ArrayList<>();

            //instead of this
//            String[] elementArray = trimmedLine.split(COMMA);

            /*
            must only split on indexs that have bypassed an equal amount of '[' & ']'
             */
            for(int i = 0; i < trimmedLine.length(); i++){

            }


            for (String element : elementArray) {
                innerPackets.add(newDistressPacket(element.trim()));
            }

            return DistressElement.builder().innerPackets(innerPackets).elementType(ElementType.LIST).build();
        } else {

            Integer value = Integer.parseInt(line);
            return DistressElement.builder().value(value).elementType(ElementType.SINGLE_ITEM).build();
        }
    }
}
