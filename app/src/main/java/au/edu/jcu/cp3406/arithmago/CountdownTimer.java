package au.edu.jcu.cp3406.arithmago;

public class CountdownTimer {
    private int seconds;

    CountdownTimer() {
        this.seconds = 60;
    }

    CountdownTimer(int inputSeconds) throws InvalidTimeException {
        if ((inputSeconds < 0) || (inputSeconds > 120)) {
            throw new InvalidTimeException(String.format("Invalid time %d - must be between 0 - 120", inputSeconds));
        } else {
            this.seconds = inputSeconds;
        }

    }

    public void tick() {
        if (seconds >= 1) {
            seconds--;
        }
    }

    public int getSeconds() {
        return seconds;
    }

    public static class InvalidTimeException extends Exception {
        InvalidTimeException(String message) {
            super(message);
        }
    }
}