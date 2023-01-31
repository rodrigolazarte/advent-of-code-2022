package day06;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SignalMarker {

    public static void main(String[] args) throws FileNotFoundException {
        showMarkerPosition(getInput("src/day06/input.txt"));
    }

    private static char[] getInput(String dataUrl) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(dataUrl));
        String rawData = "";
        while (scanner.hasNext()){
            rawData = scanner.next();
        }

        return rawData.toCharArray();
    }

    private static void showMarkerPosition(char[] inputSignal) {
        int markerPosition = 0;
        int messageLength = 13;

        EVALUATE_MARKER: while (markerPosition == 0){
            EVALUATE_SIGNAL: for (int i = 0; i < inputSignal.length-3; i++){
                Set<Character> markerGroup = new HashSet<>();
                markerGroup.add(inputSignal[i]);
                for (int j = 1; j <= messageLength; j++){
                    if (!markerGroup.add(inputSignal[i+j])) continue EVALUATE_SIGNAL;
                }
                
                markerPosition = i + messageLength + 1;
                continue EVALUATE_MARKER;

            }
        }

        System.out.println(markerPosition);
    }
}
