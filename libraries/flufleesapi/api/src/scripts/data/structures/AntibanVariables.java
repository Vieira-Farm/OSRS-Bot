package scripts.data.structures;


import scripts.scripting.antiban.AntibanUtilities;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AntibanVariables extends PrivateSingleton {

    private AtomicInteger numberSleeps;
    private AtomicInteger averageAntibanSleep;

    public AntibanVariables(Class callingClass,
                            AtomicInteger numberSleeps,
                            AtomicInteger averageAntibanSleep) throws IllegalAccessException {
        super(callingClass);
        this.numberSleeps = numberSleeps;
        this.averageAntibanSleep = averageAntibanSleep;
    }

    public int getNumberOfSleeps() {
        return numberSleeps.get();
    }

    public void incrementNumberOfSleeps() {
        numberSleeps.incrementAndGet();
    }

    public int getAverageAntibanSleep() {
        return averageAntibanSleep.get();
    }

    public void updateAverageAntibanSleep(int lastSleepTime) {
        averageAntibanSleep.set(
            AntibanUtilities.addWaitTime(
                lastSleepTime, getNumberOfSleeps(), getAverageAntibanSleep()
            )
        );
    }
}
