package types;

import java.util.Random;

public class Table {
    public static final String EMPTY = "⬜";
    public static final String EOL = System.lineSeparator();
    public static final int DIFFICULTY = 3;
    public static final int DEFAULT_BOTTLE_CAPACITY = 5;

    private Bottle[] bottles;

    public Table(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity) {
        int size = numberOfUsedSymbols + DIFFICULTY;
        bottles = new Bottle[size];
        Random random = new Random(seed);
        for (int i = 0; i < numberOfUsedSymbols; i++) {
            Filling[] content = generateRandomContent(symbols, capacity, random);
            bottles[i] = new Bottle(content);
        }
        for (int i = numberOfUsedSymbols; i < size; i++) {
            bottles[i] = new Bottle(capacity);
        }
    }

    private Filling[] generateRandomContent(Filling[] symbols, int capacity, Random random) {
        Filling[] content = new Filling[capacity];
        for (int i = 0; i < capacity; i++) {
            content[i] = symbols[random.nextInt(symbols.length)];
        }
        return content;
    }

    public void regenerateTable() {
        for (Bottle bottle : bottles) {
            bottle = new Bottle(bottle.capacity());
        }
    }

    public boolean singleFilling(int i) {
        return bottles[i].isSingleFilling();
    }

    public boolean isEmpty(int i) {
        return bottles[i].isEmpty();
    }

    public boolean isFull(int i) {
        return bottles[i].isFull();
    }

    public boolean areAllFilled() {
        for (Bottle bottle : bottles) {
            if (!bottle.isEmpty() && !bottle.isSingleFilling()) {
                return false;
            }
        }
        return true;
    }

    public void pourFromTo(int i, int j) {
        if (!bottles[i].isEmpty() && !bottles[j].isFull() && bottles[i].top() == bottles[j].top()) {
            bottles[j].receive(bottles[i].top());
            bottles[i].pourOut();
        }
    }

    public void addBottle(Bottle bottle) {
        Bottle[] newBottles = new Bottle[bottles.length + 1];
        System.arraycopy(bottles, 0, newBottles, 0, bottles.length);
        newBottles[bottles.length] = bottle;
        bottles = newBottles;
    }

    public int getSizeBottles() {
        return bottles.length;
    }

    public Filling top(int i) {
        return bottles[i].top();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Bottle bottle : bottles) {
            sb.append(bottle.toString());
        }
        return sb.toString();
    }
}
