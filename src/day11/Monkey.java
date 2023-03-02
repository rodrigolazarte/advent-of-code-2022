package day11;

import java.math.BigInteger;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Predicate;

public class Monkey {

    private Queue<BigInteger> startingItems;
    private Function<BigInteger, BigInteger> operation;
    private Predicate<BigInteger> test;
    private int divisible;
    private int destinationMonkeyIfTrue;
    private int destinationMonkeyIfFalse;
    private long inspectedItems;

    public Monkey() {
        inspectedItems = 0;
    }

    public Queue<BigInteger> getStartingItems() {
        return startingItems;
    }

    public void setStartingItems(Queue<BigInteger> startingItems) {
        this.startingItems = startingItems;
    }

    public Function<BigInteger, BigInteger> getOperation() {
        return operation;
    }

    public void setOperation(Function<BigInteger, BigInteger> operation) {
        this.operation = operation;
    }

    public Predicate<BigInteger> getTest() {
        return test;
    }

    public void setTest(Predicate<BigInteger> test) {
        this.test = test;
    }

    public int getDestinationMonkeyIfTrue() {
        return destinationMonkeyIfTrue;
    }

    public void setDestinationMonkeyIfTrue(int destinationMonkeyIfTrue) {
        this.destinationMonkeyIfTrue = destinationMonkeyIfTrue;
    }

    public int getDestinationMonkeyIfFalse() {
        return destinationMonkeyIfFalse;
    }

    public void setDestinationMonkeyIfFalse(int destinationMonkeyIfFalse) {
        this.destinationMonkeyIfFalse = destinationMonkeyIfFalse;
    }

    public BigInteger executeOperation(BigInteger oldValue) {
        return this.operation.apply(oldValue);
    }

    public boolean executeTest(BigInteger value) {
        return this.test.test(value);
    }

    public int getDivisible() {
        return divisible;
    }

    public void setDivisible(int divisible) {
        this.divisible = divisible;
    }

    public void addInspectedItem() {
        this.inspectedItems += 1;
    }

    public long getInspectedItems() {
        return inspectedItems;
    }
}
