package scripting.frameworks.mission.scriptTypes;

import client.clientextensions.Script;
import org.tribot.api.General;
import scripting.frameworks.mission.missiontypes.Mission;
import scripting.frameworks.mission.missiontypes.TaskFinish;
import scripting.frameworks.modulardecisiontree.nodes.INode;

import java.util.AbstractMap;
import java.util.ArrayList;

public abstract class MissionScript extends Script {

    protected Mission currentMission;
    public abstract Mission getMission();

    @Override
    public ArrayList<AbstractMap.SimpleEntry<String, String>> getPaintFields() {
        return Mission.getPaintFields();
    }

    @Override
    public void mainLoop() {
        TaskFinish taskFinish = INode.NodeFinishes.GENERIC_SUCCESS;
        currentMission = getMission();
        while (currentMission != null && taskFinish.getFinishType() != TaskFinish.FinishTypes.STOP_SCRIPT) {
            taskFinish = currentMission.executeMission();
            currentMission = getMission();
            currentMission = currentMission.isMissionCompleted() ? null : currentMission;
            General.sleep(300);
        }
        if (taskFinish.getFinishType() == TaskFinish.FinishTypes.STOP_SCRIPT) {
            General.println("Stopping script due to: " + taskFinish.getDescription());
        }
    }
}
