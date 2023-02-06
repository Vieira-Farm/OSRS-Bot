package scripts.scripting.frameworks.mission.missiontypes;

import org.tribot.api.General;
import scripts.data.structures.ScriptVariables;
import scripts.scripting.frameworks.modulardecisiontree.nodes.INode;

import java.util.LinkedList;
import java.util.ListIterator;

public interface MissionManager extends Mission {

    /**
     * Gets the queue of missions. A queue is used as it's FIFO
     *
     * @return Queue<Mission> containing all the missions to execute
     */
    LinkedList<Mission> getMissions();

    /**
     * Creates the list of missions for the manager to execute. This allows child missions to be initialized with
     * specific variables on each run of the manager
     */
    void initializeMissionList();

    default INode getCurrentNode() {
        return null;
    }

    /**
     * Gets whether or not the mission manager should loop it's missions. If it should when a mission is finished
     * it is readded to the mission list.
     *
     * @return True if we want to loop missions, false otherwise.
     */
    default boolean shouldLoopMissions() {
        return false;
    }

    /**
     * Gets the current mission being executed/to execute from the bag instance.
     *
     * @return Mission object representing the current mission or null if there is no current mission.
     */
    default Mission getCurrentMission() {
        return getMissions().peek();
    }
    /**
     * Gets the next mission to execute after the current mission finishes. It is important to note that if the current
     * mission is not completed then the next mission to execute is the current mission.
     *
     * @return Mission object representing the next mission to execute
     */
    default Mission getNextMission() {
        boolean removedFirstMission = false;
        Mission currentMission = getCurrentMission();
        if (currentMission != null && currentMission.isMissionExecutable()) {
            return currentMission;
        }
        ListIterator<Mission> missionListIterator = getMissions().listIterator();
        while (missionListIterator.hasNext()) {
            General.sleep(300);
            currentMission = missionListIterator.next();
            if (!removedFirstMission) {
                missionListIterator.remove();
                removedFirstMission = true;
                continue;
            }
            if (currentMission.isMissionValid() && !currentMission.isMissionCompleted()) {
                missionListIterator.remove();
                getMissions().addFirst(currentMission);
                return getCurrentMission();
            }
        }
        return null;
    }

    /**
     * Executes the current mission
     *
     * @return MissionFinish object or null
     */
    default TaskFinish executeMission() {
        TaskFinish childTaskFinish = INode.NodeFinishes.NO_VALID_MISSIONS;
        preMission();

        do {
            General.sleep(300);
            initializeMissionList();
            Mission currentMission = getNextMission();
            while (currentMission != null && currentMission.isMissionExecutable() && this.isMissionExecutable()) {
                General.sleep(300);
                ScriptVariables.getInstance().setMissionName(currentMission.getMissionName());
                childTaskFinish = currentMission.executeMission();
                if (shouldStopExecution(childTaskFinish)) {
                    return getMissionFinish(childTaskFinish);
                }
                currentMission = getNextMission();
            }
        } while (shouldLoopMissions() && this.isMissionExecutable());
        return getMissionFinish(childTaskFinish);
    }
}
