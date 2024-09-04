package dev.davidson.ian.advent.year2016.day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GridComputing {

    private static final Integer GOAL_Y = 0;
    private static final String INPUT_PATH = "adventOfCode/2016/day22/input.txt";

    public static void main(String [] args){
        GridComputing gridComputing = new GridComputing();
        List<Node> input = readFile(INPUT_PATH);
        log.info("Part1: {}", gridComputing.part1(input));
        log.info("Part2: {}", gridComputing.part2(input));
    }

    public Long part1(final List<Node> nodes){
        long count = 0;

        int n = nodes.size();
        for(int i =0  ; i < n-1; i ++){
            for(int j = i + 1; j < n; j++){
                if(nodes.get(i).canFit(nodes.get(j))){
                    count++;
                }

                if(nodes.get(j).canFit(nodes.get(i))){
                    count++;
                }
            }
        }
        return count;
    }

    public Long part2(final List<Node> nodeList){
        int maxX = nodeList.stream().max(Comparator.comparing(Node::x)).get().x();
        int maxY = nodeList.stream().max(Comparator.comparing(Node::y)).get().y();

        Node[][] nodes = new Node[maxX+1][maxY+1];
        Node wStart = null;
        Node hole = null;

        for(Node node: nodeList){
            nodes[node.x()][node.y()] = node;
        }


        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[x].length; y++) {
                Node n = nodes[x][y];
                if (x == 0 && y == 0){
                    System.out.print("S");
                }else if (x == maxX && y == 0) {
                    System.out.print("G");
                }else if (n.used() == 0) {
                    hole = n;
                    System.out.print("_");
                } else if (n.size() > 250) {
                    if (wStart == null) {
                        wStart = nodes[x - 1][y];
                    }
                    System.out.print("#");
                } else
                    System.out.print(".");
            }
            System.out.println();
        }

        int result = Math.abs(hole.x() - wStart.x()) + Math.abs(hole.y() - wStart.y());
        result += Math.abs(wStart.x() - maxX) + wStart.y();
        return (long) result + 5L * (maxX - 1);
    }

    private static List<Node> readFile(final String filePath){
        List<Node> nodes = new ArrayList<>();

        ClassLoader cl = GridComputing.class.getClassLoader();
        File file = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
        try{
            Scanner scanner = new Scanner(file);
            //top two lines are headers
            scanner.nextLine();
            scanner.nextLine();

            while(scanner.hasNextLine()){
               nodes.add(Node.newNode(scanner.nextLine()));
            }
        }catch(FileNotFoundException e){
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return nodes;
    }
}
