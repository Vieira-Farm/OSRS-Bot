package scripts.scripting.frameworks.mission.missiontypes;

import org.tribot.api.General;
import org.tribot.api2007.Login;
import scripts.data.structures.ScriptVariables;
import scripts.scripting.frameworks.modulardecisiontree.nodes.INode;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;

public interface Mission {

    String getMissionName();
    boolean isMissionValid();
    boolean isMissionCompleted();
    INode getCurrentNode();

    default TaskFinish getMissionFinish(TaskFinish nodeFinish) {
        return nodeFinish;
    }

    static ArrayList<AbstractMap.SimpleEntry<String, String>> getPaintFields() {
        return new ArrayList<>(Arrays.asList(
                new AbstractMap.SimpleEntry<>("Mission", ScriptVariables.getInstance().getMissionName()),
                new AbstractMap.SimpleEntry<>("Status", ScriptVariables.getInstance().getScriptStatus())
            )
        );
    }

    /**
     * Extendable method which determines whether or need we need to stop execution of the current mission manager
     * after getting the output from the child.
     *
     * @param childTaskFinish TaskFinish from the child mission
     * @return True if we should stop executing the current mission manager, false otherwise
     */
    default boolean shouldStopExecution(TaskFinish childTaskFinish) {
        return childTaskFinish.getFinishType() != TaskFinish.FinishTypes.SUCCESS;
    }

    default TaskFinish executeMission() {
        TaskFinish nodeFinish = INode.NodeFinishes.GENERIC_SUCCESS;
        preMission();
        INode currentNode = getCurrentNode();
        while (currentNode != null && isMissionExecutable()) {
            ScriptVariables.getInstance().setScriptStatus(currentNode.getStatus());

            nodeFinish = currentNode.execute();
            if (shouldStopExecution(nodeFinish)) {
                return getMissionFinish(nodeFinish);
            }
            currentNode = getCurrentNode();
            General.sleep(300);
        }

        postMission();
        return getMissionFinish(nodeFinish);
    }

    default void preMission() {
        return;
    }

    default void postMission() {
        return;
    }

    default MissionGameState getMissionGameState() {
        return MissionGameState.IN_GAME;
    }

    default boolean isGameStateValid() {
        return this.getMissionGameState() == MissionGameState.BOTH ||
                !(this.getMissionGameState() == MissionGameState.IN_GAME ^ Login.getLoginState() == Login.STATE.INGAME);
    }

    default boolean isMissionExecutable() {
        return isGameStateValid() && isMissionValid() && !isMissionCompleted();
    }

    enum MissionGameState {
        OUT_OF_GAME,
        IN_GAME,
        BOTH;
    }

    public static class EarlyEndMission implements Mission {

        private String endReason;

        public EarlyEndMission(String endReason) {
            this.endReason = endReason;
        }

        @Override
        public String getMissionName() {
            return "Early end";
        }

        @Override
        public boolean isMissionValid() {
            return true;
        }

        @Override
        public boolean isMissionCompleted() {
            return true;
        }

        @Override
        public INode getCurrentNode() {
            return null;
        }

        @Override
        public TaskFinish getMissionFinish(TaskFinish nodeFinish) {
            return new TaskFinish() {
                @Override
                public FinishTypes getFinishType() {
                    return FinishTypes.STOP_SCRIPT;
                }

                @Override
                public String getDescription() {
                    return endReason;
                }
            };
        }
    }
}
