package day14;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

public class SandB {
    private static final String INPUT_URL = "src/day14/input.txt";
    private static final Map<Coordinate, String> OCCUPIED_SLOTS = new HashMap<>();
    private static final int INITIAL_DROP_POINT = 500;
    private static int sandQuantity = 0;
    private static boolean initialPointReached = false;
    private static int minColumn;
    private static int maxColumn;
    private static int maxRow;

    public static void main(String[] args) throws FileNotFoundException {
        readInput(INPUT_URL);
        getLimits();
        setFloor();
        startSandDropping();
        printMap();
    }

    private static void readInput(String inputUrl) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(inputUrl)).useDelimiter(Pattern.compile("\n"));
        ArrayList<String[]> coordinates = new ArrayList<>();
        while (scanner.hasNext()) {
            coordinates.add(scanner.next().split(" -> "));
        }

        for (String[] row : coordinates) {
            int currentRow = 0;
            int currentColumn = 0;
            for (String coordinate : row) {
                int coordinateColumn = Integer.parseInt(coordinate.split(",")[0]);
                int coordinateRow = Integer.parseInt(coordinate.split(",")[1]);

                if (currentRow == 0 && currentColumn == 0) {
                    OCCUPIED_SLOTS.put(new Coordinate(coordinateRow, coordinateColumn), "#");
                    currentRow = coordinateRow;
                    currentColumn = coordinateColumn;
                    continue;
                }

                if (currentRow == coordinateRow) {
                    int minor = Math.min(currentColumn, coordinateColumn);
                    int difference = Math.abs(currentColumn - coordinateColumn);
                    for (int i = minor; i <= minor + difference; i++) {
                        OCCUPIED_SLOTS.put(new Coordinate(currentRow, i), "#");
                    }
                    currentColumn = coordinateColumn;
                    continue;
                }

                if (currentColumn == coordinateColumn) {
                    int minor = Math.min(currentRow, coordinateRow);
                    int difference = Math.abs(currentRow - coordinateRow);
                    for (int i = minor; i <= minor + difference; i++) {
                        OCCUPIED_SLOTS.put(new Coordinate(i, currentColumn), "#");
                    }
                    currentRow = coordinateRow;
                }
            }
        }
    }

    private static void printMap() {
        String[][] map = new String[maxRow + 1][maxColumn - minColumn + 1];
        for (Map.Entry<Coordinate, String> entry : OCCUPIED_SLOTS.entrySet()) {
            int row = entry.getKey().row();
            int column = entry.getKey().column() - minColumn;

            map[row][column] = entry.getValue();
        }

        for (String[] row : map) {
            for (String column : row) {
                if (column != null) {
                    System.out.print(column);
                } else
                    System.out.print(".");
            }
            System.out.print("\n");
        }

        System.out.println("Total sand grains dropped: " + sandQuantity);
    }

    private static void getLimits() {
        Queue<Integer> verticalSlots = new PriorityQueue<>();
        for (Map.Entry<Coordinate, String> entry : OCCUPIED_SLOTS.entrySet()) {
            verticalSlots.offer(entry.getKey().row());
        }

        while (!verticalSlots.isEmpty()) {
            maxRow = verticalSlots.poll();
        }

        maxRow += 2;
        minColumn = INITIAL_DROP_POINT - maxRow;
        maxColumn = INITIAL_DROP_POINT + maxRow;


        System.out.println("Min horizontal: " + minColumn);
        System.out.println("Max horizonal: " + maxColumn);
        System.out.println("Max vertical: " + maxRow);
    }

    private static void setFloor() {
        for (int i=minColumn; i <= maxColumn; i++) {
            OCCUPIED_SLOTS.put(new Coordinate(maxRow, i), "#");
        }
    }

    private static void dropSand() {
        var nearestObstacle = getNearestObstacle();
        var currentGrainPosition = new Coordinate(nearestObstacle.row() - 1, nearestObstacle.column());

        moveSand(currentGrainPosition);

    }

    private static void startSandDropping() {
        while (!initialPointReached)
            dropSand();
    }

    private static void moveSand(Coordinate currentGrainPosition) {
        var nextDownCoordinate = new Coordinate(currentGrainPosition.row() + 1, currentGrainPosition.column());
        var nextLeftCoordinate = new Coordinate(currentGrainPosition.row() + 1, currentGrainPosition.column() - 1);
        var nextRightCoordinate = new Coordinate(currentGrainPosition.row() + 1, currentGrainPosition.column() + 1);

        if (!OCCUPIED_SLOTS.containsKey(nextDownCoordinate)) {
            moveSand(nextDownCoordinate);
        }

        if (!OCCUPIED_SLOTS.containsKey(nextLeftCoordinate)) {
            moveSand(nextLeftCoordinate);
            return;
        }

        if (!OCCUPIED_SLOTS.containsKey(nextRightCoordinate)) {
            moveSand(nextRightCoordinate);
            return;
        }

        OCCUPIED_SLOTS.put(currentGrainPosition, "o");
        sandQuantity += 1;

        if (OCCUPIED_SLOTS.containsKey(new Coordinate(0, INITIAL_DROP_POINT)))
            initialPointReached = true;
    }

    private static Coordinate getNearestObstacle() {
        var nearestObstacle = new Coordinate(maxRow, INITIAL_DROP_POINT);
        for (Map.Entry<Coordinate, String> entry : OCCUPIED_SLOTS.entrySet()) {
            if (entry.getKey().column() == nearestObstacle.column()) {
                if (entry.getKey().row() < nearestObstacle.row()) {
                    nearestObstacle = entry.getKey();
                }
            }
        }
        return nearestObstacle;
    }
}
