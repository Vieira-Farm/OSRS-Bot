package scripting.frameworks.mission.missiontypes.taskmissions;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AntibanTaskMission implements TaskMission {

    protected AtomicInteger numberSleeps, averageAntibanSleep;

    public AntibanTaskMission() {
        this.numberSleeps = new AtomicInteger(0);
        this.averageAntibanSleep = new AtomicInteger(0);
    }

    public AntibanTaskMission(AtomicInteger numberSleeps, AtomicInteger averageAntibanSleep) {
        this.numberSleeps = numberSleeps;
        this.averageAntibanSleep = averageAntibanSleep;
    }

}
