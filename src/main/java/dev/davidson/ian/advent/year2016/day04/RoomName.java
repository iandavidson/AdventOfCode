package dev.davidson.ian.advent.year2016.day04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record RoomName(String encryptedName, Integer sectorId, String checkSum) {
    private static final Character HYPHEN = '-';
    private static final Pattern PATTERN = Pattern.compile("^([a-z-]+)(\\d+)\\[([a-z]{5})\\]$");

    /*
    hwbba-ejqeqncvg-tgugctej-
    232
    [iyrqv]
     */
    public static RoomName newRoomName(final String line) {

        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
            return new RoomName(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3));
        }
        throw new IllegalStateException();
    }

    public boolean isValid() {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : encryptedName.toCharArray()) {
            if (ch != HYPHEN) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
        }

        List<Character> sorted = new ArrayList<>(map.keySet());
        Collections.sort(sorted, (a, b) -> {
            if (map.get(a) == map.get(b)) {
                return a - b;
            } else {
                return map.get(b) - map.get(a);
            }
        });

        String calculated = String.valueOf(sorted.get(0)) +
                sorted.get(1) +
                sorted.get(2) +
                sorted.get(3) +
                sorted.get(4);

        return checkSum.equals(calculated);
    }
}
