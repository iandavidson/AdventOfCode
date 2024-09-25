package dev.davidson.ian.advent.year2017.day18;

public record Operand(Character charOp, Long numOp) {
    public static Operand newOperand(String op) {
        if (Character.isAlphabetic(op.charAt(0))) {
            return new Operand(op.charAt(0), null);
        } else {
            return new Operand(null, Long.parseLong(op));
        }
    }

    public boolean isLabel() {
        return charOp != null;
    }

    public boolean isNumber() {
        return numOp != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(" ");
        if (isLabel()) {
            return sb.append(charOp).toString();
        } else {
            return sb.append(numOp).toString();
        }
    }
}
