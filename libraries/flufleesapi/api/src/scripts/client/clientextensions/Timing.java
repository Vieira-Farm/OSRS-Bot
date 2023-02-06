package scripts.client.clientextensions;

public class Timing extends org.tribot.api.Timing {

    /**
     * Gets current timestamp, formatted like the TRiBot timestamps.
     *
     * @param timeMilliseconds Current system time, in milliseocnds.
     * @return String containing the current time.
     */
    public static String getTimestamp(long timeMilliseconds) {
        return String.format("%02d:%02d:%02d",
                ((timeMilliseconds / (1000 * 60 * 60)) % 24), //Hours
                ((timeMilliseconds / (1000 * 60)) % 60), //Minutes
                (timeMilliseconds / 1000) % 60); //Seconds
    }

    /**
     * Creates timestamp String to match the one's used in the TRiBot client.
     *
     * @param timeMilliseconds Current system time in milliseconds.
     * @return String containing current time, formatted to match TRiBot.
     */
    public static String getTRiBotTimestamp(long timeMilliseconds) {
        return "[" + getTimestamp(timeMilliseconds) + "]";
    }
}
