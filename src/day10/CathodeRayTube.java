package day10;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CathodeRayTube {
    private static int cycleNumber = 0;
    private static int value = 1;
    private static int signalStrength = 0;
    private static int spritePosition = 0;
    private static StringBuilder screenOutput = new StringBuilder();


    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputData = readInputData("src/day10/input.txt");
        for (String data: inputData) {
            if (data.contains("noop")) {
                noOp();
            }
            if (data.contains("addx")) {
                addX(Integer.parseInt(data.replace("addx ", "")));
            }
        }

        System.out.println("The final signal strength is: " + signalStrength);

        System.out.println(screenOutput);
    }

    private static List<String> readInputData(String inputUrl) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(inputUrl)).useDelimiter("\n");
        List<String> inputData = new ArrayList<>();

        while (scanner.hasNext()) {
            inputData.add(scanner.next().trim());
        }

        return inputData;
    }

    private static void noOp() {
        cycleNumber += 1;
        valueInCycle();
    }

    private static void addX(int newValue) {
        cycleNumber += 1;
        valueInCycle();
        cycleNumber += 1;
        valueInCycle();
        value += newValue;
    }

    private static void valueInCycle(){
        if ((cycleNumber - 20) % 40 == 0){
            signalStrength += value * cycleNumber;
            System.out.println("Cycle: " + cycleNumber + ". Value: " + value * cycleNumber);
        }
        draw(cycleNumber);
    }

    private static void draw(int cn) {
        spritePosition = value-1;

        if (cn > 40) {
            int factor = cn / 40;
            cn = cn - (40 * factor);
        }

        if (cn-1 >= spritePosition && cn-1 <= spritePosition + 2) {
            screenOutput.append("#");
        } else screenOutput.append(".");

        if (cn % 40 == 0) {
            screenOutput.append("\n");
        }
    }
}
