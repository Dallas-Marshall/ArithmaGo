package au.edu.jcu.cp3406.arithmago.gamelogic;

import java.util.Locale;


public class Equation {
    private final Locale locale = Locale.getDefault();
    private Level levelSelected;
    private int min;
    private int max;

    public Equation() {
        levelSelected = new Level();
        min = levelSelected.getMin();
        max = levelSelected.getMax();
    }

    public Equation(String specifiedLevel) {
        levelSelected = new Level(specifiedLevel);
        min = levelSelected.getMin();
        max = levelSelected.getMax();
    }

    public String generateMultiplication() {
        int multiplier = generateRandomIntInRange(min, max); // First Number
        int multiplicand = generateRandomIntInRange(min, max); // Second Number
        return String.format(locale, "%d x %d", multiplier, multiplicand);
    }

    public String generateDivision() {
        int multiplier = generateRandomIntInRange(min, max); // First Number
        int multiplicand = generateRandomIntInRange(min, max); // Second Number
        return String.format(locale, "%d ÷ %d", multiplier, multiplicand);
    }

    public String generateAddition() {
        int multiplier = generateRandomIntInRange(min, max); // First Number
        int multiplicand = generateRandomIntInRange(min, max); // Second Number
        return String.format(locale, "%d + %d", multiplier, multiplicand);
    }

    public String generateSubtraction() {
        int multiplier = generateRandomIntInRange(min, max); // First Number
        int multiplicand = generateRandomIntInRange(min, max); // Second Number
        return String.format(locale, "%d - %d", multiplier, multiplicand);
    }

    public void changeLevel(String newLevel) {
        levelSelected = new Level(newLevel);
        min = levelSelected.getMin();
        max = levelSelected.getMax();
    }

    public String getLevel() {
        return levelSelected.getLevel();
    }

    private int generateRandomIntInRange(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
