package au.edu.jcu.cp3406.arithmago;

public class Level {
    private int min;
    private int max;
    private String level;

    Level(String level) {
        switch (level.toUpperCase()) {
            case "EASY":
                this.min = 0;
                this.max = 5;
                this.level = "EASY";
                break;
            case "HARD":
                this.min = 3;
                this.max = 12;
                this.level = "HARD";
                break;
            case "EXTREME":
                this.min = 6;
                this.max = 15;
                this.level = "EXTREME";
                break;
            default:
                this.min = 1;
                this.max = 9;
                this.level = "MEDIUM";
                break;
        }
    }

    public String getLevel() {
        return this.level;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }
}