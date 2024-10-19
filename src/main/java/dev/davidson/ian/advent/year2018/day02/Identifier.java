package dev.davidson.ian.advent.year2018.day02;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Identifier {
    private final String rawId;
    private final int[] charCount;

    public static Identifier newIdentifier(final String rawId) {
        int[] charCount = new int[26];

        for (char ch : rawId.toCharArray()) {
            charCount[ch - 'a']++;
        }

        return new Identifier(rawId, charCount);
    }

    public boolean twoOfAKind() {
        for (int count : charCount) {
            if (count == 2) {
                return true;
            }
        }

        return false;
    }

    public boolean threeOfAKind() {
        for (int count : charCount) {
            if (count == 3) {
                return true;
            }
        }

        return false;
    }


    public static boolean has1Diff(final Identifier a, final Identifier b){
        int diffs = 0;
        for(int i = 0; i < 26; i++){
            if(a.charCount[i] != b.charCount[i]){
                diffs++;
            }

        }

        return diffs == 1;
    }
}
