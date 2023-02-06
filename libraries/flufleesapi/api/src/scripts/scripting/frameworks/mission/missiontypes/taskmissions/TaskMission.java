package scripts.scripting.frameworks.mission.missiontypes.taskmissions;

import scripts.scripting.frameworks.mission.missiontypes.Mission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.INode;
import scripts.scripting.frameworks.task.TaskSet;

public interface TaskMission extends Mission {

    TaskSet getTaskSet();

    default INode getCurrentNode() {
        return getTaskSet().getValidTask();
    }
}
