package day15;

import java.util.Objects;

public class Sensor {
    private int x;
    private int y;
    private Beacon closestBeacon;
    private int manhattanDistanceToBeacon;

    private int distanceToTargetLine;

    public Sensor() {
    }

    public Sensor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Beacon getClosestBeacon() {
        return closestBeacon;
    }

    public void setClosestBeacon(Beacon closestBeacon) {
        this.closestBeacon = closestBeacon;
        this.manhattanDistanceToBeacon =
                Math.abs(x - closestBeacon.x()) + Math.abs(y - closestBeacon.y());
    }

    public int getManhattanDistanceToBeacon() {
        return manhattanDistanceToBeacon;
    }

    public void setManhattanDistanceToBeacon(int manhattanDistanceToBeacon) {
        this.manhattanDistanceToBeacon = manhattanDistanceToBeacon;
    }

    public int getDistanceToTargetLine() {
        return distanceToTargetLine;
    }

    public void setDistanceToTargetLine(int distanceToTargetLine) {
        this.distanceToTargetLine = distanceToTargetLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return x == sensor.x && y == sensor.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
