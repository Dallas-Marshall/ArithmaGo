package au.edu.jcu.cp3406.arithmago;

/**
 * Custom class to store details of custom speed_spinner_row objects.
 */
public class SpeedItem {
    private final String speedName;
    private final int speedIcon;

    /**
     * Constructor for SpeedItem object.
     *
     * @param speedName String - Name of speed in row.
     * @param speedIcon int - ID of the image in row.
     */
    public SpeedItem(String speedName, int speedIcon) {
        this.speedName = speedName;
        this.speedIcon = speedIcon;
    }

    /**
     * Getter method for speedName.
     *
     * @return String - Name of speed in row.
     */
    public String getSpeedName() {
        return speedName;
    }

    /**
     * Getter method for speedIcon.
     *
     * @return int - ID of the image in row.
     */
    public int getSpeedIcon() {
        return speedIcon;
    }
}
