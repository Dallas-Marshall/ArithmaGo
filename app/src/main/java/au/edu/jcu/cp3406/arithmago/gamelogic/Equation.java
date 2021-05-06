package au.edu.jcu.cp3406.arithmago.gamelogic;

import java.util.ArrayList;
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

    public String generate(String operator) throws InvalidOperatorException {
        ArrayList<String> OPERATORS = new ArrayList<>();
        OPERATORS.add("multiplication");
        OPERATORS.add("division");
        OPERATORS.add("addition");
        OPERATORS.add("subtraction");

        try {
            int index = OPERATORS.indexOf(operator.toLowerCase());
            String operatorSymbol;
            switch (index) {
                case 0:
                    operatorSymbol = "x";
                    break;
                case 1:
                    operatorSymbol = "÷";
                    break;
                case 2:
                    operatorSymbol = "+";
                    break;
                default:
                    operatorSymbol = "-";
                    break;
            }

            int multiplier = generateRandomIntInRange(min, max); // First Number
            int multiplicand = generateRandomIntInRange(min, max); // Second Number
            return String.format(locale, "%d %s %d", multiplier, operatorSymbol, multiplicand);
        } catch (ClassCastException e) {
            throw new InvalidOperatorException(String.format("'%s' is not a valid operator", operator));
        }
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

    public static class InvalidOperatorException extends Exception {
        InvalidOperatorException(String message) {
            super(message);
        }
    }
}
