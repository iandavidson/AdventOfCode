package org.example.advent.year2023.twentyfour;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class HailCollision {
    private static final MathContext CONTEXT = MathContext.DECIMAL128;
    private static Long PART_1_PLANE_MIN = 200000000000000L;
    private static Long PART_1_PLANE_MAX = 400000000000000L;
    private static final String SAMPLE_INPUT_PATH = "adventOfCode/day24/input-sample.txt";
    private static final String INPUT_PATH = "adventOfCode/day24/input.txt";

    public static void main(String[] args) {
        HailCollision hailCollision = new HailCollision();
        System.out.println("part1: " + hailCollision.part1());
        System.out.println("part2: " + hailCollision.part2());
    }


    public Long part1() {
        List<HailTrajectory> hailTrajectories = processInput();
        return countPathsCrossed(hailTrajectories);
    }

    private List<HailTrajectory> processInput() {

        List<String> inputs = new ArrayList<>();
        try {
            ClassLoader classLoader = HailCollision.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(INPUT_PATH)).getFile());
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                inputs.add(myReader.nextLine());
            }
        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            throw new IllegalStateException();
        }

        List<HailTrajectory> hailTrajectories = new ArrayList<>();

        for (String inputLine : inputs) {
            String[] chunks = inputLine.split("@");

            String[] positions = chunks[0].split(",");
            String[] deltas = chunks[1].split(",");

            hailTrajectories.add(
                    HailTrajectory.builder()
                            .x(Long.parseLong(positions[0].trim()))
                            .y(Long.parseLong(positions[1].trim()))
                            .z(Long.parseLong(positions[2].trim()))
                            .deltaX(Long.parseLong(deltas[0].trim()))
                            .deltaY(Long.parseLong(deltas[1].trim()))
                            .deltaZ(Long.parseLong(deltas[2].trim()))
                            .build()
            );
        }

        return hailTrajectories;
    }

    private Long countPathsCrossed(List<HailTrajectory> trajectories) {
        Long count = 0L;

        for (int i = 0; i < trajectories.size(); i++) {
            HailTrajectory first = trajectories.get(i);
            for (int j = i + 1; j < trajectories.size(); j++) {
                HailTrajectory second = trajectories.get(j);

                Coordinate coordinate = first.crossesAt(second);

                boolean xInBounds = coordinate.x() >= PART_1_PLANE_MIN && coordinate.x() <= PART_1_PLANE_MAX;
                boolean yInBounds = coordinate.y() >= PART_1_PLANE_MIN && coordinate.y() <= PART_1_PLANE_MAX;

                if ((first.getDeltaX() > 0 && coordinate.x() < first.getX()) || (first.getDeltaX() < 0 && coordinate.x() > first.getX())
                        || (first.getDeltaY() > 0 && coordinate.y() < first.getY()) || (first.getDeltaY() < 0 && coordinate.y() > first.getY())) {
                    // Hailstones crossed in the past for hailstone A, throw away
                } else if ((second.getDeltaX() > 0 && coordinate.x() < second.getX()) || (second.getDeltaX() < 0 && coordinate.x() > second.getX())
                        || (second.getDeltaY() > 0 && coordinate.y() < second.getY()) || (second.getDeltaY() < 0 && coordinate.y() > second.getY())) {
                    // Hailstones crossed in the past for hailstone B, throw away
                } else if (xInBounds && yInBounds) {
                    count++;
                } else {
                    // Hailstones' paths will cross outside the test area, throw away
                }
            }
        }
        return count;
    }

    public Long part2() {
        List<HailTrajectory> hailTrajectories = processInput();

        //throw away:

        double coefficients[][] = new double[4][4];
        double rhs[] = new double[4];

        // Get X,Y,VX,VY
        for (int i = 0; i < 4; i++) {
            HailTrajectory l1 = hailTrajectories.get(i);
            HailTrajectory l2 = hailTrajectories.get(i + 1);
            coefficients[i][0] = l2.getDeltaY() - l1.getDeltaY();
            coefficients[i][1] = l1.getDeltaX() - l2.getDeltaX();
            coefficients[i][2] = l1.getY() - l2.getY();
            coefficients[i][3] = l2.getX() - l1.getX();
            rhs[i] = -l1.getX() * l1.getDeltaY() + l1.getY() * l1.getDeltaX() + l2.getX() * l2.getDeltaY() - l2.getY() * l2.getDeltaX();
        }

        gaussianElimination(coefficients, rhs);

        long x = Math.round(rhs[0]);
        long y = Math.round(rhs[1]);
        long vx = Math.round(rhs[2]);
        long vy = Math.round(rhs[3]);

        // Get X,VZ
        coefficients = new double[2][2];
        rhs = new double[2];
        for (int i = 0; i < 2; i++) {
            HailTrajectory l1 = hailTrajectories.get(i);
            HailTrajectory l2 = hailTrajectories.get(i + 1);
            coefficients[i][0] = l1.getDeltaX() - l2.getDeltaX();
            coefficients[i][1] = l2.getX() - l1.getX();
            rhs[i] = -l1.getX() * l1.getDeltaZ() + l1.getZ() * l1.getDeltaX() + l2.getX() * l2.getDeltaZ() - l2.getZ() * l2.getDeltaX()
                    - ((l2.getDeltaZ() - l1.getDeltaZ()) * x) - ((l1.getZ() - l2.getZ()) * vx);
        }

        gaussianElimination(coefficients, rhs);

        long z = Math.round(rhs[0]);
        long vz = Math.round(rhs[1]);

        HailTrajectory rock = new HailTrajectory(x, y, z, vx, vy, vz);

        return rock.getX() + rock.getY() + rock.getZ();
    }

    private void gaussianElimination(double[][] coefficients, double rhs[]) {
        int nrVariables = coefficients.length;
        for (int i = 0; i < nrVariables; i++) {
            // Select pivot
            double pivot = coefficients[i][i];
            // Normalize row i
            for (int j = 0; j < nrVariables; j++) {
                coefficients[i][j] = coefficients[i][j] / pivot;
            }
            rhs[i] = rhs[i] / pivot;
            // Sweep using row i
            for (int k = 0; k < nrVariables; k++) {
                if (k != i) {
                    double factor = coefficients[k][i];
                    for (int j = 0; j < nrVariables; j++) {
                        coefficients[k][j] = coefficients[k][j] - factor * coefficients[i][j];
                    }
                    rhs[k] = rhs[k] - factor * rhs[i];
                }
            }
        }

    }
}

/*
part 1 intuition:
I'm sure we can discount some scenarios by determining the following, more efficiently than computing where paths intersect:
- are they parallel? -> yes, throw away case
- based on comparing position and delta for x,y on both A & B; will they intersect at some point in the future?


EG 1:
    A: (0, 3) Delta(2, 0)
    B: (5, 0) Delta(0, 2)

    A has a positive X delta;
    in order to determine if B will pass through A[y] : A[y] <= B[y] + B[dY]*B[cY]
    in order to determine if B will pass through A[x] : A[x] <= B[x] + B[dX]*B[cX]


 */
