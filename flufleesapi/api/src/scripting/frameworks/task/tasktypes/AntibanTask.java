package scripting.frameworks.task.tasktypes;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import data.structures.AntibanVariables;
import scripting.antiban.AntiBanSingleton;
import scripting.frameworks.mission.missiontypes.TaskFinish;
import scripting.frameworks.modulardecisiontree.nodes.INode;

public abstract class AntibanTask extends Task {

    protected AntibanVariables variables;

    public AntibanTask(AntibanVariables variables) {
        this.variables = variables;
    }

    public abstract boolean getStoppingCondition();
    public abstract boolean beginAntibanActivity();

    public TaskFinish execute() {
        preActivitySetup();
        if (!beginAntibanActivity()) {
            return INode.NodeFinishes.GENERIC_SUCCESS;
        }

        long startTime = System.currentTimeMillis();
        while (isInGame() && getStoppingCondition()) {
            General.sleep(100, 500);
            AntiBanSingleton.get().resolveTimedActions();
        }

        int actionTime = (int) (System.currentTimeMillis() - startTime);

        AntiBanSingleton.get().setLastReactionTime(
                AntiBanSingleton.get().generateReactionTime(actionTime, false));

        variables.incrementNumberOfSleeps();
        variables.updateAverageAntibanSleep(actionTime);
        AntiBanSingleton.get().sleepReactionTime();

        return INode.NodeFinishes.GENERIC_SUCCESS;
    }

    public boolean isInGame() {
        return Login.getLoginState() == Login.STATE.INGAME;
    }

    public boolean isUnderAttack() {
        return false;
    }

    public boolean shouldIncrementWonResources() {
        return false;
    }

    public void preActivitySetup() {};
    public void postActivityTeardown() {};
}
