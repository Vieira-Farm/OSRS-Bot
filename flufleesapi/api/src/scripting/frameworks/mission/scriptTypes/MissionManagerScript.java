package scripting.frameworks.mission.scriptTypes;

import org.tribot.api.General;
import client.clientextensions.Script;
import scripting.frameworks.mission.missiontypes.Mission;
import scripting.frameworks.mission.missiontypes.MissionManager;
import scripting.frameworks.mission.missiontypes.TaskFinish;
import scripting.frameworks.modulardecisiontree.nodes.INode;

import java.util.AbstractMap;
import java.util.ArrayList;

public abstract class MissionManagerScript extends Script implements MissionManager {

    @Override
    public ArrayList<AbstractMap.SimpleEntry<String, String>> getPaintFields() {
        return Mission.getPaintFields();
    }

    @Override
    public void mainLoop() {
        TaskFinish taskFinish = INode.NodeFinishes.GENERIC_SUCCESS;
        while (!this.isMissionCompleted() && taskFinish.getFinishType() != TaskFinish.FinishTypes.STOP_SCRIPT) {
           taskFinish = executeMission();
           General.sleep(300);
        }
        if (taskFinish.getFinishType() == TaskFinish.FinishTypes.STOP_SCRIPT) {
            General.println("Stopping script due to: " + taskFinish.getDescription());
        }
    }

    @Override
    public String getMissionName() {
        return null;
    }
}
