package au.edu.jcu.cp3406.arithmago;

import org.junit.Test;

import au.edu.jcu.cp3406.arithmago.database.Leaderboard;
import au.edu.jcu.cp3406.arithmago.database.Record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LeaderboardTest {
    @Test
    public void testLeaderboard() {
        Leaderboard leaderboard = new Leaderboard();
        assertEquals(0, leaderboard.getRecords().size());

        Record record = new Record("Dallas", 1000, "Expert");
        leaderboard.addRecord(record);
        assertEquals(1, leaderboard.getRecords().size());

        assertEquals("Dallas", record.getUsername());
        assertEquals(1000, record.getScore());
        assertEquals("Expert", record.getLevel());
    }
}