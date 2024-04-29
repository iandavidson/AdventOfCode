package org.example.advent.year2022.day17;

public enum DIRECTION {

    LEFT("<"), RIGHT(">");

    private final String value;

    DIRECTION(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
