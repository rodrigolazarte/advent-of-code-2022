package day15b;

import java.util.Objects;

public class CoverZone {

    private Coordinate upCoordinate;
    private Coordinate downCoordinate;
    private Coordinate leftCoordinate;
    private Coordinate rightCoordinate;
    private Sensor sensor;

    public CoverZone(Sensor sensor) {
        upCoordinate = new Coordinate(
                sensor.getCoordinate().x(),
                sensor.getCoordinate().y() - sensor.getMaxDistanceCovered()
        );

        downCoordinate = new Coordinate(
                sensor.getCoordinate().x(),
                sensor.getCoordinate().y() + sensor.getMaxDistanceCovered()
        );

        leftCoordinate = new Coordinate(
                sensor.getCoordinate().x() - sensor.getMaxDistanceCovered(),
                sensor.getCoordinate().y()
        );

        rightCoordinate = new Coordinate(
               sensor.getCoordinate().x() + sensor.getMaxDistanceCovered(),
               sensor.getCoordinate().y()
        );

        this.sensor = sensor;
    }

    public Coordinate getUpCoordinate() {
        return upCoordinate;
    }

    public void setUpCoordinate(Coordinate upCoordinate) {
        this.upCoordinate = upCoordinate;
    }

    public Coordinate getDownCoordinate() {
        return downCoordinate;
    }

    public void setDownCoordinate(Coordinate downCoordinate) {
        this.downCoordinate = downCoordinate;
    }

    public Coordinate getLeftCoordinate() {
        return leftCoordinate;
    }

    public void setLeftCoordinate(Coordinate leftCoordinate) {
        this.leftCoordinate = leftCoordinate;
    }

    public Coordinate getRightCoordinate() {
        return rightCoordinate;
    }

    public void setRightCoordinate(Coordinate rightCoordinate) {
        this.rightCoordinate = rightCoordinate;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoverZone coverZone = (CoverZone) o;
        return Objects.equals(upCoordinate, coverZone.upCoordinate) && Objects.equals(downCoordinate, coverZone.downCoordinate) && Objects.equals(leftCoordinate, coverZone.leftCoordinate) && Objects.equals(rightCoordinate, coverZone.rightCoordinate) && Objects.equals(sensor, coverZone.sensor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(upCoordinate, downCoordinate, leftCoordinate, rightCoordinate, sensor);
    }

    public boolean verifyPointBelongsToZone(Coordinate point) {
        int manhattanDistanceToSensor =
                Math.abs(point.x() - sensor.getCoordinate().x())
                + Math.abs(point.y() - sensor.getCoordinate().y());
        return manhattanDistanceToSensor <= sensor.getMaxDistanceCovered();
    }
}
