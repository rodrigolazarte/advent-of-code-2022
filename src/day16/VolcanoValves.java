package day16;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VolcanoValves {

    private static final String INPUT_URL = "src/day16/inputExample.txt";
    private static final Map<String, Valve> VALVES = new HashMap<>();
    private static final Map<Long, List<Valve>> PATHS = new HashMap<>();
    private static long index = 0;

    public static void main(String[] args) throws FileNotFoundException {
        readInput();
        Valve firstValve = VALVES.get("AA");
        setPath(firstValve);
    }

    private static void readInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(INPUT_URL)).useDelimiter("\n");

        while (scanner.hasNext()) {
            var valve = parseToValve(scanner.next());
            VALVES.put(valve.getName(), valve);
        }

        scanner = new Scanner(new FileReader(INPUT_URL)).useDelimiter("\n");
        while (scanner.hasNext()) {
            setValvePaths(scanner.next());
        }
    }

    private static Valve parseToValve(String input) {
        Pattern valvePattern = Pattern.compile("^Valve (\\w{2}) has flow rate=(\\d{1,2});*");
        Matcher matcher = valvePattern.matcher(input);

        if (matcher.find()) {
            String name = matcher.group(1);
            long flowRate = Long.parseLong(matcher.group(2));

            return new Valve(name, flowRate);
        } else
            throw new IllegalArgumentException("The input cannot be parsed");
    }

    private static void setValvePaths(String input) {
        Pattern valvePattern = Pattern.compile("^Valve (\\w{2}) has flow rate=(\\d{1,2}); " +
                "tunnel.{0,1} lead.{0,1} to valve.{0,1} (\\w{2}.*)");
        Matcher matcher = valvePattern.matcher(input);

        if (matcher.find()) {
            Valve sourceValve = VALVES.get(matcher.group(1));
            String[] valves = matcher.group(3).split(",");
            for (String valve: valves) {
                sourceValve.getConnectedValves().add(VALVES.get(valve.trim()));
            }
        }
    }

    private static void setPath(Valve valve) {
        for (Valve v: valve.getConnectedValves()) {
            List<Valve> list = new ArrayList<>();
            if (!v.isOpen()) {
                if (v.getFlowRate() > 0) {
                    v.setOpen(true);
                    setPath(v);
                    list.add(v);
                }
            }
            PATHS.put(index, list);
        }
    }
}
