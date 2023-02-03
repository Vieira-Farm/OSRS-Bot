package scripting.antiban;

public class AntibanUtilities {

    public static int addWaitTime(int newWaitTime, int numberOfWaitTimes, int oldAverage) {
        return (((numberOfWaitTimes - 1) * oldAverage) + newWaitTime) / numberOfWaitTimes;
    }
}
