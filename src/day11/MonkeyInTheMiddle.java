package day11;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class MonkeyInTheMiddle {

    private static List<Monkey> monkeysList;
    private static long lcm = 1;
    private static final int ROUNDS = 10000;
    private static final String DATA_URL = "src/day11/input.txt";
    private static final boolean LOW_WORRY = false;


    public static void main(String[] args) throws FileNotFoundException {
        monkeysList = setMonkeysList(DATA_URL);
        setLeastCommonMultiple();
        performRounds(ROUNDS);
        System.out.println(getLevelOfMonkeyBusiness());
    }

    private static void setLeastCommonMultiple() {
        for (var monkey: monkeysList) {
            lcm *= monkey.getDivisible();
        }
    }

    public static List<Monkey> setMonkeysList(String inputUrl) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(inputUrl)).useDelimiter(Pattern.compile("\n\n"));
        List<String> inputData = new ArrayList<>();
        List<Monkey> monkeys = new ArrayList<>();
        while (scanner.hasNext()) {
            inputData.add(scanner.next().trim());
        }

        for (String monkeyInfo : inputData) {
            monkeys.add(createMonkey(monkeyInfo));
        }

        return monkeys;
    }

    private static Monkey createMonkey(String monkeyInfo) {
        String[] startingItems = new String[0];
        String operation = "";
        int divisor = 0;
        int destinationMonkeyIfTrue = 0;
        int destinationMonkeyIfFalse = 0;

        Monkey monkey = new Monkey();
        String[] monkeyAttributes = monkeyInfo.split("\n");

        //Evaluate every line of the monkey info, and set variables with monkey attributes
        for (String attribute : monkeyAttributes) {
            if (attribute.contains("Starting items: ")) {
                startingItems = attribute
                        .trim()
                        .replace("Starting items: ", "")
                        .split(",");
            }

            if (attribute.contains("Operation: ")) {
                operation = attribute
                        .trim()
                        .replace("Operation: ", "");
            }
            if (attribute.contains("Test: ")) {
                divisor = Integer.parseInt(
                        attribute
                                .trim()
                                .replace("Test: divisible by ", "")
                );
            }
            if (attribute.contains("If true: ")) {
                destinationMonkeyIfTrue = Integer.parseInt(
                        attribute
                                .trim()
                                .replace("If true: throw to monkey ", "")
                );
            }
            if (attribute.contains("If false: ")) {
                destinationMonkeyIfFalse = Integer.parseInt(
                        attribute
                                .trim()
                                .replace("If false: throw to monkey ", "")
                );
            }
        }

        monkey.setStartingItems(convertStringArrayToBigDecimal(startingItems));
        monkey.setOperation(parseOperation(operation));
        monkey.setTest(parseTest(divisor));
        monkey.setDestinationMonkeyIfTrue(destinationMonkeyIfTrue);
        monkey.setDestinationMonkeyIfFalse(destinationMonkeyIfFalse);
        monkey.setDivisible(divisor);

        return monkey;
    }

    private static Function<BigInteger, BigInteger> parseOperation(String operation) {
        operation = operation.replace("new = ", "");
        BigInteger operatorNumber;

        // Evaluates if the operation is between old value and a new number, or if it's only using
        // the old value
        if (operation.matches("\\D+\\d+")) {
            operatorNumber = BigInteger
                    .valueOf(
                            Long.parseLong(operation.replaceAll("[^0-9]", ""))
                    );
            if (operation.contains("*")) {
                return (x) -> x.multiply(operatorNumber);
            }

            return (x) -> x.add(operatorNumber);
        } else {
            if (operation.contains("*")) {
                return (x) -> x.multiply(x);
            }

            return (x) -> x.add(x);
        }
    }

    private static Predicate<BigInteger> parseTest(int divisor) {
        return x -> x.mod(BigInteger.valueOf(divisor)).equals(BigInteger.ZERO);
    }

    /**
     * Receives an array of string numbers, and returns a {@link Queue} of
     * {@link java.math.BigInteger} numbers.
     *
     * @param array
     * @return
     */
    private static Queue<BigInteger> convertStringArrayToBigDecimal(String[] array) {
        Queue<BigInteger> bigIntegers = new ArrayDeque<>();
        for (String value : array) {
            bigIntegers.add(BigInteger.valueOf(Long.parseLong(value.trim())));
        }

        return bigIntegers;
    }

    /**
     * Method that takes a monkey instance and performs the following actions over each item
     * of it's startingItems queue:
     * <ol>
     *     <li>Execute an operation over the item to increase it's worry level.</li>
     *     <li>Depending on the value of {@link #LOW_WORRY}, divide the worry level by 3 or not.</li>
     *     <li>Test the divisibility of the worry level.</li>
     *     <li>Throw the item to other monkey depending on the test result.</li>
     * </ol>
     * @param monkey Instance of class {@link Monkey}
     */
    private static void performTurn(Monkey monkey) {
        //Get reference to startingItems object of the monkey
        Queue<BigInteger> startingItems = monkey.getStartingItems();

        //Iterate over each item of the monkey
        while (!startingItems.isEmpty()) {
            BigInteger item = startingItems.poll();

            //Execute operation on an item
            item = LOW_WORRY ?
                    monkey.executeOperation(item)
                            .divide(BigInteger.valueOf(3))
                            .mod(BigInteger.valueOf(lcm)) :
                    monkey.executeOperation(item)
                            .mod(BigInteger.valueOf(lcm));

            //Execute divisibility test on the item
            boolean testResult = monkey.executeTest(item);

            //Throw item to a new monkey
            if (testResult) {
                addItemToMonkey(monkey.getDestinationMonkeyIfTrue(), item);
            } else {
                addItemToMonkey(monkey.getDestinationMonkeyIfFalse(), item);
            }

            //Increase monkey's inspected items by one
            monkey.addInspectedItem();
        }
    }

    private static void performRounds(int rounds) {
        for (int i = 0; i < rounds; i++) {
            monkeysList.forEach(MonkeyInTheMiddle::performTurn);
            System.out.println("Rounds: " + i);
        }
    }

    private static void addItemToMonkey(int monkeyNumber, BigInteger item) {
        var startingItems = monkeysList.get(monkeyNumber).getStartingItems();
        startingItems.add(item);
    }

    private static long getLevelOfMonkeyBusiness() {
        monkeysList.sort(Comparator
                .comparingLong(Monkey::getInspectedItems)
                .reversed()
        );

        return monkeysList.get(0).getInspectedItems() * monkeysList.get(1).getInspectedItems();
    }
}
