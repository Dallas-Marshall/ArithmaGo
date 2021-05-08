package au.edu.jcu.cp3406.arithmago.gamelogic;

import java.util.ArrayList;
import java.util.Random;

public class ArithmaGoGame {
    private double accuracy;
    private int numberOfQuestionsAnswered;
    private int numberOfCorrectAnswers;
    private int numberOfIncorrectAnswers;

    private String currentGameLevel;
    private final boolean isDifficultyLocked; // Whether to adjust level based on accuracy
    private ArrayList<String> OPERATORS; // List of all possible Operators
    private String lastOperator;

    private boolean isMultiplicationEnabled;
    private boolean isDivisionEnabled;
    private boolean isAdditionEnabled;
    private boolean isSubtractionEnabled;

    private final Equation equation;
    private String eqn;

    public ArithmaGoGame() {
        this.currentGameLevel = "MEDIUM";
        this.isDifficultyLocked = false;
        this.isMultiplicationEnabled = true;
        this.isDivisionEnabled = true;
        this.isAdditionEnabled = true;
        this.isSubtractionEnabled = true;

        this.equation = new Equation(currentGameLevel);
        setupOperators(); // List of all possible Operators
    }

    public ArithmaGoGame(String specifiedLevel, boolean isDifficultyLocked, boolean isMultiplicationEnabled,
                         boolean isDivisionEnabled, boolean isAdditionEnabled, boolean isSubtractionEnabled) {
        this.currentGameLevel = specifiedLevel;
        this.isDifficultyLocked = isDifficultyLocked;
        this.isMultiplicationEnabled = isMultiplicationEnabled;
        this.isDivisionEnabled = isDivisionEnabled;
        this.isAdditionEnabled = isAdditionEnabled;
        this.isSubtractionEnabled = isSubtractionEnabled;

        this.equation = new Equation(currentGameLevel);
        setupOperators(); // List of all possible Operators
    }

    public String getLastOperator() {
        return lastOperator;
    }

    public String getCurrentGameLevel() {
        return currentGameLevel;
    }

    public boolean isMultiplicationEnabled() {
        return isMultiplicationEnabled;
    }

    public boolean isDivisionEnabled() {
        return isDivisionEnabled;
    }

    public boolean isAdditionEnabled() {
        return isAdditionEnabled;
    }

    public boolean isSubtractionEnabled() {
        return isSubtractionEnabled;
    }

    public boolean isDifficultyLocked() {
        return isDifficultyLocked;
    }

    public int getNumberOfIncorrectAnswers() {
        return numberOfIncorrectAnswers;
    }

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public int getNumberOfQuestionsAnswered() {
        return numberOfQuestionsAnswered;
    }

    public String nextQuestion() {
        eqn = "";
        Random rand = new Random();
        // Generate equation with random operator
        int randomNumber = rand.nextInt(4);
        while (eqn.equals("")) {
            try {
                switch (randomNumber) {
                    case 0:
                        if (isMultiplicationEnabled) {
                            eqn = equation.generate("multiplication");
                            lastOperator = "multiplication";
                        }
                        break;
                    case 1:
                        if (isDivisionEnabled) {
                            eqn = equation.generate("division");
                            lastOperator = "division";
                        }
                        break;
                    case 2:
                        if (isAdditionEnabled) {
                            eqn = equation.generate("addition");
                            lastOperator = "addition";
                        }
                        break;
                    default:
                        if (isSubtractionEnabled) {
                            eqn = equation.generate("subtraction");
                            lastOperator = "subtraction";
                        }
                        break;
                }
            } catch (Equation.InvalidOperatorException e) {
                eqn = "1 + 1";
            }
        }
        return eqn;
    }

    public boolean checkAnswer(int answer) {
        boolean isCorrect = equation.checkAnswer(lastOperator, eqn, answer);
        numberOfQuestionsAnswered++;
        if (isCorrect) {
            numberOfCorrectAnswers++;
        } else {
            numberOfIncorrectAnswers++;
        }
        accuracy = (double) numberOfCorrectAnswers / numberOfQuestionsAnswered;
        if (!isDifficultyLocked) {
            updateDifficulty();
        }
        return isCorrect;
    }

    private void updateDifficulty() {
        double EASY_THRESHOLD = 0.15;
        double MEDIUM_THRESHOLD = 0.60;
        double HARD_THRESHOLD = 0.85;

        // Keep set level at beginning
        if (numberOfQuestionsAnswered < 5) {
            if (accuracy < EASY_THRESHOLD) {
                currentGameLevel = "EASY";
            } else if (accuracy < MEDIUM_THRESHOLD) {
                currentGameLevel = "MEDIUM";
            } else if (accuracy < HARD_THRESHOLD) {
                currentGameLevel = "HARD";
            } else {
                currentGameLevel = "EXTREME";
            }
            equation.changeLevel(currentGameLevel);
        }
    }

    private void setupOperators() {
        OPERATORS = new ArrayList<>();
        OPERATORS.add("multiplication");
        OPERATORS.add("division");
        OPERATORS.add("addition");
        OPERATORS.add("subtraction");
    }

    public void addOperator(String newOperator) {
        if (OPERATORS.contains(newOperator.toLowerCase())) { // Valid operator
            switch (newOperator.toLowerCase()) {
                case "multiplication":
                    isMultiplicationEnabled = true;
                    break;
                case "division":
                    isDivisionEnabled = true;
                    break;
                case "addition":
                    isAdditionEnabled = true;
                    break;
                case "subtraction":
                    isSubtractionEnabled = true;
                    break;
            }
        }
    }

    public void removeOperator(String operatorToRemove) {
        if (OPERATORS.contains(operatorToRemove.toLowerCase())) { // Valid operator
            switch (operatorToRemove.toLowerCase()) {
                case "multiplication":
                    isMultiplicationEnabled = false;
                    break;
                case "division":
                    isDivisionEnabled = false;
                    break;
                case "addition":
                    isAdditionEnabled = false;
                    break;
                case "subtraction":
                    isSubtractionEnabled = false;
                    break;
            }
        }
    }
}
