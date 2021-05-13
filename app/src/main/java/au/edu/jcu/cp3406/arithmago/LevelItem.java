package au.edu.jcu.cp3406.arithmago;

/**
 * Custom class to store details of custom level_spinner_row objects.
 */
public class LevelItem {
    private final String levelName;
    private final int levelIcon;

    /**
     * Constructor for LevelItem object.
     *
     * @param levelName String - Name of level in row.
     * @param levelIcon int - ID of the image in row.
     */
    public LevelItem(String levelName, int levelIcon) {
        this.levelName = levelName;
        this.levelIcon = levelIcon;
    }

    /**
     * Getter method for levelName.
     *
     * @return String - Name of level in row.
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * Getter method for levelIcon.
     *
     * @return int - ID of the image in row.
     */
    public int getLevelIcon() {
        return levelIcon;
    }
}
