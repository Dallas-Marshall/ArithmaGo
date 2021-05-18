package au.edu.jcu.cp3406.arithmago;

public class Record {
    private String username;
    private int score;
    private String level;

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
}