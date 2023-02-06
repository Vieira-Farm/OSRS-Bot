package scripts.scripting.frameworks.mission.scriptTypes;

import scripts.client.clientextensions.Script;
import org.tribot.api.General;
import scripts.scripting.frameworks.mission.missiontypes.Mission;
import scripts.scripting.frameworks.mission.missiontypes.TaskFinish;
import scripts.scripting.frameworks.modulardecisiontree.nodes.INode;

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
