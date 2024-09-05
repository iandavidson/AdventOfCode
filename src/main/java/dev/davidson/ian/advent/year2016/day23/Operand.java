package dev.davidson.ian.advent.year2016.day23;

public record Operand(Character charOp, Integer numOp) {
    private static final Character NEGATIVE = '-';

    public static Operand newOperand(String op){
        if(Character.isAlphabetic(op.charAt(0))){
            return new Operand(op.charAt(0), null);
        }else if(op.charAt(0)  == NEGATIVE){
            Integer numOp = Integer.parseInt(op.substring(1)) * -1;
            return new Operand(null, numOp);
        }else{
            return new Operand(null, Integer.parseInt(op));
        }
    }

    public boolean isLabel(){
        return charOp == null;
    }

    public boolean isNumber(){
        return numOp == null;
    }
}
