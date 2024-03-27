package org.example.advent.year2023.twentythree;

public enum DIRECTION {

    LEFT, RIGHT, UP, DOWN;

    public static int[] move(DIRECTION direction){
        switch(direction){
            case LEFT -> {
                return new int[]{0, -1};
            }
            case RIGHT -> {
                return new int[]{0, 1};
            }
            case UP -> {
                return new int[]{-1, 0};
            }
            case DOWN -> {
                return new int[]{1, 0};
            }
        }
        return null;
    }
}
