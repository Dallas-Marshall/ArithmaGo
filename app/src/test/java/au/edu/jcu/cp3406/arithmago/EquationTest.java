package au.edu.jcu.cp3406.arithmago;

import org.junit.Test;

import java.util.Locale;

import au.edu.jcu.cp3406.arithmago.gamelogic.CountdownTimer;
import au.edu.jcu.cp3406.arithmago.gamelogic.Equation;
import au.edu.jcu.cp3406.arithmago.gamelogic.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EquationTest {
    @Test
    public void testDefaultConstructor() {
        Equation equation = new Equation();

        assertEquals("MEDIUM", equation.getLevel());
        Level level = new Level("MEDIUM");

        try {
            for (int i = 0; i < 10; i++) {
                String eqn = equation.generate("multiplication");
                String[] factors = eqn.split("x");
                for (String factor : factors) {
                    // >= level min & <= level max
                    assert ((Integer.parseInt(factor.trim()) >= level.getMin()) && (Integer.parseInt(factor.trim()) <= level.getMax()));
                }
            }
        } catch (Equation.InvalidOperatorException e) {
            fail();
        }
    }

    @Test
    public void testSpecifiedConstructor() {
        Equation equationC = new Equation("EASY");

        assertEquals("EASY", equationC.getLevel());
        Level level = new Level("EASY");

        try {
            for (int i = 0; i < 10; i++) {
                String eqn = equationC.generate("multiplication");
                String[] factors = eqn.split("x");
                for (String factor : factors) {
                    // >= level min & <= level max
                    assert ((Integer.parseInt(factor.trim()) >= level.getMin()) && (Integer.parseInt(factor.trim()) <= level.getMax()));
                }
            }
        } catch (Equation.InvalidOperatorException e) {
            fail();
        }
    }

    @Test
    public void testEquationStructure() {
        Equation equationS = new Equation("EASY");
        Locale locale = Locale.getDefault();

        // Test Lowercase
        try {
            for (int i = 0; i < 10; i++) {
                String eqn = equationS.generate("multiplication");
                String[] factors = eqn.split("x");
                String correctEquationStructure = String.format(locale, "%s x %s", factors[0].trim(), factors[1].trim());
                assertEquals(correctEquationStructure, eqn);
            }

            for (int i = 0; i < 10; i++) {
                String eqn = equationS.generate("division");
                String[] factors = eqn.split("÷");
                String correctEquationStructure = String.format(locale, "%s ÷ %s", factors[0].trim(), factors[1].trim());
                assertEquals(correctEquationStructure, eqn);
            }

            // Test Uppercase
            for (int i = 0; i < 10; i++) {
                String eqn = equationS.generate("ADDITION");
                String[] factors = eqn.split("\\+");
                String correctEquationStructure = String.format(locale, "%s + %s", factors[0].trim(), factors[1].trim());
                assertEquals(correctEquationStructure, eqn);
            }

            for (int i = 0; i < 10; i++) {
                String eqn = equationS.generate("SUBTRACTION");
                String[] factors = eqn.split("-");
                String correctEquationStructure = String.format(locale, "%s - %s", factors[0].trim(), factors[1].trim());
                assertEquals(correctEquationStructure, eqn);
            }
        } catch (Equation.InvalidOperatorException e) {
            fail();
        }
    }

    @Test
    public void testChangeLevel() {
        Equation equationL = new Equation("EASY");
        assertEquals("EASY", equationL.getLevel());

        equationL.changeLevel("HARD");
        assertEquals("HARD", equationL.getLevel());
    }

    @Test
    public void testCheckAnswer() {
        Equation equationA = new Equation("EASY");
        String eqnM = "5 x 4";
        assert equationA.checkAnswer("multiplication", eqnM, 20);

        String eqnA = "5 + 2";
        assert equationA.checkAnswer("addition", eqnA, 7);

        String eqnD = "20 ÷ 4";
        assert equationA.checkAnswer("DIVISION", eqnD, 5);

        String eqnS = "5 - 2";
        assert equationA.checkAnswer("SUBTRACTION", eqnS, 3);
    }

    @Test
    public void InvalidOperatorException() {
        try {
            Equation equation = new Equation();
            equation.generate("cake");
        } catch (Equation.InvalidOperatorException e) {
            assertEquals("'cake' is not a valid operator", e.getMessage());
        }
    }
}