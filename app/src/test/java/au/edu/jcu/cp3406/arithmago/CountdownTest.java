package au.edu.jcu.cp3406.arithmago;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

public class CountdownTest {
    @Test
    public void testDefaultConstructor() {
        CountdownTimer timer = new CountdownTimer();
        int seconds = timer.getSeconds();
        assertEquals(60, seconds);
    }

    @Test
    public void testSpecifiedConstructor() {
        // Maximum possible time
        try {
            CountdownTimer timer = new CountdownTimer(120);
            int seconds = timer.getSeconds();
            assertEquals(120, seconds);
        } catch (CountdownTimer.InvalidTimeException e) {
            fail();
        }

        // Minimum possible time
        try {
            CountdownTimer timer2 = new CountdownTimer(0);
            int seconds = timer2.getSeconds();
            assertEquals(0, seconds);
        } catch (CountdownTimer.InvalidTimeException e) {
            fail();
        }

        // Time too large
        assertThrows(CountdownTimer.InvalidTimeException.class, () -> {
            CountdownTimer timer3 = new CountdownTimer(121);
        });

        // Time to small
        assertThrows(CountdownTimer.InvalidTimeException.class, () -> {
            CountdownTimer timer4 = new CountdownTimer(-1);
        });
    }

    @Test
    public void testInvalidTimeException() {
        try {
            CountdownTimer timer = new CountdownTimer(-1);
        } catch (CountdownTimer.InvalidTimeException e) {
            assertEquals("Invalid time -1 - must be between 0 - 120", e.getMessage());
        }
    }

    @Test
    public void testTickMethod() {
        // Test tick decreases seconds
        CountdownTimer timer = new CountdownTimer();
        for (int i = 0; i < 60; i++) {
            timer.tick();
        }
        assertEquals(0, timer.getSeconds());

        // Test won't go below 0
        CountdownTimer timer2 = new CountdownTimer();
        for (int i = 0; i < 61; i++) {
            timer2.tick();
        }
        assertEquals(0, timer.getSeconds());
    }
}