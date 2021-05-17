package au.edu.jcu.cp3406.arithmago;

import org.junit.Test;

import au.edu.jcu.cp3406.arithmago.gamelogic.ArithmaGoGame;
import au.edu.jcu.cp3406.arithmago.gamelogic.Equation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArithmaGoGameTest {
    @Test
    public void testDefaultConstructor() {
        ArithmaGoGame game1 = new ArithmaGoGame();

        assert game1.isMultiplicationEnabled();
        assert game1.isAdditionEnabled();
        assert game1.isDivisionEnabled();
        assert game1.isSubtractionEnabled();
        assert game1.getScore() == 0;

        assert !game1.isDifficultyLocked();

        assertEquals("MEDIUM", game1.getCurrentGameLevel());
        assertEquals(0, game1.getNumberOfQuestionsAnswered());
        assertEquals(0, game1.getNumberOfIncorrectAnswers());
        assertEquals(0, game1.getNumberOfCorrectAnswers());
    }

    @Test
    public void testSpecifiedConstructor() {
        ArithmaGoGame game2 = new ArithmaGoGame("HARD", true,
                true, false, true, false);

        assert game2.isMultiplicationEnabled();
        assert !game2.isDivisionEnabled();
        assert game2.isAdditionEnabled();
        assert !game2.isSubtractionEnabled();
        assert game2.getScore() == 0;

        assert game2.isDifficultyLocked();

        game2.addOperator("subtraction");
        assert game2.isSubtractionEnabled();

        game2.removeOperator("ADDITION");
        assert !game2.isAdditionEnabled();


        assertEquals("HARD", game2.getCurrentGameLevel());
        assertEquals(0, game2.getNumberOfQuestionsAnswered());
        assertEquals(0, game2.getNumberOfIncorrectAnswers());
        assertEquals(0, game2.getNumberOfCorrectAnswers());
    }

    @Test
    public void testScoring() {
        ArithmaGoGame game3 = new ArithmaGoGame();
        Equation testingEquationClass = new Equation();

        for (int i = 1; i < 7; i++) {
            String eqn1 = game3.nextQuestion(); // Generate equation
            String eqn1Operator = game3.getLastOperator(); // Get operator
            double eqn1Answer = testingEquationClass.getAnswer(eqn1Operator, eqn1); // Calculate answer

            assertTrue(game3.checkAnswer(eqn1Answer));
            assert game3.getScore() == (200 * i);
        }

        // Ensure game level changed properly
        assertEquals("EXTREME", game3.getCurrentGameLevel());
        String eqn2 = game3.nextQuestion();
        String eqn2Operator = game3.getLastOperator();
        double eqn2Answer = testingEquationClass.getAnswer(eqn2Operator, eqn2);

        assertTrue(game3.checkAnswer(eqn2Answer));
        assert game3.getScore() == (200 * 6 + 600);

        // Check accuracy
        assertEquals(1.0, game3.getAccuracy(), 0);

        // Test accuracy decreases after incorrect answers
        for (int i = 0; i < 3; i++) {
            String eqn3 = game3.nextQuestion();
            String eqn3Operator = game3.getLastOperator();
            double eqn3Answer = testingEquationClass.getAnswer(eqn3Operator, eqn3);

            assertFalse(game3.checkAnswer(eqn3Answer + 1));
            assert game3.getScore() == (200 * 6 + 600);
        }

        assertEquals(0.7, game3.getAccuracy(), 0);

        // Check level has decreased
        assertEquals("HARD", game3.getCurrentGameLevel());

        assertEquals(7, game3.getNumberOfCorrectAnswers());
        assertEquals(3, game3.getNumberOfIncorrectAnswers());
        assertEquals(10, game3.getNumberOfQuestionsAnswered());
    }
}