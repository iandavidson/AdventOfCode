package org.example.advent.year2023.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Gondola {


    private static final String INPUT_PATH = "adventOfCode/2023/day03/input.txt";
    private static final String SAMPLE_INPUT_PATH_1 = "adventOfCode/2023/day03/input2.txt";
    private static final String SAMPLE_INPUT_PATH_2 = "adventOfCode/2023/day03/input3.txt";

    private static boolean isSymbolValid(Character c) {
        return !Character.isLetterOrDigit(c) && !c.equals('.');
    }

    private static boolean isInbounds(int gridHeight, int gridWidth, int row, int column) {
        if (row < 0 || row > gridHeight - 1) {
            return false;
        }

        return column >= 0 && column <= gridWidth - 1;
    }

    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            ClassLoader classLoader = Gondola.class.getClassLoader();
            File file = new File(classLoader.getResource(INPUT_PATH).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return input;
    }

    public static List<Part> findParts(List<String> input) {
        List<Part> parts = new ArrayList<>();
        for (int k = 0; k < input.size(); k++) {
            //invoke helper for each (multi digit) number we find
            String number = "";
            int startIndex = -1;
            int endIndex = -1;
            boolean previousIsDigit = false;
            //iterates over row
            for (int i = 0; i < input.get(k).length(); i++) {

                if (Character.isDigit(input.get(k).charAt(i))) {
                    startIndex = i;
                    while (i < input.get(k).length() && Character.isDigit(input.get(k).charAt(i))) {
                        endIndex = i;
                        i++;
                    }

                    //call helper
                    Part part = newPart(input, k, startIndex, endIndex);

                    if (!part.getSymbols().isEmpty()) {
                        parts.add(part);
                    }
                }
            }
        }

        return parts;
    }

    private static Part newPart(List<String> inputLines, Integer lineIndex, Integer numberStartIndex, Integer numberFinalIndex) {
        Part part = new Part();
        part.setStartIndex(numberStartIndex);
        part.setEndIndex(numberFinalIndex);
        part.setRow(lineIndex);
        part.setNumber(Integer.parseInt(inputLines.get(lineIndex).substring(numberStartIndex, numberFinalIndex + 1)));

        //validate the following:
        int gridHeight = inputLines.size();
        int gridWidth = inputLines.get(0).length();

        for (int i = numberStartIndex - 1; i < numberFinalIndex + 2; i++) {
            for (int j = lineIndex - 1; j < lineIndex + 2; j++) {
                if (isInbounds(gridHeight, gridWidth, i, j)) {
                    char temp = inputLines.get(j).charAt(i);
                    if (isSymbolValid(temp)) {
                        part.getSymbols().add(new Symbol(temp, j, i, j + ":" + i));
                    }
                }
            }
        }

        return part;
    }

    public static void main(String[] args) {
        Gondola gondola = new Gondola();

//        gondola.part1();
        gondola.part2();
    }

    public int part1() {
        List<String> input = readFile();

        List<Part> parts = findParts(input);
        int sum = 0;

        for (Part part : parts) {
            System.out.println("adding to sum: " + part.getNumber());
            sum += part.getNumber();
            System.out.println("sum: " + sum);
        }

        System.out.println("final sum: " + sum);

        return sum;
    }

    public int part2() {
        List<String> input = readFile();

        List<Part> parts = findParts(input);
        int sum = 0;

        Map<Symbol, List<Part>> symbolMap = new HashMap<>();
        for (Part part : parts) {
            for (Symbol symbol : part.getSymbols()) {
                if (!symbolMap.containsKey(symbol)) {
                    symbolMap.put(symbol, new ArrayList<>());
                }
                symbolMap.get(symbol).add(part);
            }
        }
        for (Symbol symbol : symbolMap.keySet()) {
            if (symbolMap.get(symbol).size() == 2) {
                sum = sum + (symbolMap.get(symbol).get(0).getNumber() * symbolMap.get(symbol).get(1).getNumber());
                System.out.println("Sum: " + sum);
            }
        }
        System.out.println("final sum: " + sum);
        return sum;
    }

    public static class Part {
        private final List<Symbol> symbols;
        private Integer number;
        private Integer row;
        private Integer startIndex;
        private Integer endIndex;


        public Part() {
            symbols = new ArrayList<>();
        }

        public List<Symbol> getSymbols() {
            return symbols;
        }

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        public Integer getRow() {
            return row;
        }

        public void setRow(Integer row) {
            this.row = row;
        }

        public Integer getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(Integer startIndex) {
            this.startIndex = startIndex;
        }

        public Integer getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(Integer endIndex) {
            this.endIndex = endIndex;
        }

    }


    public static class Symbol {
        private final Character value;
        private final Integer row;
        private final Integer column;
        private final String identifier;

        public Symbol(Character value, Integer row, Integer column, String identifier) {
            this.value = value;
            this.row = row;
            this.column = column;
            this.identifier = identifier;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Symbol symbol = (Symbol) o;
            return Objects.equals(identifier, symbol.identifier);
        }

        @Override
        public int hashCode() {
            return Objects.hash(identifier);
        }

        public Character getValue() {
            return value;
        }

        public Integer getRow() {
            return row;
        }

        public Integer getColumn() {
            return column;
        }

        public String getIdentifier() {
            return identifier;
        }
    }
}
