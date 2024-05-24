package dev.davidson.ian.advent.year2015.day08;

public record Code(String raw, int literalLength, int inMemLength) {

    /*
    Santa's list is a file that contains many double-quoted string literals,
    one on each line. The only escape sequences used are \\ (which represents a single backslash),
    \" (which represents a lone double-quote character),
    \x plus two hexadecimal characters (which represents a single character with that ASCII code).

    in other words, for counting:
    - every line starts and finishes with a: " ; literal +2, in mem +0;
    - escape quote ; literal +2, in mem +1
    - Hex address; 2 character prefix, then 2 more for addr; literal +4, in mem +1
     */

    private static final String BACK = "\\\\";// >\\<
    private static final String ESC_QUOTE = "\\\""; // >\"<
//    private static final String HEX = "\\x.."; // >\x<
    private static final String HEX = "[\\\\][x][0-9a-f]{2}"; //

    // >\x..<


    public static Code newCode(final String line){
        String operand = line.substring(1, line.length()-1);
        operand = operand.replaceAll(ESC_QUOTE, "~");
        operand = operand.replaceAll(HEX, "X");
        operand = operand.replaceAll(BACK, "");

        int inMemLength = operand.length();
        return new Code(line, line.length(), operand.length());
    }
}
