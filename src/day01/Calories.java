package day01;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calories {

    public static void main(String[] args) throws FileNotFoundException {
        //Part One
        List<Integer> caloriesPerDeer = caloriesPerDeer();
        Integer max = caloriesPerDeer.stream().max(Integer::compare).get();
        System.out.println(max);

        //Part Two
        int caloriesTotal = max;
        caloriesPerDeer.remove(max);
        for (int i = 0; i < 2; i++) {
            Integer nextMax = caloriesPerDeer.stream().max(Integer::compare).get();
            caloriesPerDeer.remove(nextMax);
            caloriesTotal += nextMax;
        }

        System.out.println(caloriesTotal);

    }

    public static List<Integer> caloriesPerDeer() throws FileNotFoundException {
        List<Integer> caloriesPerDeer = new ArrayList<>();
        Scanner scanner = new Scanner(new FileReader("src/day01/input.txt")).useDelimiter("\n");
        String str;
        int partialCalories = 0;

        while (scanner.hasNext()) {
            str = scanner.next();
            if (!str.equals(""))
                partialCalories += Integer.parseInt(str);
            else {
                caloriesPerDeer.add(partialCalories);
                partialCalories = 0;
            }
        }

        return caloriesPerDeer;
    }
}
