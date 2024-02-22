package org.example.checkIfPointIsReachable;

import java.util.HashMap;
import java.util.Map;

class Solution {
    ///causes stack overflow, brute force approach, not so good.
//    public boolean isReachable(int targetX, int targetY) {
//        Map<Integer, Map<Integer, Boolean>> beenThere = new HashMap<>();
//
//        return isReachable(targetX, targetY, 1, 1, beenThere);
//    }
//
//
//    private boolean isReachable(int targetX, int targetY, int currentX, int currentY, Map<Integer, Map<Integer, Boolean>> beenThere) {
//        if (targetX == currentX && targetY == currentY) {
//            return true;
//        }
//
//        if (beenThere.get(currentX) != null && beenThere.get(currentX).get(currentY) != null) {
//            return false; // fail fast, no use in following the same path we've been on
//        }
//
//        System.out.println("x: " + currentX + " y: " + currentY);
//
//        //mark been there
//        if (beenThere.get(currentX) == null) {
//            Map<Integer, Boolean> newEntry = new HashMap<>();
//            newEntry.put(currentY, true);
//            beenThere.put(currentX, newEntry);
//        } else {
//            Map<Integer, Boolean> temp = beenThere.get(currentX);
//            temp.put(currentY, true);
//        }
//
//        return isReachable(targetX, targetY, currentX, currentY - currentX, beenThere) || isReachable(targetX, targetY, currentX - currentY, currentY, beenThere) || isReachable(targetX, targetY, 2 * currentX, currentY, beenThere) || isReachable(targetX, targetY, currentX, 2 * currentY, beenThere);
//    }

    public boolean isReachable(int targetX, int targetY) {
        //use GCD
        return false;
    }


//    public static void main(String[] args){
//        Solution solution = new Solution();
//
//        System.out.println(solution.isReachable(6, 9));
//    }

}