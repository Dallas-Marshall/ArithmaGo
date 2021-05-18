package au.edu.jcu.cp3406.arithmago;

import java.util.ArrayList;

public class Leaderboard {
    private ArrayList<Record> records;

    public Leaderboard() {
        records = new ArrayList<>();
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public ArrayList<Record> getRecords() {
        return records;
    }
}
