package org.example.advent.year2022.day13;

import java.util.ArrayList;
import java.util.List;

public record PacketPair(DistressElement left, DistressElement right) {

            /*
        rules:
        go left and right packets concurrently:


    If both values are integers, the lower integer should come first.
        If the left integer is lower than the right integer, the inputs are in the right order.
        If the left integer is higher than the right integer, the inputs are not in the right order.
        Otherwise, the inputs are the same integer; continue checking the next part of the input. (implicitly OK)

    If both values are lists, compare the first value of each list, then the second value, and so on.
        If the left list runs out of items first, the inputs are in the right order.
        If the right list runs out of items first, the inputs are not in the right order.
        If the lists are the same length and no comparison makes a decision about the order, continue checking the next part of the input.

    If exactly one value is an integer,
        convert the integer to a list which contains that integer as its only value, then retry the comparison.
        For example, if comparing [0,0,0] and 2, convert the right value to [2] (a list containing 2); the result is then found by instead comparing [0,0,0] and [2].

         */
        public Boolean isPacketValid(){

            boolean complete = false;
            //both will be list type
            while(!complete){
                //find next for left

                if(left().getElementType() == ElementType.LIST){
                    left().getInnerPackets().get(0);
                } else if(left().getElementType() == ElementType.SINGLE_ITEM);

                //find next for right

                //compare -> any finding that indicates incorrectness should return false from here in iteration
            }

            return true;
        }
}
