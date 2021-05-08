package au.edu.jcu.cp3406.arithmago;

import org.junit.Test;

import au.edu.jcu.cp3406.arithmago.gamelogic.ArithmaGoGame;

import static org.junit.Assert.assertEquals;

public class ArithmaGoGameTest {
    @Test
    public void testDefaultConstructor() {
        ArithmaGoGame game1 = new ArithmaGoGame();

        assert game1.isMultiplicationEnabled();
        assert game1.isAdditionEnabled();
        assert game1.isDivisionEnabled();
        assert game1.isSubtractionEnabled();

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

        assert game2.isDifficultyLocked();

        game2.addOperator("subtraction");
        assert game2.isSubtractionEnabled();

        game2.removeOperator("ADDITION");
        assert !game2.isAdditionEnabled();


        assertEquals("HARD", game2.getCurrentGameLevel());
        assertEquals(0, game2.getNumberOfQuestionsAnswered());
        assertEquals(0, game2.getNumberOfIncorrectAnswers());
        assertEquals(0, game2.getNumberOfCorrectAnswers());

        // TODO: Add scoring system
    }
}