package dev.davidson.ian.advent.year2015.day08;

public record Code(String raw, String inMem, String encoded) {

    private static final String BACK = "\\\\\\\\";// >\\<
    private static final String ESC_QUOTE = "\\\\\""; // >\"<
    private static final String HEX = "[\\\\][x][0-9a-f]{2}"; // >\x..<

    public static Code newCode(final String line) {
        //part1
        String inMem = line.substring(1, line.length() - 1) //rip out wrapping quotes
                .replaceAll(HEX, "X") //replace 4 hex characters with single char
                .replaceAll(ESC_QUOTE, "~") //replace escaped quote with single char
                .replaceAll(BACK, "^"); //replace escaped backslash with single char

        //part2
        String encoded = "!!" + line //leave in quotes, add 4 more chars
                .replaceAll(HEX, ">>>>>") //replace 4 hex characters with 5 chars
                .replaceAll(ESC_QUOTE, "####") //replace backslash and quote with 4 chars
                .replaceAll(BACK, "%%%%") + "!!"; //replace escaped backslash with 4 chars (i think, idk no example)

        return new Code(
                line,
                inMem,
                encoded
        );
    }
}
