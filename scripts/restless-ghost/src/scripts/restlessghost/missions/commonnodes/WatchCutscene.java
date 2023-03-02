package scripts.restlessghost.missions.commonnodes;

import org.tribot.api.General;
import org.tribot.api2007.types.RSVarBit;
import scripts.client.clientextensions.Game;
import scripts.client.clientextensions.NPCChat;
import scripts.scripting.antiban.AntiBanSingleton;
import scripts.scripting.frameworks.mission.missiontypes.TaskFinish;
import scripts.scripting.frameworks.task.Priority;
import scripts.scripting.frameworks.task.tasktypes.Task;

public class WatchCutscene extends Task {
    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean isValid() {
        return Game.getVarBitValue(6719) > 0 && !NPCChat.inChat(1);
    }

    @Override
    public TaskFinish execute() {
        long startTime = System.currentTimeMillis();
        RSVarBit cutsceneVarbit = RSVarBit.get(6719);
        while (cutsceneVarbit.getValue() > 0) {
            General.sleep(200, 300);
            AntiBanSingleton.get().resolveTimedActions();
        }

        int actionTime = (int) (System.currentTimeMillis() - startTime);
        AntiBanSingleton.get().setLastReactionTime(AntiBanSingleton.get().generateReactionTime(actionTime, false));
        return NodeFinishes.GENERIC_SUCCESS;
    }

    @Override
    public String getStatus() {
        return "Watching cutscene";
    }
}
