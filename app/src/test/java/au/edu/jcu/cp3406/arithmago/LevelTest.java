package au.edu.jcu.cp3406.arithmago;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LevelTest {
    @Test
    public void testEasyLevel() {
        // Uppercase specified
        Level levelEU = new Level("EASY");

        assertEquals("EASY", levelEU.getLevel());
        assertEquals(0, levelEU.getMin());
        assertEquals(5, levelEU.getMax());

        // Lowercase specified
        Level levelEL = new Level("easy");
        String returnedLevel = levelEL.getLevel();

        assertEquals("EASY", levelEL.getLevel());
        assertEquals(0, levelEL.getMin());
        assertEquals(5, levelEL.getMax());
    }

    @Test
    public void testMediumLevel() {
        // Uppercase specified
        Level levelMU = new Level("MEDIUM");

        assertEquals("MEDIUM", levelMU.getLevel());
        assertEquals(1, levelMU.getMin());
        assertEquals(9, levelMU.getMax());

        // Lowercase specified
        Level levelML = new Level("medium");
        String returnedLevel = levelML.getLevel();

        assertEquals("MEDIUM", levelML.getLevel());
        assertEquals(1, levelML.getMin());
        assertEquals(9, levelML.getMax());
    }

    @Test
    public void testHardLevel() {
        // Uppercase specified
        Level levelHU = new Level("HARD");

        assertEquals("HARD", levelHU.getLevel());
        assertEquals(3, levelHU.getMin());
        assertEquals(12, levelHU.getMax());

        // Lowercase specified
        Level levelHL = new Level("hard");
        String returnedLevel = levelHL.getLevel();

        assertEquals("HARD", levelHL.getLevel());
        assertEquals(3, levelHL.getMin());
        assertEquals(12, levelHL.getMax());
    }

    @Test
    public void testExtremeLevel() {
        // Uppercase specified
        Level levelEU = new Level("EXTREME");

        assertEquals("EXTREME", levelEU.getLevel());
        assertEquals(6, levelEU.getMin());
        assertEquals(15, levelEU.getMax());

        // Lowercase specified
        Level levelEL = new Level("extreme");
        String returnedLevel = levelEL.getLevel();

        assertEquals("EXTREME", levelEL.getLevel());
        assertEquals(6, levelEL.getMin());
        assertEquals(15, levelEL.getMax());
    }

    @Test
    public void testDefaultLevel() {
        // Uppercase specified
        Level levelDU = new Level("NOTALEVEL");

        assertEquals("MEDIUM", levelDU.getLevel());
        assertEquals(1, levelDU.getMin());
        assertEquals(9, levelDU.getMax());

        // Lowercase specified
        Level levelDL = new Level("notalevel");
        String returnedLevel = levelDL.getLevel();

        assertEquals("MEDIUM", levelDL.getLevel());
        assertEquals(1, levelDL.getMin());
        assertEquals(9, levelDL.getMax());
    }
}