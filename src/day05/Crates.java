package day05;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Crates {

    private static final List<Stack<String>> listOfStacks = new ArrayList<>();
    private static final List<Integer[]> listOfMoves = new ArrayList<>();
    private static final int maxStacks = 9;
    private static final CraneModels model = CraneModels.CRATE_MOVER_9001;

    public static void main(String[] args) throws FileNotFoundException {
        List<Stack<String>> stacks = getInitialStacksConfiguration("src/day05/stackConfiguration.txt");

        getListOfMoves("src/day05/stackMoves.txt");

        performAllMoves();

        System.out.println(readAllTops());

    }

    private static List<Integer[]> getListOfMoves(String movesUrl) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(movesUrl)).useDelimiter("\n");

        while (scanner.hasNext()) {
            String[] numbersOfMoves = scanner.next().replaceAll("[^\\d]+", " ").split(" ");
            Integer[] moves = new Integer[]{
                    Integer.parseInt(numbersOfMoves[1]),
                    Integer.parseInt(numbersOfMoves[2]),
                    Integer.parseInt(numbersOfMoves[3])
            };
            listOfMoves.add(moves);
        }

        scanner.close();

        return listOfMoves;
    }

    private static String readAllTops() {
        StringBuilder allTops = new StringBuilder();
        for (Stack<String> stack : listOfStacks) {
            allTops.append(stack.peek());
        }

        return allTops.toString();
    }

    private static void performAllMoves() {
        for (Integer[] moves : listOfMoves) {
            moveCrates(moves);
        }
    }

    private static void moveCrates(Integer[] moves) {
        int quantity = moves[0];
        int fromStack = moves[1] - 1;
        int toStack = moves[2] - 1;

        switch (model) {
            case CRATE_MOVER_9000 -> {
                for (int i = 0; i < quantity; i++) {
                    String crateToMove = listOfStacks.get(fromStack).pop();
                    listOfStacks.get(toStack).push(crateToMove);
                }
            }
            case CRATE_MOVER_9001 -> {
                Stack<String> elementsToMove = new Stack<>();
                for (int i = 0; i < quantity; i++) {
                    elementsToMove.push(listOfStacks.get(fromStack).pop());
                }
                while (!elementsToMove.empty()) {
                    listOfStacks.get(toStack).push(elementsToMove.pop());
                }
            }
        }
    }

    private static List<Stack<String>> getInitialStacksConfiguration(String dataUrl) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(dataUrl)).useDelimiter("\n");
        List<String> rawData = new ArrayList<>();

        while (scanner.hasNext()) {
            rawData.add(scanner.next().trim());
        }
        scanner.close();

        rawData = formatStackData(rawData);

        for (int i = 0; i < maxStacks; i++) {
            listOfStacks.add(new Stack<>());
        }

        for (int i = rawData.size() - 1; i >= 0; i--) {
            String[] crates = rawData.get(i).split(" ");
            for (int j = 0; j < crates.length; j++) {
                if (!crates[j].equals("[0]"))
                    listOfStacks.get(j).push(crates[j]);
            }
        }

        return listOfStacks;
    }

    private static List<String> formatStackData(List<String> rawData) {
        List<String> formattedData = new ArrayList<>();
        for (String stack : rawData) {
            while (stack.contains("   ")) {
                stack = stack.replace("     ", " [0] ");
            }

            formattedData.add(stack);
        }

        return formattedData;
    }

    public enum CraneModels {
        CRATE_MOVER_9000,
        CRATE_MOVER_9001
    }
}
