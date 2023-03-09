package day15;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeaconExclusionZone {

    private static final Set<Sensor> SENSORS = new HashSet<>();
    private static final Set<Beacon> BEACONS = new HashSet<>();
    private static final Set<Sensor> CANDIDATE_SENSORS = new HashSet<>();
    private static final Set<Integer> COVERED_POSITIONS = new HashSet<>();
    private static final int TARGET_LINE = 2000000;
    private static final String INPUT_URL = "src/day15/input.txt";

    public static void main(String[] args) throws FileNotFoundException {
        readInput();
        searchCandidates();
        setCoveredPositions();
        removeBeaconsFromCoveredPositions();
        System.out.println("There are "
                + COVERED_POSITIONS.size()
                + " positions in line "
                + TARGET_LINE
                + " where a beacon cannot be present.");
    }

    private static void readInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(INPUT_URL)).useDelimiter("\n");

        while (scanner.hasNext()) {
            String[] line = scanner.next().split(":");
            var sensor = parseSensor(line[0]);
            var beacon = parseBeacon(line[1]);

            sensor.setClosestBeacon(beacon);

            SENSORS.add(sensor);
            BEACONS.add(beacon);
        }
    }

    private static Sensor parseSensor(String input) {
        Pattern pattern = Pattern.compile("x=(.*\\d+), y=(.*\\d+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return new Sensor(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2))
            );
        } else
            throw new IllegalArgumentException("The input cannot be parsed as a Sensor.\n" + input);
    }

    private static Beacon parseBeacon(String input) {
        Pattern pattern = Pattern.compile("x=(.*\\d+), y=(.*\\d+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return new Beacon(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2))
            );
        } else
            throw new IllegalArgumentException("The input cannot be parsed as a Beacon. \n" + input);
    }

    private static void searchCandidates() {
        for (Sensor sensor : SENSORS) {
            int y = sensor.getY();
            int maxDownIndex = y + sensor.getManhattanDistanceToBeacon();
            int maxUpIndex = y - sensor.getManhattanDistanceToBeacon();

            if (y < TARGET_LINE && maxDownIndex >= TARGET_LINE) {
                sensor.setDistanceToTargetLine(TARGET_LINE - y);
                CANDIDATE_SENSORS.add(sensor);
                continue;
            }

            if (y > TARGET_LINE && maxUpIndex <= TARGET_LINE) {
                sensor.setDistanceToTargetLine(y - TARGET_LINE);
                CANDIDATE_SENSORS.add(sensor);
                continue;
            }

            if (y == TARGET_LINE) {
                CANDIDATE_SENSORS.add(sensor);
            }
        }
    }

    private static void setCoveredPositions() {
        for (Sensor sensor : CANDIDATE_SENSORS) {
            int movesToX = Math.abs(sensor.getManhattanDistanceToBeacon() - sensor.getDistanceToTargetLine());
            int x = sensor.getX();
            COVERED_POSITIONS.add(x);

            for (int i = x; i <= x+movesToX; i++) {
                COVERED_POSITIONS.add(i);
            }

            for (int i = x; i >= x-movesToX; i--) {
                COVERED_POSITIONS.add(i);
            }
        }
    }

    private static void removeBeaconsFromCoveredPositions() {
        for (Beacon beacon: BEACONS) {
            if (beacon.y() == TARGET_LINE) {
                COVERED_POSITIONS.remove(beacon.x());
            }
        }
    }
}
