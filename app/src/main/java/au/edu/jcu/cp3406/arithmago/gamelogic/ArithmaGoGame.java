package au.edu.jcu.cp3406.arithmago.gamelogic;

import java.util.Random;

public class ArithmaGoGame {
    private double accuracy;
    private int numberOfQuestionsAnswered;
    private int numberOfCorrectAnswers;
    private int numberOfIncorrectAnswers;

    private String currentGameLevel;
    private final boolean isDifficultyLocked; // Whether to adjust level based on accuracy
    private String lastOperator;

    private final Equation equation;
    private String eqn;
    private String[] possibleAnswers;
    private int score;

    public ArithmaGoGame() {
        this.currentGameLevel = "MEDIUM";
        this.isDifficultyLocked = false;
        this.score = 0;

        this.equation = new Equation(currentGameLevel);
    }

    public ArithmaGoGame(String specifiedLevel, boolean isDifficultyLocked) {
        this.currentGameLevel = specifiedLevel;
        this.isDifficultyLocked = isDifficultyLocked;
        this.score = 0;

        this.equation = new Equation(currentGameLevel);
    }

    public String getLastOperator() {
        return lastOperator;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getScore() {
        return score;
    }

    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    public String getCurrentGameLevel() {
        return currentGameLevel;
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
                        eqn = equation.generate("multiplication");
                        possibleAnswers = equation.generateAnswers("multiplication", eqn);
                        lastOperator = "multiplication";
                        break;
                    case 1:
                        eqn = equation.generate("division");
                        possibleAnswers = equation.generateAnswers("division", eqn);
                        lastOperator = "division";
                        break;
                    case 2:
                        eqn = equation.generate("addition");
                        possibleAnswers = equation.generateAnswers("addition", eqn);
                        lastOperator = "addition";
                        break;
                    default:
                        eqn = equation.generate("subtraction");
                        possibleAnswers = equation.generateAnswers("subtraction", eqn);
                        lastOperator = "subtraction";
                        break;
                }
            } catch (Equation.InvalidOperatorException e) {
                eqn = "1 + 1";
            }
        }
        return eqn;
    }

    public boolean checkAnswer(double answer) {
        boolean isCorrect = equation.checkAnswer(lastOperator, eqn, answer);
        numberOfQuestionsAnswered++;
        if (isCorrect) {
            numberOfCorrectAnswers++;
        } else {
            numberOfIncorrectAnswers++;
        }
        accuracy = (double) numberOfCorrectAnswers / numberOfQuestionsAnswered;
        if (isCorrect) {
            updateScore();
        }
        if (!isDifficultyLocked) {
            updateDifficulty();
        }
        return isCorrect;
    }

    private void updateScore() {
        switch (currentGameLevel.toUpperCase()) {
            case "EASY":
                score += 100;
                break;
            case "MEDIUM":
                score += 200;
                break;
            case "HARD":
                score += 400;
                break;
            case "EXTREME":
                score += 600;
                break;
        }
    }

    private void updateDifficulty() {
        double EASY_THRESHOLD = 0.15;
        double MEDIUM_THRESHOLD = 0.60;
        double HARD_THRESHOLD = 0.85;

        // Keep set level at beginning
        if (numberOfQuestionsAnswered > 5) {
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
}
