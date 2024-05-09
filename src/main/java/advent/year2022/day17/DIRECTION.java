package advent.year2022.day17;

import lombok.Getter;

@Getter
public enum DIRECTION {

    LEFT("<"), RIGHT(">");

    private final String value;

    DIRECTION(String value){
        this.value = value;
    }

}
