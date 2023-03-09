package day15b;

import java.util.Objects;

public class Sensor {

    private Coordinate coordinate;
    private int maxDistanceCovered;

    public Sensor() {
    }

    public Sensor(int x, int y) {
        this.coordinate = new Coordinate(x, y);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getMaxDistanceCovered() {
        return maxDistanceCovered;
    }

    public void setMaxDistanceCovered(int maxDistanceCovered) {
        this.maxDistanceCovered = maxDistanceCovered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return maxDistanceCovered == sensor.maxDistanceCovered && Objects.equals(coordinate, sensor.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate, maxDistanceCovered);
    }
}
