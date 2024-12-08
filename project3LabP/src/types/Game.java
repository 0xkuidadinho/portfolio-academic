package types;

import java.util.Random;

public class Game {
    private Table table;
    private int score;
    private int numberOfMoves;

    public Game(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity) {
        this(symbols, numberOfUsedSymbols, seed, capacity, 0);
    }

    public Game(Filling[] symbols, int numberOfUsedSymbols, int seed, int capacity, int score) {
        table = new Table(symbols, numberOfUsedSymbols, seed, capacity);
        this.score = score;
        numberOfMoves = 0;
    }

    public Bottle getNewBottle() {
        return new Bottle(table.getSizeBottles());
    }

    public void play(int i, int j) {
        if (i >= 0 && i < table.getSizeBottles() && j >= 0 && j < table.getSizeBottles()) {
            table.pourFromTo(i, j);
            numberOfMoves++;
        }
    }

    public boolean isRoundFinished() {
        return table.areAllFilled() || numberOfMoves == 0; // Round is finished if all bottles are filled or no moves have been made yet
    }

    public void startNewRound() {
        table.regenerateTable();
        numberOfMoves = 0;
    }

    public int score() {
        return score;
    }

    public int jogadas() {
        return numberOfMoves;
    }

    public void provideHelp() {
        score -= 100;
        table.addBottle(new Bottle(table.getSizeBottles()));
    }

    public int updateScore() {
        int pointsEarned = 0;
        if (isRoundFinished()) {
            if (numberOfMoves <= 10) {
                pointsEarned = 1000;
            } else if (numberOfMoves <= 15) {
                pointsEarned = 500;
            } else if (numberOfMoves <= 25) {
                pointsEarned = 200;
            }
        }
        score += pointsEarned;
        return pointsEarned;
    }
}
