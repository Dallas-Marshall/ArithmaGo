package au.edu.jcu.cp3406.arithmago;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Leaderboard {
    private final ArrayList<Record> records;

    public Leaderboard() {
        records = new ArrayList<>();
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Record record : records) {
            stringBuilder.append(record.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
