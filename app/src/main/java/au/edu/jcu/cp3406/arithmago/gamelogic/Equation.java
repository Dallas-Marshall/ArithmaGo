package au.edu.jcu.cp3406.arithmago.gamelogic;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;


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
            if (multiplicand == 0) {
                multiplicand = 1;
            }
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

    public boolean checkAnswer(String operator, String equation, double guess) {
        String regex = getRegex(operator);
        String[] factors = equation.split(regex);
        double answer = calculateAnswer(regex, factors);
        return guess == answer;
    }

    public double getAnswer(String operator, String equation) {
        String regex = getRegex(operator);
        String[] factors = equation.split(regex);
        return calculateAnswer(regex, factors);
    }

    public String[] generateAnswers(String operator, String equation) {
        String[] possibleAnswers = new String[3];
        String regex = getRegex(operator);
        String[] factors = equation.split(regex);
        Random random = new Random();
        NumberFormat nf = new DecimalFormat("###.###");

        double numberToDisplay;
        double answer = calculateAnswer(regex, factors);
        if ((answer < 3) && (answer > -3) && (operator.toLowerCase().equals("division"))) { // Answer is likely to be a decimal
            for (int i = 0; i < 2; ++i) {
                double salt = random.nextDouble() + 0.01;
                if (random.nextBoolean()) {
                    numberToDisplay = answer + salt;
                } else {
                    numberToDisplay = answer - salt;
                }
                if ((i == 1) && (numberToDisplay == Double.parseDouble(possibleAnswers[0]))) { // Answers are the same
                    numberToDisplay += 0.01;
                }
                possibleAnswers[i] = nf.format(numberToDisplay);
            }
        } else { // Answer is a whole number
            for (int i = 0; i < 2; ++i) {
                int salt = random.nextInt(7) + 1;
                if (random.nextBoolean()) {
                    numberToDisplay = answer + salt;
                } else {
                    numberToDisplay = answer - salt;
                }
                if ((i == 1) && (numberToDisplay == Double.parseDouble(possibleAnswers[0]))) { // Answers are the same
                    numberToDisplay += 1;
                }
                possibleAnswers[i] = nf.format(numberToDisplay);
            }
        }
        possibleAnswers[2] = nf.format((answer));
        return possibleAnswers;
    }

    public String getRegex(String operator) {
        String regex;
        switch (operator.toLowerCase()) {
            case "division":
                regex = "÷";
                break;
            case "addition":
                regex = "\\+";
                break;
            case "subtraction":
                regex = "-";
                break;
            default:
                regex = "x";
                break;
        }
        return regex;
    }

    private double calculateAnswer(String regex, String[] factors) {
        double answer;
        switch (regex) {
            case "÷":
                answer = Double.parseDouble(factors[0].trim()) / Double.parseDouble(factors[1].trim());
                break;
            case "\\+":
                answer = Double.parseDouble(factors[0].trim()) + Double.parseDouble(factors[1].trim());
                break;
            case "-":
                answer = Double.parseDouble(factors[0].trim()) - Double.parseDouble(factors[1].trim());
                break;
            default:
                answer = Double.parseDouble(factors[0].trim()) * Double.parseDouble(factors[1].trim());
                break;
        }
        return answer;
    }

    public static class InvalidOperatorException extends Exception {
        InvalidOperatorException(String message) {
            super(message);
        }
    }
}
