package day02;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class RockPaperScissors {

    public static void main(String[] args) throws FileNotFoundException {
        List<String> matches = matches();
        int totalScore = calculateScore(matches);
        System.out.println(totalScore);
    }

    private static List<String> matches() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader("src/day02/input.txt")).useDelimiter("\n");
        List<String> matchesArray = new ArrayList<>();

        while (scanner.hasNext()) {
            matchesArray.add(scanner.next().trim());
        }

        return matchesArray;
    }

    private static int calculateScore(List<String> matches) {
        int total = 0;
        for (String match: matches){
            total += calculateMatch(match);
        }

        return total;
    }

    private static int calculateMatch(String match) {
        Map<String, Integer> rules = new HashMap<>();
        rules.put("A X", 4); //(3 for draw + 1 for rock)
        rules.put("A Y", 8); //(6 for win + 2 for paper)
        rules.put("A Z", 3); //(0 for lose + 3 for scissor)
        rules.put("B X", 1);
        rules.put("B Y", 5);
        rules.put("B Z", 9);
        rules.put("C X", 7);
        rules.put("C Y", 2);
        rules.put("C Z", 6);

        return rules.get(match);

    }
}
