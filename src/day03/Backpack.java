package day03;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Backpack {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(getSumOfPriorities(readBackpacksContents()));

        System.out.println(getSumOfPrioritiesByGroup(readBackpacksContentsByGroup()));
    }

    private static List<String> readBackpacksContentsByGroup() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader("src/day03/input.txt")).useDelimiter("\n");
        List<String> backpacksContents = new ArrayList<>();

        while (scanner.hasNext()) {
            backpacksContents.add(scanner.next().trim());
        }

        return backpacksContents;
    }

    private static List<String> readBackpacksContents() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader("src/day03/input.txt")).useDelimiter("\n");
        List<String> backpacksContents = new ArrayList<>();

        while (scanner.hasNext()) {
            backpacksContents.add(scanner.next().trim());
        }

        return backpacksContents;
    }

    private static int getSumOfPriorities(List<String> backpacksContents) {
        int sumOfPriorities = 0;

        for (String backpackContent : backpacksContents) {
            Set<Character> firstCompartment = convertToCharSet(
                    backpackContent.substring(0, backpackContent.length() / 2));
            Set<Character> secondCompartment = convertToCharSet(
                    backpackContent.substring(backpackContent.length() / 2));

            for (char element : secondCompartment) {
                if (!firstCompartment.add(element)) {
//                    System.out.println("" + element + " " + calculatePriorityOfElement(element));
                    sumOfPriorities += calculatePriorityOfElement(element);
                }
            }
        }

        return sumOfPriorities;
    }

    private static int getSumOfPrioritiesByGroup(List<String> backpackContents){
        int sumOfPriorities = 0;

        for (int i=0; i < backpackContents.size(); i += 3) {

            Set<Character> firstBackpack = convertToCharSet(backpackContents.get(i));
            Set<Character> secondBackpack = convertToCharSet(backpackContents.get(i+1));
            Set<Character> thirdBackpack = convertToCharSet(backpackContents.get(i+2));

            Set<Character> repeatedChars = new HashSet<>();
            char repeated;

            for (char element: secondBackpack){
                if (!firstBackpack.add(element))
                    repeatedChars.add(element);
            }

            for (char element: repeatedChars) {
                if (!thirdBackpack.add(element)){
                    repeated = element;
                    sumOfPriorities += calculatePriorityOfElement(repeated);
                }
            }
        }

        return sumOfPriorities;
    }

    private static int calculatePriorityOfElement(char element) {
        if (element > 96)
            return element - 96;
        else
            return element - 38;
    }

    private static Set<Character> convertToCharSet(String stringToSet){
        Set<Character> characterSet = new HashSet<>();
        char[] charArray = stringToSet.toCharArray();

        for (Character character: charArray) {
            characterSet.add(character);
        }

        return characterSet;
    }
}
