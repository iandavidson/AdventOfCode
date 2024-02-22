package org.example.advent.year2023.ten;

import lombok.Data;
import lombok.ToString;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class AnimalMaze {

    private static final String INPUT_PATH = "/Users/Ian/Documents/PersonalProjects/interviewprep/src/main/java/org/example/advent/year2023/ten/input.txt";
//    private static final String INPUT_PATH = "/Users/Ian/Documents/PersonalProjects/interviewprep/src/main/java/org/example/advent/year2023/ten/input-sample-1.txt";
//    private static final String INPUT_PATH = "/Users/Ian/Documents/PersonalProjects/interviewprep/src/main/java/org/example/advent/year2023/ten/input-sample-2.txt";

    public static final Map<Character, PipeType> SYMBOL_MAP = Map.of('|', PipeType.Pipe, '-', PipeType.Hyphen, 'L', PipeType.L, 'J', PipeType.J, '7', PipeType.Seven, 'F', PipeType.F, '.', PipeType.Period, 'S', PipeType.Start);

    public static int START_ROW = -1;
    public static int START_COL = -1;

    public static final Map<String, List<PipeType>> RELATIVE_START_MAP = new HashMap<>();

    static {
        {
            RELATIVE_START_MAP.put("UP", Arrays.asList(PipeType.F, PipeType.Pipe, PipeType.Seven));
            RELATIVE_START_MAP.put("DOWN", Arrays.asList(PipeType.J, PipeType.L, PipeType.Pipe));
            RELATIVE_START_MAP.put("LEFT", Arrays.asList(PipeType.Hyphen, PipeType.F, PipeType.L));
            RELATIVE_START_MAP.put("RIGHT", Arrays.asList(PipeType.Hyphen, PipeType.J, PipeType.Seven));
        }
    }


    private static List<String> readFile() {
        List<String> input = new ArrayList<>();
        try {
            File file = new File(INPUT_PATH);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                input.add(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        return input;
    }

    private static List<List<MazeElement>> processInput(List<String> inputLines) {
        List<List<MazeElement>> grid = new ArrayList<>();
        for (int i = 0; i < inputLines.size(); i++) {
            List<MazeElement> gridRow = new ArrayList<>();
            for (int j = 0; j < inputLines.get(0).length(); j++) {
                gridRow.add(new MazeElement(SYMBOL_MAP.get(inputLines.get(i).charAt(j)), i, j));

                if (inputLines.get(i).charAt(j) == 'S') {
                    START_ROW = i;
                    START_COL = j;
                }

            }
            grid.add(gridRow);
        }

        return grid;
    }

    private static int traverseGraph(List<List<MazeElement>> grid) {
        //find start
        Set<MazeElement> foundList = new HashSet<>();
        MazeElement start = grid.get(START_ROW).get(START_COL);

        Deque<MazeElement> queue = new LinkedList<>();
        queue.add(start);

        int distance = -1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            distance++;

            for (int i = 0; i < size; i++) {
                MazeElement temp = queue.pollFirst();
                foundList.add(temp);

                //find all valid, pipe directions from temp
                for (MazeElement neighbor : findValidNeighbors(grid, temp)) {
                    if (!foundList.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }

            }

        }

        return distance;
    }

    private static List<MazeElement> findValidNeighbors(List<List<MazeElement>> grid, MazeElement current) {
        List<MazeElement> neighbors = new ArrayList<>();
        int maxHeight = grid.size();
        int maxWidth = grid.get(0).size();


//        System.out.println(current);

        MazeElement up = inBounds(current.getRow() - 1, maxHeight - 1) ? grid.get(current.getRow() - 1).get(current.getCol()) : null;
        MazeElement down = inBounds(current.getRow() + 1, maxHeight - 1) ? grid.get(current.getRow() + 1).get(current.getCol()) : null;
        MazeElement left = inBounds(current.getCol() - 1, maxWidth - 1) ? grid.get(current.getRow()).get(current.getCol() - 1) : null;
        MazeElement right = inBounds(current.getCol() + 1, maxWidth - 1) ? grid.get(current.getRow()).get(current.getCol() + 1) : null;

        switch (current.type) {
            case Start:
                if (up != null && RELATIVE_START_MAP.get("UP").contains(up.getType())) {
                    neighbors.add(up);
                }

                if (down != null && RELATIVE_START_MAP.get("DOWN").contains(down.getType())) {
                    neighbors.add(down);
                }

                if (left != null && RELATIVE_START_MAP.get("LEFT").contains(left.getType())) {
                    neighbors.add(left);
                }

                if (right != null && RELATIVE_START_MAP.get("RIGHT").contains(right.getType())) {
                    neighbors.add(right);
                }
                // this sucks
                break;
            case F:
                neighbors.add(right);
                neighbors.add(down);
                break;
            case J:
                neighbors.add(up);
                neighbors.add(left);
                break;
            case L:
                neighbors.add(up);
                neighbors.add(right);
                break;
            case Pipe:
                neighbors.add(up);
                neighbors.add(down);
                break;
            case Seven:
                neighbors.add(left);
                neighbors.add(down);
                break;
            case Hyphen:
                neighbors.add(left);
                neighbors.add(right);
                break;
            case Period:
                throw new RuntimeException("whoops, f**ked up somewhere, shouldn't be processing >.<");
        }

        if (neighbors.size() != 2) {
            System.out.println("Not 2 neighbors found, issue....");
        }

        return neighbors;
    }

    private static boolean inBounds(int provided, int max) {
        return provided >= 0 && provided <= max;
    }

    public static long part1() {
        List<String> inputLines = readFile();
        List<List<MazeElement>> grid = processInput(inputLines);
        int distance = traverseGraph(grid);

        return distance;
    }

    private static List<MazeElement> traverseGraphPart2(List<List<MazeElement>> grid) {
        //find start
        Set<MazeElement> foundList = new HashSet<>();
        MazeElement start = grid.get(START_ROW).get(START_COL);

        List<MazeElement> mazeElementPath = new ArrayList<>();

        Deque<MazeElement> queue = new LinkedList<>();
        foundList.add(start);
        mazeElementPath.add(start);

        List<MazeElement> starts = findValidNeighbors(grid, start);
        queue.add(starts.get(0));
        foundList.add(starts.get(0));
        mazeElementPath.add(starts.get(0));

        int distance = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            distance++;

            for (int i = 0; i < size; i++) {
                MazeElement temp = queue.pollFirst();
                foundList.add(temp);

                //find all valid, pipe directions from temp
                for (MazeElement neighbor : findValidNeighbors(grid, temp)) {
                    if (!foundList.contains(neighbor)) {
                        queue.add(neighbor);
                        mazeElementPath.add(neighbor);
                    }
                }

            }

        }

        return mazeElementPath;
    }

    private static long countInsideGrid(List<MazeElement> gridWithPath){
        //shoelace alg:
        long area = 0;
        int j = gridWithPath.size() -1;

        for(int i = 0; i < gridWithPath.size(); i++){
            area += (long)  (gridWithPath.get(j).getRow() + gridWithPath.get(i).getRow()) * (gridWithPath.get(i).getCol() - gridWithPath.get(j).getCol());

            j = i;
        }

        area =  Math.abs(area / 2);

        //pick's theorem:
        return area - (gridWithPath.size()/2) +  1;
    }


    public static long part2(){
        List<String> inputLines = readFile();
        List<List<MazeElement>> grid = processInput(inputLines);
        List<MazeElement> gridWithPath = traverseGraphPart2(grid);

        return countInsideGrid(gridWithPath);
    }

    public static void main(String[] args) {
        long longest = part1();
        System.out.println("longest: " + longest);

        long containedArea = part2();
        System.out.println("contained area: " + containedArea);
    }


    @Data
    @ToString
    static class MazeElement {
        private PipeType type;
        private int row;
        private int col;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MazeElement that = (MazeElement) o;
            return row == that.row && col == that.col && type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, row, col);
        }

        public MazeElement(PipeType type, int row, int col) {
            this.type = type;
            this.row = row;
            this.col = col;
        }
    }

    public enum PipeType {
        /*
| is a vertical pipe connecting north and south.
- is a horizontal pipe connecting east and west.
L is a 90-degree bend connecting north and east.
J is a 90-degree bend connecting north and west.
7 is a 90-degree bend connecting south and west.
F is a 90-degree bend connecting south and east.
. is ground; there is no pipe in this tile.
S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
         */

        Pipe, Hyphen, L, J, Seven, F, Period, Start;
    }

}
