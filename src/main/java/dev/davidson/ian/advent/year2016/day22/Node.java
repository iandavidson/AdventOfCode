package dev.davidson.ian.advent.year2016.day22;

public record Node(String filename, Coordinate coordinate, Integer size, Integer used, Integer available,
                   Integer percentInUse) {

    //"/dev/grid/node-x0-y0     93T   67T    26T   72%"

    public static Node newNode(String line) {
//        System.out.println(line);
        String[] parts = line.split("\\s+");
        String[] fileNameParts = parts[0].split("-");

        return new Node(
                parts[0],
                new Coordinate(
                        Integer.parseInt(fileNameParts[1].substring(1)),
                        Integer.parseInt(fileNameParts[2].substring(1))
                ),
                Integer.parseInt(parts[1].substring(0, parts[1].length() - 1)),
                Integer.parseInt(parts[2].substring(0, parts[2].length() - 1)),
                Integer.parseInt(parts[3].substring(0, parts[3].length() - 1)),
                Integer.parseInt(parts[4].substring(0, parts[4].length() - 1))
        );
    }

    public Integer x() {
        return coordinate.x();
    }

    public Integer y() {
        return coordinate.y();
    }

    public boolean canFit(final Node other) {
        if (used == 0 || this.equals(other)) {
            return false;
        } else return used <= other.available();
    }
}