package au.edu.jcu.cp3406.arithmago;

import org.junit.Test;

import java.util.Locale;

import au.edu.jcu.cp3406.arithmago.gamelogic.Equation;
import au.edu.jcu.cp3406.arithmago.gamelogic.Level;

import static org.junit.Assert.assertEquals;

public class EquationTest {
    @Test
    public void testDefaultConstructor() {
        Equation equation = new Equation();

        assertEquals("MEDIUM", equation.getLevel());
        Level level = new Level("MEDIUM");

        for (int i = 0; i < 10; i++) {
            String eqn = equation.generateMultiplication();
            String[] factors = eqn.split("x");
            for (String factor : factors) {
                // >= level min & <= level max
                assert ((Integer.parseInt(factor.trim()) >= level.getMin()) && (Integer.parseInt(factor.trim()) <= level.getMax()));
            }
        }
    }

    @Test
    public void testSpecifiedConstructor() {
        Equation equationC = new Equation("EASY");

        assertEquals("EASY", equationC.getLevel());
        Level level = new Level("EASY");

        for (int i = 0; i < 10; i++) {
            String eqn = equationC.generateMultiplication();
            String[] factors = eqn.split("x");
            for (String factor : factors) {
                // >= level min & <= level max
                assert ((Integer.parseInt(factor.trim()) >= level.getMin()) && (Integer.parseInt(factor.trim()) <= level.getMax()));
            }
        }
    }

    @Test
    public void testEquationStructure() {
        Equation equationS = new Equation("EASY");
        Locale locale = Locale.getDefault();

        for (int i = 0; i < 10; i++) {
            String eqn = equationS.generateMultiplication();
            String[] factors = eqn.split("x");
            String correctEquationStructure = String.format(locale, "%s x %s", factors[0].trim(), factors[1].trim());
            assertEquals(correctEquationStructure, eqn);
        }

        for (int i = 0; i < 10; i++) {
            String eqn = equationS.generateDivision();
            String[] factors = eqn.split("÷");
            String correctEquationStructure = String.format(locale, "%s ÷ %s", factors[0].trim(), factors[1].trim());
            assertEquals(correctEquationStructure, eqn);
        }

        for (int i = 0; i < 10; i++) {
            String eqn = equationS.generateAddition();
            String[] factors = eqn.split("\\+");
            String correctEquationStructure = String.format(locale, "%s + %s", factors[0].trim(), factors[1].trim());
            assertEquals(correctEquationStructure, eqn);
        }

        for (int i = 0; i < 10; i++) {
            String eqn = equationS.generateSubtraction();
            String[] factors = eqn.split("-");
            String correctEquationStructure = String.format(locale, "%s - %s", factors[0].trim(), factors[1].trim());
            assertEquals(correctEquationStructure, eqn);
        }
    }

    @Test
    public void testChangeLevel() {
        Equation equationL = new Equation("EASY");
        assertEquals("EASY", equationL.getLevel());

        equationL.changeLevel("HARD");
        assertEquals("HARD", equationL.getLevel());
    }
}