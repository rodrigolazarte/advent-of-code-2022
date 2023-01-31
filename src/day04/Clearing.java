package day04;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Clearing {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(calculateContainedSections(readInputData("src/day04/input.txt")));

        System.out.println(calculateOverlappedSections(readInputData("src/day04/input.txt")));
    }

    private static List<String> readInputData(String inputUrl) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(inputUrl)).useDelimiter("\n");
        List<String> inputData = new ArrayList<>();

        while (scanner.hasNext()) {
            inputData.add(scanner.next().trim());
        }

        return inputData;
    }

    private static int calculateContainedSections(List<String> sectionsAssignments) {
        int fullyContainedAssignments = 0;
        for (String sectionAssignment : sectionsAssignments) {
            if (isFullyContained(sectionAssignment)) {
                fullyContainedAssignments += 1;
            }
        }

        return fullyContainedAssignments;
    }

    private static int calculateOverlappedSections(List<String> sectionsAssignments) {
        int overlappedAssignments = 0;
        for (String sectionAssignment: sectionsAssignments) {
            if (isOverlapping(sectionAssignment)) {
                overlappedAssignments += 1;
            }
        }

        return overlappedAssignments;
    }

    private static boolean isFullyContained(String sectionAssignment) {
        String[] ranges = sectionAssignment.split(",");

        int[] firstRange = new int[2];
        int[] secondRange = new int[2];

        assignRanges(ranges[0], firstRange);
        assignRanges(ranges[1], secondRange);

        if (firstRange[0] >= secondRange[0] && firstRange[1] <= secondRange[1])
            return true;
        if (secondRange[0] >= firstRange[0] && secondRange[1] <= firstRange[1])
            return true;

        return false;
    }

    private static boolean isOverlapping(String sectionAssignment) {
        String[] ranges = sectionAssignment.split(",");

        int[] firstRange = new int[2];
        int[] secondRange = new int[2];

        assignRanges(ranges[0], firstRange);
        assignRanges(ranges[1], secondRange);

        if (firstRange[0] >= secondRange[0] && firstRange[0] <= secondRange[1])
            return true;
        if (firstRange[1] >= secondRange[0] && firstRange[1] <= secondRange[1])
            return true;

        if (secondRange[0] >= firstRange[0] && secondRange[0] <= firstRange[1])
            return true;
        if (secondRange[1] >= firstRange[0] && secondRange[1] <= firstRange[1])
            return true;

        return false;
    }

    private static void assignRanges(String stringRange, int[] range) {
        range[0] = Integer.parseInt(stringRange.split("-")[0]);
        range[1] = Integer.parseInt(stringRange.split("-")[1]);
    }
}
