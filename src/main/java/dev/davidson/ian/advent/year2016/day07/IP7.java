package dev.davidson.ian.advent.year2016.day07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record IP7(List<String> enclosed, List<String> open) {
    private static final String BRACKET_REGEX_STRING = "\\[[a-z]+\\]";
    private static final String PIPE_REGEX_STRING = "\\|";
    private static final Pattern BRACKET_PATTERN = Pattern.compile(BRACKET_REGEX_STRING);

    public static IP7 newIP7(final String line) {
        Matcher matcher = BRACKET_PATTERN.matcher(line);

        List<String> enclosed = new ArrayList<>();
        while (matcher.find()) {
            String tempEnclosed = matcher.group();
            enclosed.add(tempEnclosed.substring(1, tempEnclosed.length() - 1));
        }

        String treatedLine = line.replaceAll(BRACKET_REGEX_STRING, "|");
        List<String> open = Arrays.asList(treatedLine.split(PIPE_REGEX_STRING));

        return new IP7(enclosed, open);
    }

    public boolean supportsTLS() {
        for (String enclosedSequence : enclosed) {
            if (containsABBA(enclosedSequence)) {
                return false;
            }
        }

        for (String openSequence : open) {
            if (containsABBA(openSequence)) {
                return true;
            }
        }

        return false;
    }


    private boolean containsABBA(final String sequence) {
        for (int i = 3; i < sequence.length(); i++) {
            if (sequence.charAt(i - 3) == sequence.charAt(i) &&
                    sequence.charAt(i - 2) == sequence.charAt(i - 1) &&
                    sequence.charAt(i) != sequence.charAt(i - 1)) {
                return true;
            }
        }
        return false;
    }

    public boolean supportsSSL() {
        Set<String> abas = new HashSet<>();
        for (String openSequence : open) {
            abas.addAll(findABAs(openSequence));
        }

        for (String enclosedSequence : enclosed) {
            if (findBABs(enclosedSequence, abas)) {
                return true;
            }
        }

        return false;
    }

    private Set<String> findABAs(final String sequence) {
        Set<String> abas = new HashSet<>();
        for (int i = 2; i < sequence.length(); i++) {
            if (sequence.charAt(i) == sequence.charAt(i - 2) &&
                    sequence.charAt(i) != sequence.charAt(i - 1)) {
                abas.add(sequence.substring(i - 2, i + 1));
            }
        }

        return abas;
    }

    private boolean findBABs(final String sequence, final Set<String> abas) {
        for (String aba : abas) {
            String bab = String.valueOf(aba.charAt(1)) + aba.charAt(0) + aba.charAt(1);
            if (sequence.contains(bab)) {
                return true;
            }
        }
        return false;
    }
}
