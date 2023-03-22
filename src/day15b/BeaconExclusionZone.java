package day15b;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BeaconExclusionZone {

    private static final String INPUT_URL = "src/day15b/input.txt";
    private static final Set<Sensor> SENSORS = new HashSet<>();
    private static final Set<CoverZone> COVER_ZONES = new HashSet<>();
    private static final Set<Coordinate> CANDIDATE_POINTS = new HashSet<>();
    private static final Set<Coordinate> DISCARDED_POINTS = new HashSet<>();
    private static final int LOW_LIMIT = 0;
    private static final int HIGH_LIMIT = 20;

    public static void main(String[] args) throws FileNotFoundException {
        readInput();
        setCoverZones();
        evaluateZones();
        CANDIDATE_POINTS.removeAll(DISCARDED_POINTS);
        System.out.println("END!");
    }


    private static void readInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(INPUT_URL)).useDelimiter("\n");

        while (scanner.hasNext()) {
            String[] line = scanner.next().split(":");
            var sensor = parseSensor(line[LOW_LIMIT], line[1]);
            SENSORS.add(sensor);
        }
    }

    private static Sensor parseSensor(String sensorInput, String beaconInput) {
        Pattern pattern = Pattern.compile("x=(.*\\d+), y=(.*\\d+)");
        Matcher sensorMatcher = pattern.matcher(sensorInput);
        Matcher beaconMatcher = pattern.matcher(beaconInput);

        if (sensorMatcher.find() && beaconMatcher.find()) {
            var sensor = new Sensor(
                    Integer.parseInt(sensorMatcher.group(1)),
                    Integer.parseInt(sensorMatcher.group(2))
            );
            int xBeacon = Integer.parseInt(beaconMatcher.group(1));
            int yBeacon = Integer.parseInt(beaconMatcher.group(2));

            sensor.setMaxDistanceCovered(
                    Math.abs(sensor.getCoordinate().x() - xBeacon)
                            + Math.abs(sensor.getCoordinate().y() - yBeacon)
            );

            return sensor;
        } else
            throw new IllegalArgumentException("The inputs cannot be parsed");
    }

    private static void setCoverZones() {
        for (Sensor sensor : SENSORS) {
            COVER_ZONES.add(new CoverZone(sensor));
        }
    }

    private static void evaluateZones() {
        for (CoverZone coverZone: COVER_ZONES) {
            //Set the candidate points for that zone
            setCandidatePoints(coverZone);
        }

        for (CoverZone coverZone: COVER_ZONES) {
            verifyPointsBelongingToZone(coverZone);
        }
    }

    private static void verifyPointsBelongingToZone(CoverZone coverZone) {
        for (Coordinate entry: CANDIDATE_POINTS) {
            var result = coverZone.verifyPointBelongsToZone(entry);
            if (result) {
                DISCARDED_POINTS.add(entry);
            }
        }
    }

    private static void setCandidatePoints(CoverZone coverZone) {
        //Values of coordinates
        Coordinate left = coverZone.getLeftCoordinate();
        Coordinate right = coverZone.getRightCoordinate();
        Coordinate up = coverZone.getUpCoordinate();
        Coordinate down = coverZone.getDownCoordinate();

        //Set candidate points to left and up
        setCandidatePointsLeftAndUp(left, up);
        //Set candidate points to left and down
        setCandidatePointsLeftAndDown(left, down);
        //Set candidate points to right and up
        setCandidatePointsRightAndUp(right, up);
        //Set candidate points to right and down
        setCandidatePointsRightAndDown(right, down);
    }

    public static void setCandidatePointsLeftAndUp(Coordinate left, Coordinate up) {
        int j = left.y();
        for (int i = left.x() - 1; i <= up.x(); i++) {
            var coordinate = new Coordinate(i,j);
            if (!CANDIDATE_POINTS.contains(coordinate) &&
                    (LOW_LIMIT <= coordinate.x() && coordinate.x() <= HIGH_LIMIT) &&
                    (LOW_LIMIT <= coordinate.y() && coordinate.y() <= HIGH_LIMIT)
            ){
                CANDIDATE_POINTS.add(new Coordinate(i, j));
            }
            j--;

            System.out.println(CANDIDATE_POINTS.size());
        }
    }

    private static void setCandidatePointsLeftAndDown(Coordinate left, Coordinate down) {
        int j = left.y();
        for (int i = left.x() - 1; i <= down.x(); i++) {
            var coordinate = new Coordinate(i,j);
            if (!CANDIDATE_POINTS.contains(coordinate) &&
                    (LOW_LIMIT <= coordinate.x() && coordinate.x() <= HIGH_LIMIT) &&
                    (LOW_LIMIT <= coordinate.y() && coordinate.y() <= HIGH_LIMIT)
            ){
                CANDIDATE_POINTS.add(new Coordinate(i, j));
            }
            j++;
            System.out.println(CANDIDATE_POINTS.size());
        }
    }

    private static void setCandidatePointsRightAndUp(Coordinate right, Coordinate up) {
        int j = right.y();
        for (int i = right.x() + 1; i >= up.x(); i--) {
            var coordinate = new Coordinate(i,j);
            if (!CANDIDATE_POINTS.contains(coordinate) &&
                    (LOW_LIMIT <= coordinate.x() && coordinate.x() <= HIGH_LIMIT) &&
                    (LOW_LIMIT <= coordinate.y() && coordinate.y() <= HIGH_LIMIT)
            ){
                CANDIDATE_POINTS.add(new Coordinate(i, j));
            }
            j--;
            System.out.println(CANDIDATE_POINTS.size());
        }
    }

    private static void setCandidatePointsRightAndDown(Coordinate right, Coordinate down) {
        int j = right.y();
        for (int i = right.x() + 1; i >= down.x(); i--) {
            var coordinate = new Coordinate(i,j);
            if (!CANDIDATE_POINTS.contains(coordinate) &&
                    (LOW_LIMIT <= coordinate.x() && coordinate.x() <= HIGH_LIMIT) &&
                    (LOW_LIMIT <= coordinate.y() && coordinate.y() <= HIGH_LIMIT)
            ){
                CANDIDATE_POINTS.add(new Coordinate(i, j));
            }
            j++;
            System.out.println(CANDIDATE_POINTS.size());
        }
    }
}
