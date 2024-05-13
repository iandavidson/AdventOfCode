package dev.davidson.ian.advent.year2022.day15;

public record Sensor(Coordinate location, int distance) {

    public static Sensor newSensor(Coordinate location, Coordinate nearestBeacon){
        int dX = Math.max(location.x(), nearestBeacon.x()) - Math.min(location.x(), nearestBeacon.x());
        int dY = Math.max(location.y(), nearestBeacon.y()) - Math.min(location.y(), nearestBeacon.y());
        return new Sensor(location, dX + dY);
    }

    public boolean inBounds(int x, int y){
        return Math.abs(x - location.x()) + Math.abs(y - location().y()) <= distance;
    }
}
