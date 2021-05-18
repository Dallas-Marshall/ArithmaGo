package au.edu.jcu.cp3406.arithmago;

import androidx.annotation.NonNull;

public class Record {
    private final String username;
    private final int score;
    private final String level;

    public Record(String username, int score, String level) {
        this.username = username;
        this.score = score;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getLevel() {
        return level;
    }

    @NonNull
    @Override
    public String toString() {
        return "Record{" +
                "username='" + username + '\'' +
                ", score=" + score +
                ", level='" + level + '\'' +
                '}';
    }
}